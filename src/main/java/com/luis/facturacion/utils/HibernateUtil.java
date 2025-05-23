package com.luis.facturacion.utils;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class HibernateUtil {
    private static SessionFactory sessionFactory;
    private static boolean initialized = false;

    public static synchronized void initializeDatabase() {
        if (initialized) {
            return;
        }

        try {
            // 1: Create DDBB
            createDatabaseIfNotExists();

            // 2: Start Hibernate
            initializeHibernate();

            initialized = true;
            System.out.println("Base de datos y Hibernate inicializados correctamente");

        } catch (Exception e) {
            System.err.println("Error al inicializar la base de datos: " + e.getMessage());
            ShowAlert.showError("Database Error",
                    "No se pudo inicializar la base de datos: " + e.getMessage() +
                            "\n\nVerifica que MySQL esté instalado y funcionando.");
            throw new RuntimeException("Failed to initialize database", e);
        }
    }

    private static void createDatabaseIfNotExists() throws SQLException {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
                "root", "1234")) {

            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS puchol CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci");
                System.out.println("Base de datos 'puchol' verificada/creada");
            }
        }
    }

    private static void initializeHibernate() {
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            sessionFactory = configuration.buildSessionFactory();
            System.out.println("Hibernate inicializado correctamente");
        } catch (Throwable ex) {
            System.err.println("Error al inicializar Hibernate: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        if (!initialized || sessionFactory == null) {
            throw new IllegalStateException(
                    "HibernateUtil no ha sido inicializado. Llama a initializeDatabase() primero.");
        }
        return sessionFactory;
    }

    public static boolean isMySQLAvailable() {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
                "root", "1234")) {
            return true;
        } catch (SQLException e) {
            return false;
        }
    }


    /**
     * Only call this method at the end
     */
    public static void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }
}
