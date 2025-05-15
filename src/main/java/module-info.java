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

    // Apertura para todas las vistas y controladores
    opens com.luis.facturacion.mvc_login to javafx.fxml;
    //opens com.luis.facturacion.mvc_login.database to javafx.base, org.hibernate.orm.core;
    opens com.luis.facturacion.mvc_mainmenu to javafx.fxml;
    //opens com.luis.facturacion.mvc_mainmenu.database to javafx.base, org.hibernate.orm.core;

    opens com.luis.facturacion.mvc_article to javafx.fxml;
    opens com.luis.facturacion.mvc_article.database to javafx.base, org.hibernate.orm.core;
    opens com.luis.facturacion.mvc_vatConfig to javafx.fxml;
    opens com.luis.facturacion.mvc_vatConfig.database to javafx.base, org.hibernate.orm.core;
    opens com.luis.facturacion.mvc_client to javafx.fxml;
    opens com.luis.facturacion.mvc_client.database to javafx.base, org.hibernate.orm.core;
    opens com.luis.facturacion.mvc_deliveryNote to javafx.fxml, javafx.base, org.hibernate.orm.core;
    opens com.luis.facturacion.mvc_deliveryNoteList to javafx.fxml, javafx.base;
    opens com.luis.facturacion.utils to org.hibernate.orm.core;


    opens com.luis.facturacion.mvc_invoice to javafx.fxml;
    opens com.luis.facturacion.mvc_invoice.database to javafx.base, org.hibernate.orm.core;
    opens com.luis.facturacion.mvc_invoiceList to javafx.fxml;
    opens com.luis.facturacion.mvc_correctiveInvoice to javafx.fxml;
    opens com.luis.facturacion.mvc_correctiveInvoice.database to javafx.base, org.hibernate.orm.core;

    opens com.luis.facturacion.mvc_summary to javafx.fxml;
    //opens com.luis.facturacion.mvc_summary.database to javafx.base, org.hibernate.orm.core;

    // Exportación para el AppController y las clases principales
    exports com.luis.facturacion;
    exports com.luis.facturacion.mvc_article;
    exports com.luis.facturacion.mvc_login;
    exports com.luis.facturacion.mvc_mainmenu;
    exports com.luis.facturacion.mvc_client;
    exports com.luis.facturacion.mvc_invoice;
    exports com.luis.facturacion.mvc_summary;
    exports com.luis.facturacion.utils;

}
