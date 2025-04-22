package com.luis.facturacion.mvc_mainmenu;

import com.luis.facturacion.AppController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainMenuView {
private AppController appController;

    public MainMenuView(AppController appController) {
        System.out.println("MainView created");
        this.appController = appController;
    }

    public void show(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/luis/facturacion/mainMenu.fxml"));
            Parent root = loader.load();

            // Obtener el controlador y asignarle el AppController
            MainMenuController mainMenuController = loader.getController();
            mainMenuController.setAppController(appController);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);

            primaryStage.setMaximized(true);
            primaryStage.setTitle("Sistema de Facturación");

            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

