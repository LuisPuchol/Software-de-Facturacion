package com.luis.facturacion.view;

import com.luis.facturacion.controller.AppController;
import com.luis.facturacion.controller.MainMenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
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

            Scene scene = new Scene(root, 800, 600);
            primaryStage.setTitle("Sistema de Facturación");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

