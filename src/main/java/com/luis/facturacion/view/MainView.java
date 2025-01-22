package com.luis.facturacion.view;

import com.luis.facturacion.controller.MainMenuController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainView {
private MainMenuController mainMenuController;
    public MainView(MainMenuController mainMenuController) {
        //this.mainMenuController = mainMenuController;
    }

    public void startMainMenu(Stage primaryStage) {
        // Configurar la ventana principal
        BorderPane root = new BorderPane();
        MenuBar menuBar = new MenuBar();

        // Añadir los menús principales
        menuBar.getMenus().addAll(
                HandlersMenu.createArchivosMenu(),
                HandlersMenu.createEmitirAlbaranMenu(),
                HandlersMenu.createFacturacionMenu(),
                HandlersMenu.createResumenMenu()
        );

        // Configurar el layout
        root.setTop(menuBar);

        // Mostrar la ventana
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Sistema de Facturación");

        primaryStage.setScene(scene);
        primaryStage.show();
    }


}

