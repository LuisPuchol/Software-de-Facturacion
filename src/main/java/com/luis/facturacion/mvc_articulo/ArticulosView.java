package com.luis.facturacion.mvc_articulo;

import com.luis.facturacion.mvc_mainmenu.MainMenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ArticulosView {
    private ArticulosController articulosController;
    private MainMenuController mainMenuController;

    public ArticulosView(MainMenuController mainMenuController) {
        System.out.println("ArticulosView created");
        this.mainMenuController = mainMenuController;
    }

    public void show(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/luis/facturacion/articulos.fxml"));
            Parent root = loader.load();

            // Obtener el controlador y asignarle el AppController
            articulosController = loader.getController();
            articulosController.setMainMenuController(mainMenuController);

            Scene scene = new Scene(root, 1000, 600);
            primaryStage.setTitle("Gestión de articulos");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
