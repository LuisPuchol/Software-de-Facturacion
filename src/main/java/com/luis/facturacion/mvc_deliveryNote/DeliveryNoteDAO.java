package com.luis.facturacion.mvc_deliveryNote;

import com.luis.facturacion.HibernateUtil;
import com.luis.facturacion.mvc_article.database.ArticleEntity;
import com.luis.facturacion.utils.GlobalDAO;
import org.hibernate.Session;
import org.hibernate.query.Query;


public class DeliveryNoteDAO extends GlobalDAO<DeliveryNoteEntity, Integer> {
    private static DeliveryNoteDAO instance;

    private DeliveryNoteDAO() {
        super(DeliveryNoteEntity.class);
    }

    public static DeliveryNoteDAO getInstance() {
        if (instance == null) {
            instance = new DeliveryNoteDAO();
        }
        return instance;
    }

    public Integer getNextDeliveryNoteNumber() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Number> query = session.createQuery(
                    "SELECT COALESCE(MAX(d.index), 0) FROM DeliveryNoteEntity d",
                    Number.class
            );
            Number maxIndex = query.getSingleResult();
            return maxIndex.intValue() + 1;
        } catch (Exception e) {
            System.err.println("Error obtaining the number: " + e.getMessage());
            throw e;
        }
    }
}
