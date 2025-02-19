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
    opens com.luis.facturacion.mvc_login to javafx.fxml;
    //opens com.luis.facturacion.mvc_login.database to javafx.base, org.hibernate.orm.core;
    opens com.luis.facturacion.mvc_mainmenu to javafx.fxml;
    //opens com.luis.facturacion.mvc_mainmenu.database to javafx.base, org.hibernate.orm.core;

    opens com.luis.facturacion.mvc_articulo to javafx.fxml;
    opens com.luis.facturacion.mvc_articulo.database to javafx.base, org.hibernate.orm.core;
    opens com.luis.facturacion.mvc_familiaArticulos to javafx.fxml;
    opens com.luis.facturacion.mvc_familiaArticulos.database to javafx.base, org.hibernate.orm.core;
    opens com.luis.facturacion.mvc_tipoIva to javafx.fxml;
    opens com.luis.facturacion.mvc_tipoIva.database to javafx.base, org.hibernate.orm.core;
    opens com.luis.facturacion.mvc_formaPago to javafx.fxml;
    opens com.luis.facturacion.mvc_formaPago.database to javafx.base, org.hibernate.orm.core;
    opens com.luis.facturacion.mvc_client to javafx.fxml;
    opens com.luis.facturacion.mvc_client.database to javafx.base, org.hibernate.orm.core;
    opens com.luis.facturacion.mvc_factura to javafx.fxml;
    opens com.luis.facturacion.mvc_factura.database to javafx.base, org.hibernate.orm.core;
    opens com.luis.facturacion.mvc_rectificativa to javafx.fxml;
    opens com.luis.facturacion.mvc_rectificativa.database to javafx.base, org.hibernate.orm.core;

    opens com.luis.facturacion.mvc_resumen to javafx.fxml;
    //opens com.luis.facturacion.mvc_resumen.database to javafx.base, org.hibernate.orm.core;

    // Exportación para el AppController y las clases principales
    exports com.luis.facturacion;
    exports com.luis.facturacion.mvc_articulo;
    exports com.luis.facturacion.mvc_login;
    exports com.luis.facturacion.mvc_mainmenu;
    exports com.luis.facturacion.mvc_client;
    exports com.luis.facturacion.mvc_factura;
    exports com.luis.facturacion.mvc_resumen;
    exports com.luis.facturacion.mvc_familiaArticulos;

}
