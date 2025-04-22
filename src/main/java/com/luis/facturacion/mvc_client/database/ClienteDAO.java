package com.luis.facturacion.mvc_client.database;
import com.luis.facturacion.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class ClienteDAO {

    public ClienteDAO() {
        System.out.println("ClienteDAO creado");
    }

    public void save(ClienteEntity cliente) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            // Guardar la entidad
            session.persist(cliente);

            // Confirmar la transacción
            transaction.commit();
        } catch (Exception e) {
            System.err.println("Error al guardar cliente: " + e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e; // Re-lanzar la excepción para manejarla en el nivel superior
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public void update(ClienteEntity cliente) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            // Actualizar la entidad
            session.merge(cliente);

            // Confirmar la transacción
            transaction.commit();
        } catch (Exception e) {
            System.err.println("Error al actualizar cliente: " + e.getMessage());
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

    public void delete(ClienteEntity cliente) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            // Si el cliente está "detached", primero lo cargamos
            if (cliente.getId() != null) {
                cliente = session.get(ClienteEntity.class, cliente.getId());
                if (cliente != null) {
                    session.remove(cliente);
                }
            }

            // Confirmar la transacción
            transaction.commit();
        } catch (Exception e) {
            System.err.println("Error al eliminar cliente: " + e.getMessage());
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

    public ClienteEntity getById(int id) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            return session.get(ClienteEntity.class, id);
        } catch (Exception e) {
            System.err.println("Error al obtener cliente por ID: " + e.getMessage());
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
            ClienteEntity cliente = session.get(ClienteEntity.class, id);
            return (cliente != null) ? cliente.getNombre() : null;
        } catch (Exception e) {
            System.err.println("Error al obtener nombre de cliente por ID: " + e.getMessage());
            throw e;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public List<ClienteEntity> getAll() {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            return session.createQuery("FROM ClienteEntity", ClienteEntity.class).list();
        } catch (Exception e) {
            System.err.println("Error al obtener todos los clientes: " + e.getMessage());
            throw e;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}