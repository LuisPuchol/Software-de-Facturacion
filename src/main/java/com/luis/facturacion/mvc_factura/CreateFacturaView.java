package com.luis.facturacion.mvc_factura;

import com.luis.facturacion.AppController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CreateFacturaView {
    private CreateFacturaController createFacturaController;
    private AppController appController;
    private Stage primaryStage;

    public CreateFacturaView(AppController appController) {
        System.out.println("CreateFacturaView created");
        this.appController = appController;
    }

    public void show(Stage primaryStage) {
        this.primaryStage = primaryStage;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/luis/facturacion/lineasFactura.fxml"));
            Parent root = loader.load();

            createFacturaController = loader.getController();
            createFacturaController.setAppController(appController);

            Scene scene = new Scene(root, 1000, 600);
            primaryStage.setTitle("Gestión de Facturas");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }}
