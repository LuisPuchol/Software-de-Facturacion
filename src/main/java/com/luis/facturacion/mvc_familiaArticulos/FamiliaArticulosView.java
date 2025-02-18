package com.luis.facturacion.mvc_familiaArticulos;

import com.luis.facturacion.AppController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FamiliaArticulosView {
    private FamiliaArticulosController familiaArticulosController;
    private AppController appController;

    public FamiliaArticulosView(AppController appController) {
        System.out.println("ArticulosView created");
        this.appController = appController;
    }

    public void show(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/luis/facturacion/FamiliaArticulos.fxml"));
            Parent root = loader.load();

            familiaArticulosController = loader.getController();
            familiaArticulosController.setAppController(appController);

            Scene scene = new Scene(root, 1000, 600);
            primaryStage.setTitle("Familia de Articulos");
            primaryStage.setScene(scene);
            primaryStage.show();

            familiaArticulosController.setUpModel(familiaArticulosController);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public FamiliaArticulosController getController() {
        return familiaArticulosController;
    }

}
