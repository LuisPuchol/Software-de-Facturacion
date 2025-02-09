package com.luis.facturacion.mvc_client;

import com.luis.facturacion.mvc_articulo.ArticulosController;
import com.luis.facturacion.mvc_mainmenu.MainMenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClienteView {

    public ClienteView() {
        System.out.println("ArticulosView created");
    }

    public void show(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/luis/facturacion/clientes.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root, 1000, 600);
            primaryStage.setTitle("Gestión de articulos");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
