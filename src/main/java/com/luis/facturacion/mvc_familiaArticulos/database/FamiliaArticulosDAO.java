package com.luis.facturacion.mvc_familiaArticulos.database;

import com.luis.facturacion.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class FamiliaArticulosDAO {

    public FamiliaArticulosDAO() {
        System.out.println("FamiliaArticulosDAO creado");
    }

    public void save(FamiliaArticulosEntity familiaArticulos) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.persist(familiaArticulos);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void update(FamiliaArticulosEntity familiaArticulos) {

    }

    public void delete(FamiliaArticulosEntity familiaArticulos) {

    }

    //public FamiliaArticulosEntity getById(int id) {

    //}

    public List<FamiliaArticulosEntity> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("From FamiliaArticulosEntity", FamiliaArticulosEntity.class).list();
        }
    }
}
