package com.luis.facturacion.mvc_invoice.database;

import com.luis.facturacion.utils.GlobalDAO;
import com.luis.facturacion.utils.HibernateUtil;
import org.hibernate.Session;

import org.hibernate.query.Query;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.LocalDate;
import java.util.List;

public class InvoiceDAO extends GlobalDAO<InvoiceEntity, Integer> {
    private static final Logger LOGGER = Logger.getLogger(InvoiceDAO.class.getName());

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
     * Gets the next invoice number for creating new invoices
     * @return Next available invoice number
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
            LOGGER.log(Level.SEVERE, "Error obtaining next invoice number", e);
            throw new RuntimeException("Error obtaining next invoice number", e);
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

    /**
     * Gets an invoice by its ID (since invoices don't have separate index field)
     * This overrides the parent method to use the primary key ID instead of index
     * @param id The ID of the invoice
     * @return The InvoiceEntity or null if not found
     */
    @Override
    public InvoiceEntity getByIndex(Integer id) {
        return getById(id); // For invoices, index == id
    }

    /**
     * Gets an invoice by its primary key ID
     * @param id The primary key ID of the invoice
     * @return The InvoiceEntity or null if not found
     */
    public InvoiceEntity getById(Integer id) {
        if (id == null) {
            return null;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(InvoiceEntity.class, id);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting invoice by ID: " + id, e);
            return null;
        }
    }


}