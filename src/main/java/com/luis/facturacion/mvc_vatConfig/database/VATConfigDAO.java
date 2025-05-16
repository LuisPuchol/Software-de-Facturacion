package com.luis.facturacion.mvc_vatConfig.database;

import com.luis.facturacion.utils.GlobalDAO;
import com.luis.facturacion.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class VATConfigDAO extends GlobalDAO<VATConfigEntity, Integer> {
    private static VATConfigDAO instance;

    private VATConfigDAO() {
        super(VATConfigEntity.class);
    }

    public static VATConfigDAO getInstance() {
        if (instance == null) {
            instance = new VATConfigDAO();
        }
        return instance;
    }

    /**
     * Gets the current VAT configuration from the database.
     * Since there should only be one configuration, this method returns the first one found.
     *
     * @return The current VAT configuration or null if none exists
     */
    public VATConfigEntity getCurrentConfig() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<VATConfigEntity> query = session.createQuery(
                    "FROM VATConfigEntity",
                    VATConfigEntity.class
            );
            query.setMaxResults(1);
            return query.uniqueResult();
        } catch (Exception e) {
            System.err.println("Error getting VAT configuration: " + e.getMessage());
            return null;
        }
    }

    /**
     * Updates an existing VAT configuration in the database.
     *
     * @param entity The entity to update
     */
    public void update(VATConfigEntity entity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error updating VAT configuration: " + e.getMessage());
            throw new RuntimeException("Error updating VATConfigEntity: " + e.getMessage(), e);
        }
    }
}