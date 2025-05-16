package com.luis.facturacion.mvc_invoiceList;

import com.luis.facturacion.mvc_client.database.ClientDAO;
import com.luis.facturacion.mvc_deliveryNote.DeliveryNoteEntity;
import com.luis.facturacion.mvc_deliveryNoteList.DeliveryNoteListItem;
import com.luis.facturacion.mvc_invoice.database.InvoiceEntity;
import com.luis.facturacion.mvc_vatConfig.database.VATConfigDAO;
import com.luis.facturacion.mvc_vatConfig.database.VATConfigEntity;
import com.luis.facturacion.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

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
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private InvoiceListModel() {
        this.clientDAO = ClientDAO.getInstance();
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
     * @param toDate End date for the search
     * @return List of InvoiceListItem objects
     */
    public List<InvoiceListItem> getInvoicesByDateRange(LocalDate fromDate, LocalDate toDate) {
        List<InvoiceEntity> entities = new ArrayList<>();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("FROM InvoiceEntity i WHERE i.date BETWEEN :fromDate AND :toDate");

            Query<InvoiceEntity> query = session.createQuery(queryBuilder.toString(), InvoiceEntity.class);
            query.setParameter("fromDate", fromDate);
            query.setParameter("toDate", toDate);

            entities = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertToInvoiceItems(entities);
    }

    /**
     * Retrieves delivery notes associated with a specific invoice.
     *
     * @param invoiceId ID of the invoice
     * @return List of DeliveryNoteListItem objects
     */
    public List<DeliveryNoteListItem> getDeliveryNotesForInvoice(Integer invoiceId) {
        List<DeliveryNoteEntity> entities = new ArrayList<>();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM DeliveryNoteEntity d WHERE d.invoiceNumber = :invoiceId";

            Query<DeliveryNoteEntity> query = session.createQuery(hql, DeliveryNoteEntity.class);
            query.setParameter("invoiceId", invoiceId);

            entities = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertToDeliveryNoteItems(entities);
    }

    /**
     * Converts InvoiceEntity objects to InvoiceListItem objects
     * with additional display data including calculated fields.
     *
     * @param entities List of InvoiceEntity objects
     * @return List of InvoiceListItem objects
     */
    private List<InvoiceListItem> convertToInvoiceItems(List<InvoiceEntity> entities) {
        List<InvoiceListItem> items = new ArrayList<>();

        for (InvoiceEntity entity : entities) {
            InvoiceListItem item = new InvoiceListItem(entity);

            // Get client information
            String clientName = "Cliente desconocido";
            try {
                String name = clientDAO.getNameById(entity.getClientId());
                if (name != null && !name.isEmpty()) {
                    clientName = name;
                }
            } catch (Exception e) {
                System.err.println("Error getting client name: " + e.getMessage());
            }
            item.setClientName(clientName);

            // Calculate base amount and VAT amount
            double totalAmount = entity.getTotalAmount();
            double baseAmount = calculateBaseAmount(entity);
            double vatAmount = totalAmount - baseAmount;

            item.setBaseAmount(String.format("%.2f", baseAmount));
            item.setVatAmount(String.format("%.2f", vatAmount));

            items.add(item);
        }

        return items;
    }

    /**
     * Calculates the base amount for an invoice by retrieving and summing
     * the total amounts of its associated delivery notes.
     *
     * @param invoiceEntity The invoice entity
     * @return The calculated base amount
     */
    private double calculateBaseAmount(InvoiceEntity invoiceEntity) {
        double baseAmount = 0.0;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT SUM(d.totalAmount) FROM DeliveryNoteEntity d WHERE d.invoiceNumber = :invoiceId";

            Query<Double> query = session.createQuery(hql, Double.class);
            query.setParameter("invoiceId", invoiceEntity.getId());

            Double sum = query.uniqueResult();
            if (sum != null) {
                baseAmount = sum;
            } else {
                // Fallback: Reverse calculate based on invoice type and VAT settings
                baseAmount = calculateBaseAmountFromTotal(invoiceEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Fallback: Reverse calculate based on invoice type and VAT settings
            baseAmount = calculateBaseAmountFromTotal(invoiceEntity);
        }

        return baseAmount;
    }

    /**
     * Reverse calculates the base amount from the total amount using
     * the client type and VAT settings.
     *
     * @param invoiceEntity The invoice entity
     * @return The calculated base amount
     */
    private double calculateBaseAmountFromTotal(InvoiceEntity invoiceEntity) {
        double totalAmount = invoiceEntity.getTotalAmount();
        Integer clientType = invoiceEntity.getType();

        // If client type doesn't require VAT, the base amount equals the total amount
        if (clientType == null || clientType != 1) {
            return totalAmount;
        }

        // Otherwise, reverse calculate based on VAT rate
        try {
            VATConfigEntity vatConfig = VATConfigDAO.getInstance().getCurrentConfig();
            if (vatConfig != null) {
                double vatRate = vatConfig.getVatRate();
                if (vatRate > 0) {
                    // totalAmount = baseAmount * (1 + vatRate/100)
                    // baseAmount = totalAmount / (1 + vatRate/100)
                    return totalAmount / (1 + vatRate/100);
                }
            }
        } catch (Exception e) {
            System.err.println("Error getting VAT config: " + e.getMessage());
        }

        // If VAT calculation fails, return 90% as a fallback approximation
        return totalAmount * 0.9;
    }

    /**
     * Converts DeliveryNoteEntity objects to DeliveryNoteListItem objects.
     *
     * @param entities List of DeliveryNoteEntity objects
     * @return List of DeliveryNoteListItem objects
     */
    private List<DeliveryNoteListItem> convertToDeliveryNoteItems(List<DeliveryNoteEntity> entities) {
        List<DeliveryNoteListItem> items = new ArrayList<>();

        for (DeliveryNoteEntity entity : entities) {
            DeliveryNoteListItem item = new DeliveryNoteListItem(entity);
            items.add(item);
        }

        return items;
    }
}