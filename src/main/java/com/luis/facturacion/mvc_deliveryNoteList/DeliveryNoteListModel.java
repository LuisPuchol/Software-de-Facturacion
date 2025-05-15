package com.luis.facturacion.mvc_deliveryNoteList;

import com.luis.facturacion.HibernateUtil;
import com.luis.facturacion.mvc_client.database.ClientDAO;
import com.luis.facturacion.mvc_deliveryNote.DeliveryNoteDAO;
import com.luis.facturacion.mvc_deliveryNote.DeliveryNoteEntity;
import com.luis.facturacion.mvc_deliveryNote.DeliveryNoteItemEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Model for the Delivery Note List view.
 * Handles business logic and data access for listing delivery notes.
 */
public class DeliveryNoteListModel {
    private static DeliveryNoteListModel instance;
    private DeliveryNoteListController controller;

    // Database access objects
    private DeliveryNoteDAO deliveryNoteDAO;
    private ClientDAO clientDAO;

    /**
     * Private constructor for Singleton pattern.
     */
    private DeliveryNoteListModel() {
        System.out.println("Delivery-Note List Model created");
        this.deliveryNoteDAO = DeliveryNoteDAO.getInstance();
        this.clientDAO = ClientDAO.getInstance();
    }

    /**
     * Gets the singleton instance of DeliveryNoteListModel.
     */
    public static DeliveryNoteListModel getInstance() {
        if (instance == null) {
            instance = new DeliveryNoteListModel();
        }
        return instance;
    }

    /**
     * Sets the controller reference.
     */
    public void setController(DeliveryNoteListController controller) {
        if (this.controller == null) {
            this.controller = controller;
        }
    }

    /**
     * Retrieves delivery notes from the database within the specified date range.
     * Optionally includes only those with associated invoices.
     *
     * @param fromDate Start date for the search
     * @param toDate End date for the search
     * @param includeInvoices Whether to include only notes with associated invoices
     * @return List of DeliveryNoteListItem objects
     */
    public List<DeliveryNoteListItem> getDeliveryNotesByDateRange(
            LocalDate fromDate, LocalDate toDate, boolean includeInvoices) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Build the HQL query with date range
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("FROM DeliveryNoteEntity d WHERE d.date BETWEEN :fromDate AND :toDate");

            // Add filter for invoices if needed
            if (includeInvoices) {
                queryBuilder.append(" AND d.invoiceNumber IS NOT NULL");
            }

            // Create and execute query
            Query<DeliveryNoteEntity> query = session.createQuery(queryBuilder.toString(), DeliveryNoteEntity.class);
            query.setParameter("fromDate", fromDate);
            query.setParameter("toDate", toDate);

            List<DeliveryNoteEntity> entities = query.list();

            // Convert entities to list items with additional data
            return convertToListItems(entities);
        } catch (Exception e) {
            System.err.println("Error retrieving delivery notes: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Converts DeliveryNoteEntity objects to DeliveryNoteListItem objects
     * with additional display data.
     *
     * @param entities List of DeliveryNoteEntity objects
     * @return List of DeliveryNoteListItem objects
     */
    private List<DeliveryNoteListItem> convertToListItems(List<DeliveryNoteEntity> entities) {
        List<DeliveryNoteListItem> items = new ArrayList<>();

        for (DeliveryNoteEntity entity : entities) {
            DeliveryNoteListItem item = new DeliveryNoteListItem(entity);

            // Set client name
            String clientName = clientDAO.getNameById(entity.getClientId());
            item.setClientName(clientName != null ? clientName : "Cliente #" + entity.getClientId());

            // Calculate and set total amount
            BigDecimal totalAmount = calculateTotalAmount(entity.getId());
            item.setTotalAmount(totalAmount);

            items.add(item);
        }

        return items;
    }

    /**
     * Calculates the total amount for a delivery note by summing its items.
     *
     * @param deliveryNoteId The delivery note ID
     * @return The total amount
     */
    private BigDecimal calculateTotalAmount(Integer deliveryNoteId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Query to get all items for this delivery note
            Query<DeliveryNoteItemEntity> query = session.createQuery(
                    "FROM DeliveryNoteItemEntity i WHERE i.deliveryNoteId = :deliveryNoteId",
                    DeliveryNoteItemEntity.class
            );
            query.setParameter("deliveryNoteId", deliveryNoteId);

            List<DeliveryNoteItemEntity> items = query.list();

            // Sum the amounts
            BigDecimal total = BigDecimal.ZERO;
            for (DeliveryNoteItemEntity item : items) {
                BigDecimal itemAmount = item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
                total = total.add(itemAmount);
            }

            return total;
        } catch (Exception e) {
            System.err.println("Error calculating total amount: " + e.getMessage());
            return BigDecimal.ZERO;
        }
    }
}