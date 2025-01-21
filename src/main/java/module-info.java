module com.luis.facturacion {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.luis.facturacion to javafx.fxml;
    opens com.luis.facturacion.controller to javafx.fxml;
    opens com.luis.facturacion.view to javafx.fxml;

    exports com.luis.facturacion;
    exports com.luis.facturacion.view;
}