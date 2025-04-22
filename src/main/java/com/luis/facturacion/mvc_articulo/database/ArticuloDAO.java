package com.luis.facturacion.mvc_articulo.database;

import com.luis.facturacion.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ArticuloDAO{

    public ArticuloDAO() {
        System.out.println("ArticuloDAO creado");
    }

    public void save(ArticuloEntity articulo) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            // Guardar la entidad
            session.persist(articulo);

            // Confirmar la transacción
            transaction.commit();
        } catch (Exception e) {
            System.err.println("Error al guardar artículo: " + e.getMessage());
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

    public void update(ArticuloEntity articulo) {

    }

    public void delete(ArticuloEntity articulo) {

    }

    //public ArticuloEntity getById(int id) {

    //}

    public List<ArticuloEntity> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM ArticuloEntity", ArticuloEntity.class).list();
        }
    }

    public String getProductNameById(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            ArticuloEntity articulo = session.get(ArticuloEntity.class, id);
            return (articulo != null) ? articulo.getNombre() : null;
        }
    }
}
