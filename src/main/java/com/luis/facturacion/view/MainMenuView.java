package com.luis.facturacion.view;

import com.luis.facturacion.controller.AppController;
import com.luis.facturacion.controller.MainMenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

public class MainMenuView {
private MainMenuController mainMenuController;
private AppController appController;

    public MainMenuView(AppController appController) {
        System.out.println("MainView created");
        this.appController = appController;
    }

    public void show(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/luis/facturacion/main-menu.fxml"));
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

