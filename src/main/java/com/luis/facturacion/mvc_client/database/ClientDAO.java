package com.luis.facturacion.mvc_client.database;
import com.luis.facturacion.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class ClientDAO {

    public ClientDAO() {
        System.out.println("ClientDAO created");
    }

    public void save(ClientEntity cliente) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            session.persist(cliente);

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

    public void update(ClientEntity cliente) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            session.merge(cliente);

            transaction.commit();
        } catch (Exception e) {
            System.err.println("Error saving client: " + e.getMessage());
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

    public void delete(ClientEntity cliente) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            if (cliente.getId() != null) {
                cliente = session.get(ClientEntity.class, cliente.getId());
                if (cliente != null) {
                    session.remove(cliente);
                }
            }

            transaction.commit();
        } catch (Exception e) {
            System.err.println("Error deleting Client: " + e.getMessage());
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

    public ClientEntity getById(int id) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            return session.get(ClientEntity.class, id);
        } catch (Exception e) {
            System.err.println("Error obtaining ID: " + e.getMessage());
            throw e;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public String getNameByID(int id) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            ClientEntity cliente = session.get(ClientEntity.class, id);
            return (cliente != null) ? cliente.getName() : null;
        } catch (Exception e) {
            System.err.println("Error obtaining name by ID: " + e.getMessage());
            throw e;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public List<ClientEntity> getAll() {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            return session.createQuery("FROM ClientEntity", ClientEntity.class).list();
        } catch (Exception e) {
            System.err.println("Error obtaining all Clients: " + e.getMessage());
            throw e;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}