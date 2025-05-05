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
    opens com.luis.facturacion.mvc_tipoIva to javafx.fxml;
    opens com.luis.facturacion.mvc_tipoIva.database to javafx.base, org.hibernate.orm.core;
    opens com.luis.facturacion.mvc_client to javafx.fxml;
    opens com.luis.facturacion.mvc_client.database to javafx.base, org.hibernate.orm.core;

    opens com.luis.facturacion.mvc_deliveryNote to javafx.fxml;

    opens com.luis.facturacion.mvc_factura to javafx.fxml;
    opens com.luis.facturacion.mvc_factura.database to javafx.base, org.hibernate.orm.core;
    opens com.luis.facturacion.mvc_listadoFacturas to javafx.fxml;
    opens com.luis.facturacion.mvc_rectificativa to javafx.fxml;
    opens com.luis.facturacion.mvc_rectificativa.database to javafx.base, org.hibernate.orm.core;

    opens com.luis.facturacion.mvc_resumen to javafx.fxml;
    //opens com.luis.facturacion.mvc_resumen.database to javafx.base, org.hibernate.orm.core;

    // Exportación para el AppController y las clases principales
    exports com.luis.facturacion;
    exports com.luis.facturacion.mvc_article;
    exports com.luis.facturacion.mvc_login;
    exports com.luis.facturacion.mvc_mainmenu;
    exports com.luis.facturacion.mvc_client;
    exports com.luis.facturacion.mvc_factura;
    exports com.luis.facturacion.mvc_resumen;

}
