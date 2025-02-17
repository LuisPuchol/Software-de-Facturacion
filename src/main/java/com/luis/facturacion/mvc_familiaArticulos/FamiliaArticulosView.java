package com.luis.facturacion.mvc_familiaArticulos;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FamiliaArticulosView {

    public FamiliaArticulosView() {
        System.out.println("ArticulosView created");
    }

    public void show(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/luis/facturacion/FamiliaArticulos.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root, 1000, 600);
            primaryStage.setTitle("Familia de Articulos");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
