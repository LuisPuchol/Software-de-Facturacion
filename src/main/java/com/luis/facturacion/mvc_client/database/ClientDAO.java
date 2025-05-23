package com.luis.facturacion.mvc_client.database;

import com.luis.facturacion.utils.GlobalDAO;
import com.luis.facturacion.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientDAO extends GlobalDAO<ClientEntity, Integer> {
    private static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());
    private static ClientDAO instance;

    public ClientDAO() {
        super(ClientEntity.class);
    }

    public static ClientDAO getInstance() {
        if (instance == null) {
            instance = new ClientDAO();
        }
        return instance;
    }

    /**
     * Gets a client name by its index (user-defined ID)
     * @param index The user-defined index of the client
     * @return The name of the client, or empty string if not found
     */
    public String getNameByIndex(Integer index) {
        if (index == null) {
            return "";
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<String> query = session.createQuery(
                    "SELECT c.name FROM ClientEntity c WHERE c.index = :index",
                    String.class);
            query.setParameter("index", index);
            String result = query.uniqueResult();
            return result != null ? result : "";
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error getting client name for index: " + index, e);
            return "";
        }
    }

    /**
     * Gets a client by its index (user-defined ID)
     * @param index The user-defined index of the client
     * @return The ClientEntity or null if not found
     */
    public ClientEntity getByIndex(Integer index) {
        if (index == null) {
            return null;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<ClientEntity> query = session.createQuery(
                    "FROM ClientEntity c WHERE c.index = :index",
                    ClientEntity.class);
            query.setParameter("index", index);
            return query.uniqueResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting client by index: " + index, e);
            return null;
        }
    }
}