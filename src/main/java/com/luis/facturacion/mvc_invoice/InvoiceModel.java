package com.luis.facturacion.mvc_invoice;

import com.luis.facturacion.mvc_client.database.ClientEntity;
import com.luis.facturacion.mvc_invoice.database.InvoiceDAO;
import com.luis.facturacion.mvc_invoice.database.InvoiceEntity;
import com.luis.facturacion.mvc_vatConfig.database.VATConfigDAO;
import com.luis.facturacion.mvc_vatConfig.database.VATConfigEntity;
import com.luis.facturacion.utils.HibernateUtil;
import com.luis.facturacion.mvc_client.database.ClientDAO;
import com.luis.facturacion.mvc_deliveryNote.DeliveryNoteDAO;
import com.luis.facturacion.mvc_deliveryNote.DeliveryNoteEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

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
        ClientEntity client = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            client = session.get(ClientEntity.class, clientId);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching client: " + e.getMessage());
        }

        if (client == null) {
            throw new RuntimeException("Client not found with ID: " + clientId);
        }

        // Calculate total amount from delivery notes
        double totalAmount = deliveryNotes.stream()
                .mapToDouble(DeliveryNoteEntity::getTotalAmount)
                .sum();

        // Apply VAT and surcharge if needed
        Integer clientType = client.getClientType();
        boolean applyVAT = (clientType != null && clientType == 1);
        boolean applySurcharge = (client.getEquivalenceSurcharge() != null && client.getEquivalenceSurcharge() == 1);

        double finalAmount = calculateFinalAmount(totalAmount, applyVAT, applySurcharge);

        // Create and save new invoice
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
            if (vatConfig != null) {
                if (applyVAT) {
                    double vatAmount = baseAmount * (vatConfig.getVatRate() / 100);
                    finalAmount += vatAmount;
                }

                if (applySurcharge) {
                    double surchargeAmount = baseAmount * (vatConfig.getSurchargeRate() / 100);
                    finalAmount += surchargeAmount;
                }
            }
        }

        finalAmount = Math.round(finalAmount * 100.0) / 100.0;

        return finalAmount;
    }

    /**
     * Updates all delivery notes to reference the newly created invoice.
     *
     * @param deliveryNotes List of delivery notes to update
     * @param invoiceId     ID of the invoice to reference
     */
    private void updateDeliveryNotesWithInvoice(List<DeliveryNoteEntity> deliveryNotes, Integer invoiceId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            for (DeliveryNoteEntity note : deliveryNotes) {
                note.setInvoiceNumber(invoiceId);
                session.merge(note);
            }

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error updating delivery notes: " + e.getMessage());
        }
    }

    public List<ClientInvoiceItem> getClientsWithDeliveryNotes(LocalDate toDate) {
        List<DeliveryNoteEntity> deliveryNotes = getDeliveryNotesUpToDate(toDate);
        Map<Integer, ClientInvoiceItem> clientMap = new HashMap<>();

        for (DeliveryNoteEntity note : deliveryNotes) {
            int clientId = note.getClientId();

            // Skip delivery notes that already have an invoice
            if (note.getInvoiceNumber() != null) {
                continue;
            }

            // Get or create client item
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
        List<DeliveryNoteEntity> entities = new ArrayList<>();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM DeliveryNoteEntity d WHERE d.clientId = :clientId " +
                    "AND d.date <= :toDate AND d.invoiceNumber IS NULL";

            Query<DeliveryNoteEntity> query = session.createQuery(hql, DeliveryNoteEntity.class);
            query.setParameter("clientId", clientId);
            query.setParameter("toDate", toDate);

            entities = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertToDeliveryNoteItems(entities);
    }

    /**
     * Retrieves all delivery notes up to the specified date.
     *
     * @param toDate End date for the search
     * @return List of DeliveryNoteEntity objects
     */
    private List<DeliveryNoteEntity> getDeliveryNotesUpToDate(LocalDate toDate) {
        List<DeliveryNoteEntity> entities = new ArrayList<>();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM DeliveryNoteEntity d WHERE d.date <= :toDate";

            Query<DeliveryNoteEntity> query = session.createQuery(hql, DeliveryNoteEntity.class);
            query.setParameter("toDate", toDate);

            entities = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return entities;
    }

    /**
     * Converts DeliveryNoteEntity objects to DeliveryNoteInvoiceItem objects.
     *
     * @param entities List of DeliveryNoteEntity objects
     * @return List of DeliveryNoteInvoiceItem objects
     */
    private List<DeliveryNoteInvoiceItem> convertToDeliveryNoteItems(List<DeliveryNoteEntity> entities) {
        List<DeliveryNoteInvoiceItem> items = new ArrayList<>();

        for (DeliveryNoteEntity entity : entities) {
            DeliveryNoteInvoiceItem item = new DeliveryNoteInvoiceItem(
                    entity.getDate() != null ? entity.getDate().format(DATE_FORMATTER) : "",
                    String.valueOf(entity.getIndex()),
                    String.valueOf(entity.getTotalAmount())
            );

            items.add(item);
        }

        return items;
    }

    /**
     * Gets delivery note entities for a specific client up to the specified date.
     * This version returns the actual entities rather than display items.
     *
     * @param clientId ID of the client
     * @param toDate   End date for the search
     * @return List of DeliveryNoteEntity objects
     */
    public List<DeliveryNoteEntity> getDeliveryNoteEntitiesForClient(int clientId, LocalDate toDate) {
        List<DeliveryNoteEntity> entities = new ArrayList<>();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM DeliveryNoteEntity d WHERE d.clientId = :clientId " +
                    "AND d.date <= :toDate AND d.invoiceNumber IS NULL";

            Query<DeliveryNoteEntity> query = session.createQuery(hql, DeliveryNoteEntity.class);
            query.setParameter("clientId", clientId);
            query.setParameter("toDate", toDate);

            entities = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return entities;
    }
}