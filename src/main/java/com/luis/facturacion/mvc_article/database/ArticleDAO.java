package com.luis.facturacion.mvc_article.database;

import com.luis.facturacion.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ArticleDAO {

    public ArticleDAO() {
        System.out.println("ArticleDAO created");
    }

    public void save(ArticleEntity article) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            // Save
            session.persist(article);

            // Confirm
            transaction.commit();
        } catch (Exception e) {
            System.err.println("Error saving article: " + e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e; // Re-throw the exception to handle it at the higher level
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public void update(ArticleEntity article) {

    }

    public void delete(ArticleEntity article) {

    }

    public ArticleEntity getById(int id) {
        return null;
    }

    public List<ArticleEntity> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM ArticleEntity", ArticleEntity.class).list();
        }
    }

    public String getProductNameById(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            ArticleEntity articulo = session.get(ArticleEntity.class, id);
            return (articulo != null) ? articulo.getName() : null;
        }
    }
}
