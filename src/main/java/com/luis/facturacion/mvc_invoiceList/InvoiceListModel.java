package com.luis.facturacion.mvc_invoiceList;

import com.luis.facturacion.mvc_client.database.ClientDAO;
import com.luis.facturacion.mvc_deliveryNote.DeliveryNoteDAO;
import com.luis.facturacion.mvc_deliveryNote.DeliveryNoteEntity;
import com.luis.facturacion.mvc_deliveryNoteList.DeliveryNoteListItem;
import com.luis.facturacion.mvc_invoice.database.InvoiceDAO;
import com.luis.facturacion.mvc_invoice.database.InvoiceEntity;
import com.luis.facturacion.mvc_vatConfig.database.VATConfigDAO;
import com.luis.facturacion.mvc_vatConfig.database.VATConfigEntity;
import com.luis.facturacion.utils.HibernateUtil;
import com.luis.facturacion.utils.pdf.PDFGenerator;
import com.luis.facturacion.utils.ShowAlert;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Model for the Invoice List view.
 * Handles business logic and data access for listing invoices.
 */
public class InvoiceListModel {
    private static InvoiceListModel instance;
    private InvoiceListController controller;

    private ClientDAO clientDAO;
    private DeliveryNoteDAO deliveryNoteDAO;
    private InvoiceDAO invoiceDAO;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private InvoiceListModel() {
        this.clientDAO = ClientDAO.getInstance();
        this.deliveryNoteDAO = DeliveryNoteDAO.getInstance();
        this.invoiceDAO = InvoiceDAO.getInstance();
    }

    /**
     * Gets the singleton instance of InvoiceListModel.
     *
     * @return The singleton instance
     */
    public static InvoiceListModel getInstance() {
        if (instance == null) {
            instance = new InvoiceListModel();
        }
        return instance;
    }

    /**
     * Sets the controller reference.
     *
     * @param controller The controller to set
     */
    public void setController(InvoiceListController controller) {
        if (this.controller == null) {
            this.controller = controller;
        }
    }

    /**
     * Retrieves invoices from the database within the specified date range.
     *
     * @param fromDate Start date for the search
     * @param toDate   End date for the search
     * @return List of InvoiceListItem objects
     */
    public List<InvoiceListItem> getInvoicesByDateRange(LocalDate fromDate, LocalDate toDate) {
        List<InvoiceEntity> entities = invoiceDAO.findByDateRange(fromDate, toDate);
        return convertToInvoiceItems(entities);
    }

    /**
     * Retrieves delivery notes associated with a specific invoice.
     *
     * @param invoiceId ID of the invoice
     * @return List of DeliveryNoteListItem objects
     */
    public List<DeliveryNoteListItem> getDeliveryNotesForInvoice(Integer invoiceId) {
        List<DeliveryNoteEntity> entities = deliveryNoteDAO.findByInvoiceId(invoiceId);
        return convertToDeliveryNoteItems(entities);
    }

    /**
     * Generating and showing a PDF for an invoice
     *
     * @param invoiceId ID of the invoice to generate PDF for
     */
    public void generateAndShowInvoicePDF(Integer invoiceId) {
        try {
            InvoiceEntity invoice = invoiceDAO.getById(invoiceId);
            List<DeliveryNoteEntity> deliveryNotes = deliveryNoteDAO.findByInvoiceId(invoiceId);

            File pdfFile = PDFGenerator.generateInvoicePDF(invoice, deliveryNotes);
            java.awt.Desktop.getDesktop().open(pdfFile);

        } catch (Exception e) {
            e.printStackTrace();
            ShowAlert.showError("Error", "Error al generar el PDF de la factura: " + e.getMessage());
        }
    }

    /**
     * Converts InvoiceEntity objects to InvoiceListItem objects
     * with additional display data including calculated fields.
     *
     * @param entities List of InvoiceEntity objects
     * @return List of InvoiceListItem objects
     */
    private List<InvoiceListItem> convertToInvoiceItems(List<InvoiceEntity> entities) {
        return entities.stream()
                .map(this::createInvoiceListItem)
                .toList();
    }

    /**
     * Creates and configures a single InvoiceListItem from an InvoiceEntity.
     * Sets client name and calculates amount fields for display.
     *
     * @param entity The invoice entity to convert
     * @return Fully configured InvoiceListItem for table display
     */
    private InvoiceListItem createInvoiceListItem(InvoiceEntity entity) {
        InvoiceListItem item = new InvoiceListItem(entity);

        item.setClientName(clientDAO.getNameById(entity.getClientId()));
        setAmountFields(item, entity);

        return item;
    }

    private void setAmountFields(InvoiceListItem item, InvoiceEntity entity) {
        double totalAmount = entity.getTotalAmount();
        double baseAmount = calculateBaseAmount(entity);
        double vatAmount = totalAmount - baseAmount;

        item.setBaseAmount(String.format("%.2f", baseAmount));
        item.setVatAmount(String.format("%.2f", vatAmount));
    }

    /**
     * Calculates the base amount for an invoice by retrieving and summing
     * the total amounts of its associated delivery notes.
     *
     * @param invoiceEntity The invoice entity
     * @return The calculated base amount
     */
    private double calculateBaseAmount(InvoiceEntity invoiceEntity) {
        return deliveryNoteDAO.getTotalAmountByInvoiceId(invoiceEntity.getId());

    }


    /**
     * Converts DeliveryNoteEntity objects to DeliveryNoteListItem objects.
     *
     * @param entities List of DeliveryNoteEntity objects
     * @return List of DeliveryNoteListItem objects
     */
    private List<DeliveryNoteListItem> convertToDeliveryNoteItems(List<DeliveryNoteEntity> entities) {
        return entities.stream()
                .map(DeliveryNoteListItem::new)
                .toList();
    }

}
