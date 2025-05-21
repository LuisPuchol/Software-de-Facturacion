package com.luis.facturacion.mvc_deliveryNote.database;

import com.luis.facturacion.utils.HibernateUtil;
import com.luis.facturacion.utils.GlobalDAO;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.List;


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

    public List<DeliveryNoteEntity> findByDateBeforeAndNotInvoiced(LocalDate toDate) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM DeliveryNoteEntity d WHERE d.date <= :toDate";
            Query<DeliveryNoteEntity> query = session.createQuery(hql, DeliveryNoteEntity.class);
            query.setParameter("toDate", toDate);
            return query.list();
        } catch (Exception e) {
            System.err.println("Error getting delivery notes up to date: " + e.getMessage());
            throw e;
        }
    }

    public List<DeliveryNoteEntity> findByClientAndDateBeforeAndNotInvoiced(Integer clientId, LocalDate toDate) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM DeliveryNoteEntity d WHERE d.clientId = :clientId " +
                    "AND d.date <= :toDate AND d.invoiceNumber IS NULL";
            Query<DeliveryNoteEntity> query = session.createQuery(hql, DeliveryNoteEntity.class);
            query.setParameter("clientId", clientId);
            query.setParameter("toDate", toDate);
            return query.list();
        } catch (Exception e) {
            System.err.println("Error getting delivery notes for client: " + e.getMessage());
            throw e;
        }
    }

    public List<DeliveryNoteEntity> findByInvoiceId(Integer invoiceId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM DeliveryNoteEntity d WHERE d.invoiceNumber = :invoiceId";
            Query<DeliveryNoteEntity> query = session.createQuery(hql, DeliveryNoteEntity.class);
            query.setParameter("invoiceId", invoiceId);
            return query.list();
        } catch (Exception e) {
            System.err.println("Error getting delivery notes for invoice: " + e.getMessage());
            throw e;
        }
    }

    public Double getTotalAmountByInvoiceId(Integer invoiceId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT SUM(d.totalAmount) FROM DeliveryNoteEntity d WHERE d.invoiceNumber = :invoiceId";
            Query<Double> query = session.createQuery(hql, Double.class);
            query.setParameter("invoiceId", invoiceId);
            return query.uniqueResult();
        } catch (Exception e) {
            System.err.println("Error calculating total amount for invoice: " + e.getMessage());
            throw e;
        }
    }
}
