module com.luis.facturacion {
    // ===== REQUIRES =====
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires java.naming;

    // Hibernate/JPA
    requires jakarta.persistence;
    requires org.hibernate.orm.core;

    // PDF generation
    requires kernel;
    requires layout;
    requires pdfbox;

    // Opcional - solo si realmente los usas
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    // ===== EXPORTS =====
    exports com.luis.facturacion;

    // Controllers
    exports com.luis.facturacion.mvc_login;
    exports com.luis.facturacion.mvc_mainmenu;
    exports com.luis.facturacion.mvc_article;
    exports com.luis.facturacion.mvc_client;
    exports com.luis.facturacion.mvc_invoice;
    exports com.luis.facturacion.mvc_invoiceList;
    exports com.luis.facturacion.mvc_summary;

    // Utils
    exports com.luis.facturacion.utils;
    exports com.luis.facturacion.utils.pdf;

    // ===== OPENS =====
    // FXML Controllers
    opens com.luis.facturacion.mvc_login to javafx.fxml, javafx.base;
    opens com.luis.facturacion.mvc_mainmenu to javafx.fxml, javafx.base;
    opens com.luis.facturacion.mvc_article to javafx.fxml, javafx.base;
    opens com.luis.facturacion.mvc_client to javafx.fxml, javafx.base;
    opens com.luis.facturacion.mvc_vatConfig to javafx.fxml, javafx.base;
    opens com.luis.facturacion.mvc_deliveryNote to javafx.fxml, javafx.base;
    opens com.luis.facturacion.mvc_deliveryNoteList to javafx.fxml, javafx.base;
    opens com.luis.facturacion.mvc_invoice to javafx.fxml, javafx.base;
    opens com.luis.facturacion.mvc_invoiceList to javafx.fxml, javafx.base;
    opens com.luis.facturacion.mvc_correctiveInvoice to javafx.fxml, javafx.base;
    opens com.luis.facturacion.mvc_summary to javafx.fxml, javafx.base;

    // Database entities para Hibernate
    opens com.luis.facturacion.mvc_article.database to org.hibernate.orm.core, javafx.base;
    opens com.luis.facturacion.mvc_client.database to org.hibernate.orm.core, javafx.base;
    opens com.luis.facturacion.mvc_vatConfig.database to org.hibernate.orm.core, javafx.base;
    opens com.luis.facturacion.mvc_deliveryNote.database to org.hibernate.orm.core, javafx.base;
    opens com.luis.facturacion.mvc_invoice.database to org.hibernate.orm.core, javafx.base;
    opens com.luis.facturacion.mvc_correctiveInvoice.database to org.hibernate.orm.core, javafx.base;

    // Utils
    opens com.luis.facturacion.utils to javafx.base, org.hibernate.orm.core;
    opens com.luis.facturacion.utils.pdf to javafx.base;

    // App principal
    opens com.luis.facturacion to javafx.base;
}