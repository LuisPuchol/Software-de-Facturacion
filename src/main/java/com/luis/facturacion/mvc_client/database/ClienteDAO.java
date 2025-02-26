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
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Iniciar la transacción
            transaction = session.beginTransaction();

            // Guardar la entidad
            session.persist(cliente);

            // Confirmar la transacción
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Revertir los cambios si ocurre un error
            }
            e.printStackTrace();
        }
    }

    public void update(ClienteEntity cliente) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Iniciar la transacción
            transaction = session.beginTransaction();

            // Actualizar la entidad
            session.merge(cliente);

            // Confirmar la transacción
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void delete(ClienteEntity cliente) {

    }

    public ClienteEntity getById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(ClienteEntity.class, id);
        }
    }

    public String getNameByID(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            ClienteEntity cliente = session.get(ClienteEntity.class, id);
            return (cliente != null) ? cliente.getNombreCliente() : null;
        }
    }


    public List<ClienteEntity> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM ClienteEntity", ClienteEntity.class).list();
        }
    }
}

