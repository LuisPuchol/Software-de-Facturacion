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

    // Apertura para todas las vistas y controladores
    opens com.luis.facturacion.mvc_articulo to javafx.fxml;
    opens com.luis.facturacion.mvc_articulo.database.entitiesfx to javafx.fxml;
    opens com.luis.facturacion.mvc_login to javafx.fxml;
    opens com.luis.facturacion.mvc_mainmenu to javafx.fxml;
    opens com.luis.facturacion.mvc_albaran to javafx.fxml;
    opens com.luis.facturacion.mvc_client to javafx.fxml;
    opens com.luis.facturacion.mvc_facturacion to javafx.fxml;
    opens com.luis.facturacion.mvc_resumen to javafx.fxml;
    opens com.luis.facturacion.mvc_articulo.database.entities_hibernate to org.hibernate.orm.core;

    // Exportación para el AppController y las clases principales
    exports com.luis.facturacion;
    exports com.luis.facturacion.mvc_articulo;
    exports com.luis.facturacion.mvc_login;
    exports com.luis.facturacion.mvc_mainmenu;
    exports com.luis.facturacion.mvc_albaran;
    exports com.luis.facturacion.mvc_client;
    exports com.luis.facturacion.mvc_facturacion;
    exports com.luis.facturacion.mvc_resumen;
}
