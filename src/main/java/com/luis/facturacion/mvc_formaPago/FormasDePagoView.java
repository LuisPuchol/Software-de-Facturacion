package com.luis.facturacion.mvc_formaPago;

import com.luis.facturacion.AppController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FormasDePagoView {
    private FormaDePagoController formaPagoController;
    private AppController appController;

    public FormasDePagoView(AppController appController) {
        System.out.println("FormaDePagoView created");
        this.appController = appController;
    }

    public void show(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/luis/facturacion/formapago.fxml"));
            Parent root = loader.load();

            formaPagoController = loader.getController();
            formaPagoController.setAppController(appController);

            Scene scene = new Scene(root, 1000, 600);
            primaryStage.setTitle("Gestión de Formas de Pago");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
