package com.luis.facturacion.mvc_article.database;

import com.luis.facturacion.utils.GlobalDAO;
import com.luis.facturacion.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ArticleDAO extends GlobalDAO<ArticleEntity, Integer> {
    private static final Logger LOGGER = Logger.getLogger(ArticleDAO.class.getName());
    private static ArticleDAO instance;

    private ArticleDAO() {
        super(ArticleEntity.class);
    }

    public static ArticleDAO getInstance() {
        if (instance == null) {
            instance = new ArticleDAO();
        }
        return instance;
    }

    /**
     * Gets an article name by its index (user-defined ID)
     * @param index The user-defined index of the article
     * @return The name of the article, or empty string if not found
     */
    public String getNameByIndex(Integer index) {
        if (index == null) {
            return "";
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<String> query = session.createQuery(
                    "SELECT a.name FROM ArticleEntity a WHERE a.index = :index",
                    String.class);
            query.setParameter("index", index);
            String result = query.uniqueResult();
            return result != null ? result : "";
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error getting article name for index: " + index, e);
            return "";
        }
    }

    /**
     * Gets an article by its index (user-defined ID)
     * @param index The user-defined index of the article
     * @return The ArticleEntity or null if not found
     */
    public ArticleEntity getByIndex(Integer index) {
        if (index == null) {
            return null;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<ArticleEntity> query = session.createQuery(
                    "FROM ArticleEntity a WHERE a.index = :index",
                    ArticleEntity.class);
            query.setParameter("index", index);
            return query.uniqueResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting article by index: " + index, e);
            return null;
        }
    }
}