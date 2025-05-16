package com.luis.facturacion.mvc_invoice;

import com.luis.facturacion.utils.HibernateUtil;
import com.luis.facturacion.mvc_client.database.ClientDAO;
import com.luis.facturacion.mvc_deliveryNote.DeliveryNoteDAO;
import com.luis.facturacion.mvc_deliveryNote.DeliveryNoteEntity;
import org.hibernate.Session;
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

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private InvoiceModel() {
        this.deliveryNoteDAO = DeliveryNoteDAO.getInstance();
        this.clientDAO = ClientDAO.getInstance();
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
     * Retrieves clients with pending delivery notes up to the specified date.
     *
     * @param toDate End date for the search
     * @return List of ClientInvoiceItem objects
     */
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
     * @param toDate End date for the search
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
}