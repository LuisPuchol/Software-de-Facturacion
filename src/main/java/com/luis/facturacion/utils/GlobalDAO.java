package com.luis.facturacion.utils;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.Serializable;
import java.util.List;

/**
 *
 * TODO: IMPLEMENT INTERFACES TO REDUCE COUPLING
 *
 *
 *
 * DAO class for database operations on entities
 * @param <T> Entity type
 * @param <ID> Type of the entity's primary key
 */
public abstract class GlobalDAO<T, ID extends Serializable> {

    private final Class<T> entityClass;

    public GlobalDAO(Class<T> entityClass) {
        System.out.println(entityClass.getSimpleName() + "DAO created");
        this.entityClass = entityClass;
    }

    /**
     * Save a new entity
     * @param entity Entity to save
     */
    public void save(T entity) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            session.persist(entity);

            transaction.commit();
        } catch (Exception e) {
            System.err.println("Error saving " + entityClass.getSimpleName() + ": " + e.getMessage());
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

    /**
     * Update an entity
     * @param entity Entity to update
     */
    public void update(T entity) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            session.merge(entity);

            transaction.commit();
        } catch (Exception e) {
            System.err.println("Error updating " + entityClass.getSimpleName() + ": " + e.getMessage());
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

    /**
     * Delete an entity
     * @param entity Entity to delete
     */
    public void delete(T entity) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            // This assumes entity has an ID field and that session.get can find it
            session.remove(entity);

            transaction.commit();
        } catch (Exception e) {
            System.err.println("Error deleting " + entityClass.getSimpleName() + ": " + e.getMessage());
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

    /**
     * Find an entity by ID
     * @param id ID of the entity to find
     * @return Entity or null if not found
     */
    public T getById(ID id) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            return session.get(entityClass, id);
        } catch (Exception e) {
            System.err.println("Error obtaining " + entityClass.getSimpleName() + " by ID: " + e.getMessage());
            throw e;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    /**
     * Get all entities of this type
     * @return List of all entities
     */
    public List<T> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM " + entityClass.getSimpleName(), entityClass).list();
        } catch (Exception e) {
            System.err.println("Error obtaining all " + entityClass.getSimpleName() + "s: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Get name of entity by ID (requires getName() method in entity)
     * This is a specialized method that assumes the entity has a getName() method
     * @param id ID of the entity
     * @return Name of the entity or null if not found
     */
    public String getNameById(ID id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            T entity = session.get(entityClass, id);

            if (entity == null) {
                return null;
            }

            try {
                return (String) entity.getClass().getMethod("getName").invoke(entity);
            } catch (Exception e) {
                System.err.println("Error: Entity does not have getName() method: " + e.getMessage());
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error obtaining name by ID: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Find an entity by its index field
     * @param index Index value to search for
     * @return Entity or null if not found
     */
    public T getByIndex(Integer index) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM " + entityClass.getSimpleName() + " e WHERE e.index = :index";
            Query<T> query = session.createQuery(hql, entityClass);
            query.setParameter("index", index);
            return query.uniqueResult();
        } catch (Exception e) {
            System.err.println("Error obtaining " + entityClass.getSimpleName() + " by index: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Get name of entity by index (requires getName() method in entity)
     * @param index Index of the entity
     * @return Name of the entity or null if not found
     */
    public String getNameByIndex(Integer index) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM " + entityClass.getSimpleName() + " e WHERE e.index = :index";
            Query<T> query = session.createQuery(hql, entityClass);
            query.setParameter("index", index);
            T entity = query.uniqueResult();

            if (entity == null) {
                return null;
            }

            try {
                return (String) entity.getClass().getMethod("getName").invoke(entity);
            } catch (Exception e) {
                System.err.println("Error: Entity does not have getName() method: " + e.getMessage());
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error obtaining name by index: " + e.getMessage());
            throw e;
        }
    }
}