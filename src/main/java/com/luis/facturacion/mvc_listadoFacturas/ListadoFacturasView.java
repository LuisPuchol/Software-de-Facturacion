package com.luis.facturacion.mvc_listadoFacturas;

import com.luis.facturacion.AppController;
import com.luis.facturacion.mvc_factura.FacturaController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ListadoFacturasView {
    private ListadoFacturasController listadoFacturasController;
    private AppController appController;

    public ListadoFacturasView(AppController appController) {
        System.out.println("ListadoFacturasView created");
        this.appController = appController;
    }

    public void show(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/luis/facturacion/listadoFacturas.fxml"));
            Parent root = loader.load();

            listadoFacturasController = loader.getController();
            listadoFacturasController.setAppController(appController);

            Scene scene = new Scene(root, 1000, 600);
            primaryStage.setTitle("Gestión de Facturas");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
