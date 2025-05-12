package com.luis.facturacion.mvc_deliveryNote;

import com.luis.facturacion.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class DeliveryNoteDAO {

    public void save(DeliveryNoteEntity deliveryNoteEntity) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            session.persist(deliveryNoteEntity);

            transaction.commit();
        } catch (Exception e) {
            System.err.println("Error al guardar cliente: " + e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
