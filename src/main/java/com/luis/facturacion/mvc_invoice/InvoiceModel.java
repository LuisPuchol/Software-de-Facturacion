package com.luis.facturacion.mvc_invoice;

import com.luis.facturacion.mvc_client.database.ClientEntity;
import com.luis.facturacion.mvc_invoice.database.InvoiceDAO;
import com.luis.facturacion.mvc_invoice.database.InvoiceEntity;
import com.luis.facturacion.mvc_vatConfig.database.VATConfigDAO;
import com.luis.facturacion.mvc_vatConfig.database.VATConfigEntity;
import com.luis.facturacion.mvc_client.database.ClientDAO;
import com.luis.facturacion.mvc_deliveryNote.database.DeliveryNoteDAO;
import com.luis.facturacion.mvc_deliveryNote.database.DeliveryNoteEntity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Model for the Invoice view.
 * Handles business logic and data access for creating invoices from delivery notes.
 */
public class InvoiceModel {
    private static InvoiceModel instance;
    private InvoiceController controller;

    private DeliveryNoteDAO deliveryNoteDAO;
    private ClientDAO clientDAO;
    private InvoiceDAO invoiceDAO;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private InvoiceModel() {
        this.deliveryNoteDAO = DeliveryNoteDAO.getInstance();
        this.clientDAO = ClientDAO.getInstance();
        this.invoiceDAO = InvoiceDAO.getInstance();
    }

    /**
     * Gets the singleton instance of InvoiceModel.
     *
     * @return The singleton instance
     */
    public static InvoiceModel getInstance() {
        if (instance == null) {
            instance = new InvoiceModel();
        }
        return instance;
    }

    /**
     * Sets the controller reference.
     *
     * @param controller The controller to set
     */
    public void setController(InvoiceController controller) {
        if (this.controller == null) {
            this.controller = controller;
        }
    }

    /**
     * Creates a new invoice for a client based on their delivery notes.
     * Updates all delivery notes to reference the new invoice.
     *
     * @param clientId      The ID of the client
     * @param deliveryNotes List of delivery notes to include in the invoice
     * @return The ID of the created invoice
     */
    public Integer createInvoice(Integer clientId, List<DeliveryNoteEntity> deliveryNotes) {
        ClientEntity client = clientDAO.getById(clientId);

        // Total from delivery notes
        double totalAmount = deliveryNotes.stream()
                .mapToDouble(DeliveryNoteEntity::getTotalAmount)
                .sum();

        // Apply VAT and surcharge
        Integer clientType = client.getClientType();
        boolean applyVAT = clientType == 1;
        boolean applySurcharge = client.getEquivalenceSurcharge() == 1;

        double finalAmount = calculateFinalAmount(totalAmount, applyVAT, applySurcharge);

        // Create invoice
        InvoiceEntity invoice = new InvoiceEntity(
                clientId,
                LocalDate.now(),
                clientType,
                finalAmount
        );

        InvoiceDAO.getInstance().save(invoice);

        // Update all delivery notes to reference this invoice
        updateDeliveryNotesWithInvoice(deliveryNotes, invoice.getId());

        return invoice.getId();
    }

    /**
     * Calculates the final invoice amount including VAT and surcharge if applicable.
     *
     * @param baseAmount     The base amount before taxes
     * @param applyVAT       Whether to apply VAT
     * @param applySurcharge Whether to apply equivalence surcharge
     * @return The final amount after applying taxes
     */
    private double calculateFinalAmount(double baseAmount, boolean applyVAT, boolean applySurcharge) {
        double finalAmount = baseAmount;

        if (applyVAT || applySurcharge) {
            VATConfigEntity vatConfig = VATConfigDAO.getInstance().getCurrentConfig();
            if (applyVAT) {
                double vatAmount = baseAmount * (vatConfig.getVatRate() / 100);
                finalAmount += vatAmount;
            }

            if (applySurcharge) {
                double surchargeAmount = baseAmount * (vatConfig.getSurchargeRate() / 100);
                finalAmount += surchargeAmount;
            }

        }

        // Original, but i guess its easier using BigDecimal
        // finalAmount = Math.round(finalAmount * 100.0) / 100.0;

        finalAmount = BigDecimal.valueOf(finalAmount)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
        return finalAmount;
    }

    /**
     * Updates all delivery notes to reference the newly created invoice.
     *
     * @param deliveryNotes List of delivery notes to update
     * @param invoiceId     ID of the invoice to reference
     */
    private void updateDeliveryNotesWithInvoice(List<DeliveryNoteEntity> deliveryNotes, Integer invoiceId) {
        deliveryNotes.forEach(note -> {
            note.setInvoiceNumber(invoiceId);
            deliveryNoteDAO.update(note);
        });
    }

    /**
     * Retrieves clients with pending delivery notes up to the specified date.
     * Groups delivery notes by client and calculates total count and amount for each.
     * Uses HashMap to avoid client duplicates and aggregate statistics efficiently.
     *
     * @param toDate Maximum date to include delivery notes
     * @return List of ClientInvoiceItem with aggregated data per client
     */
    public List<ClientInvoiceItem> getClientsWithDeliveryNotes(LocalDate toDate) {
        List<DeliveryNoteEntity> deliveryNotes = deliveryNoteDAO.findByDateBeforeAndNotInvoiced(toDate);
        Map<Integer, ClientInvoiceItem> clientMap = new HashMap<>();

        for (DeliveryNoteEntity note : deliveryNotes) {
            int clientId = note.getClientId();

            // Skip delivery notes that already have an invoice
            if (note.getInvoiceNumber() != null) {
                continue;
            }

            // Get / create client item
            if (!clientMap.containsKey(clientId)) {
                String clientName = clientDAO.getNameById(clientId);
                ClientInvoiceItem clientItem = new ClientInvoiceItem(
                        String.valueOf(clientId),
                        clientName,
                        "0",
                        "0.00"
                );
                clientMap.put(clientId, clientItem);
            }

            // Update client stats
            ClientInvoiceItem clientItem = clientMap.get(clientId);
            clientItem.incrementDeliveryNoteCount();
            clientItem.addToTotalAmount(note.getTotalAmount());
        }

        return new ArrayList<>(clientMap.values());
    }

    /**
     * Retrieves delivery notes for a specific client up to the specified date.
     *
     * @param clientId ID of the client
     * @param toDate   End date for the search
     * @return List of DeliveryNoteInvoiceItem objects
     */
    public List<DeliveryNoteInvoiceItem> getDeliveryNotesForClient(int clientId, LocalDate toDate) {
        List<DeliveryNoteEntity> entities = deliveryNoteDAO.findByClientAndDateBeforeAndNotInvoiced(clientId, toDate);
        return convertToDeliveryNoteItems(entities);
    }

    /**
     * Converts DeliveryNoteEntity objects to DeliveryNoteInvoiceItem objects.
     *
     * @param entities List of DeliveryNoteEntity objects
     * @return List of DeliveryNoteInvoiceItem objects
     */
    private List<DeliveryNoteInvoiceItem> convertToDeliveryNoteItems(List<DeliveryNoteEntity> entities) {
        return entities.stream()
                .map(entity -> new DeliveryNoteInvoiceItem(
                        entity.getDate().format(DATE_FORMATTER),
                        String.valueOf(entity.getIndex()),
                        String.valueOf(entity.getTotalAmount())
                ))
                .toList();
    }

    /**
     * Creates an invoice for a specific client including all their pending delivery notes up to a date.
     *
     * @param clientId The ID of the client to invoice
     * @param toDate   The maximum date for delivery notes to include
     * @return The ID of the created invoice
     * @throws RuntimeException if client not found or no delivery notes available
     */
    public Integer createInvoiceForClient(Integer clientId, LocalDate toDate) {
        List<DeliveryNoteEntity> deliveryNotes = deliveryNoteDAO.findByClientAndDateBeforeAndNotInvoiced(clientId, toDate);
        return createInvoice(clientId, deliveryNotes);
    }
}