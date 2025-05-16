module com.luis.facturacion {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.desktop;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.sql;
    requires kernel;
    requires layout;

    // Login
    opens com.luis.facturacion.mvc_login to javafx.fxml, javafx.base;
    exports com.luis.facturacion.mvc_login;

    // Main Menu
    opens com.luis.facturacion.mvc_mainmenu to javafx.fxml, javafx.base;
    exports com.luis.facturacion.mvc_mainmenu;

    // Article
    opens com.luis.facturacion.mvc_article to javafx.fxml, javafx.base;
    opens com.luis.facturacion.mvc_article.database to javafx.base, org.hibernate.orm.core;
    exports com.luis.facturacion.mvc_article;

    // Client
    opens com.luis.facturacion.mvc_client to javafx.fxml, javafx.base;
    opens com.luis.facturacion.mvc_client.database to javafx.base, org.hibernate.orm.core;
    exports com.luis.facturacion.mvc_client;

    // VAT Config
    opens com.luis.facturacion.mvc_vatConfig to javafx.fxml, javafx.base;
    opens com.luis.facturacion.mvc_vatConfig.database to javafx.base, org.hibernate.orm.core;

    // Delivery Notes
    opens com.luis.facturacion.mvc_deliveryNote to javafx.fxml, javafx.base, org.hibernate.orm.core;
    opens com.luis.facturacion.mvc_deliveryNoteList to javafx.fxml, javafx.base;

    // Invoices
    opens com.luis.facturacion.mvc_invoice to javafx.fxml, javafx.base;
    opens com.luis.facturacion.mvc_invoice.database to javafx.base, org.hibernate.orm.core;
    opens com.luis.facturacion.mvc_invoiceList to javafx.fxml, javafx.base;
    exports com.luis.facturacion.mvc_invoice;

    // Corrective Invoices
    opens com.luis.facturacion.mvc_correctiveInvoice to javafx.fxml, javafx.base;
    opens com.luis.facturacion.mvc_correctiveInvoice.database to javafx.base, org.hibernate.orm.core;
    exports com.luis.facturacion.mvc_invoiceList;

    // Summary
    opens com.luis.facturacion.mvc_summary to javafx.fxml, javafx.base;
    exports com.luis.facturacion.mvc_summary;

    // Utils
    exports com.luis.facturacion.utils;

    // App base
    exports com.luis.facturacion;
    opens com.luis.facturacion.utils to javafx.base, javafx.fxml, org.hibernate.orm.core;
}
