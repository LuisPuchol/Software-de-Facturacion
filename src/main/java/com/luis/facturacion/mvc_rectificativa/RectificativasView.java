package com.luis.facturacion.mvc_rectificativa;

import com.luis.facturacion.AppController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RectificativasView {
    private RectificativaController rectificativaController;
    private AppController appController;

    public RectificativasView(AppController appController) {
        System.out.println("RectificativaView created");
        this.appController = appController;
    }

    public void show(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/luis/facturacion/rectificativa.fxml"));
            Parent root = loader.load();

            rectificativaController = loader.getController();
            rectificativaController.setAppController(appController);

            Scene scene = new Scene(root, 1000, 600);
            primaryStage.setTitle("Gestión de Facturas Rectificativas");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
