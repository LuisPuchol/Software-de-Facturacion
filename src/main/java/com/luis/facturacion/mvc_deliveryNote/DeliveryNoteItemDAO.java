package com.luis.facturacion.mvc_deliveryNote;

import com.luis.facturacion.utils.GlobalDAO;
import com.luis.facturacion.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeliveryNoteItemDAO extends GlobalDAO<DeliveryNoteItemEntity, Integer> {
    private static final Logger LOGGER = Logger.getLogger(DeliveryNoteItemDAO.class.getName());
    private static DeliveryNoteItemDAO instance;

    private DeliveryNoteItemDAO() {
        super(DeliveryNoteItemEntity.class);
    }

    public static DeliveryNoteItemDAO getInstance() {
        if (instance == null) {
            instance = new DeliveryNoteItemDAO();
        }
        return instance;
    }

    /**
     * Gets all items for a specific delivery note
     * @param deliveryNoteId ID of the delivery note
     * @return List of items for the delivery note
     */
    public List<DeliveryNoteItemEntity> getItemsByDeliveryNoteId(Integer deliveryNoteId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<DeliveryNoteItemEntity> query = session.createQuery(
                    "FROM DeliveryNoteItemEntity WHERE deliveryNoteID = :id",
                    DeliveryNoteItemEntity.class);
            query.setParameter("id", deliveryNoteId);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting items for delivery note: " + deliveryNoteId, e);
            return List.of();
        }
    }
}
