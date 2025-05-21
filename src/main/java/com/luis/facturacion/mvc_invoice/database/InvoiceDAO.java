package com.luis.facturacion.mvc_invoice.database;

import com.luis.facturacion.utils.GlobalDAO;
import com.luis.facturacion.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.List;

public class InvoiceDAO extends GlobalDAO<InvoiceEntity, Integer> {
    private static InvoiceDAO instance;

    private InvoiceDAO() {
        super(InvoiceEntity.class);
    }

    public static InvoiceDAO getInstance() {
        if (instance == null) {
            instance = new InvoiceDAO();
        }
        return instance;
    }

    /**
     * Gets the next available invoice number
     *
     * @return The next available invoice number
     */
    public Integer getNextInvoiceNumber() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Number> query = session.createQuery(
                    "SELECT COALESCE(MAX(i.id), 0) FROM InvoiceEntity i",
                    Number.class
            );
            Number maxId = query.getSingleResult();
            return maxId.intValue() + 1;
        } catch (Exception e) {
            System.err.println("Error obtaining the next invoice number: " + e.getMessage());
            throw e;
        }
    }

    public List<InvoiceEntity> findByDateRange(LocalDate fromDate, LocalDate toDate) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM InvoiceEntity i WHERE i.date BETWEEN :fromDate AND :toDate";
            Query<InvoiceEntity> query = session.createQuery(hql, InvoiceEntity.class);
            query.setParameter("fromDate", fromDate);
            query.setParameter("toDate", toDate);
            return query.list();
        } catch (Exception e) {
            System.err.println("Error getting invoices by date range: " + e.getMessage());
            throw e;
        }
    }
}