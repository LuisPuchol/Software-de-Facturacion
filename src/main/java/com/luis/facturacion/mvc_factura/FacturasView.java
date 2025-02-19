package com.luis.facturacion.mvc_factura;

import com.luis.facturacion.AppController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FacturasView {
    private FacturaController facturaController;
    private AppController appController;

    public FacturasView(AppController appController) {
        System.out.println("FacturaView created");
        this.appController = appController;
    }

    public void show(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/luis/facturacion/factura.fxml"));
            Parent root = loader.load();

            facturaController = loader.getController();
            facturaController.setAppController(appController);

            Scene scene = new Scene(root, 1000, 600);
            primaryStage.setTitle("Gestión de Facturas");
            primaryStage.setScene(scene);
            primaryStage.show();

            facturaController.setUpModel(facturaController);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
