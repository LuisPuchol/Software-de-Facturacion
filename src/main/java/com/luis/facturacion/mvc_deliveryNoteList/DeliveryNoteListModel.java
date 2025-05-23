package com.luis.facturacion.mvc_deliveryNoteList;

import com.luis.facturacion.utils.HibernateUtil;
import com.luis.facturacion.mvc_client.database.ClientDAO;
import com.luis.facturacion.mvc_deliveryNote.database.DeliveryNoteDAO;
import com.luis.facturacion.mvc_deliveryNote.database.DeliveryNoteEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;

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

    private DeliveryNoteDAO deliveryNoteDAO;
    private ClientDAO clientDAO;

    private DeliveryNoteListModel() {
        this.deliveryNoteDAO = DeliveryNoteDAO.getInstance();
        this.clientDAO = ClientDAO.getInstance();
    }

    /**
     * Gets the singleton instance of DeliveryNoteListModel.
     *
     * @return The singleton instance
     */
    public static DeliveryNoteListModel getInstance() {
        if (instance == null) {
            instance = new DeliveryNoteListModel();
        }
        return instance;
    }

    /**
     * Sets the controller reference.
     *
     * @param controller The controller to set
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

        List<DeliveryNoteEntity> entities = new ArrayList<>();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("FROM DeliveryNoteEntity d WHERE d.date BETWEEN :fromDate AND :toDate");

            if (includeInvoices) {
                queryBuilder.append(" AND d.invoiceNumber IS NOT NULL");
            }

            Query<DeliveryNoteEntity> query = session.createQuery(queryBuilder.toString(), DeliveryNoteEntity.class);
            query.setParameter("fromDate", fromDate);
            query.setParameter("toDate", toDate);

            entities = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertToListItems(entities);
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

            try {
                String clientName = clientDAO.getNameByIndex(entity.getClientId());
                item.setClientName(clientName);
            } catch (Exception e) {
                item.setClientName("Cliente #" + entity.getClientId());
            }

            items.add(item);
        }

        return items;
    }

}