package com.luis.facturacion.controller;

import com.luis.facturacion.model.Model;
import com.luis.facturacion.view.LoginView;
import com.luis.facturacion.view.MainMenuView;
import javafx.stage.Stage;

public class AppController {
    private Stage primaryStage;


    public AppController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void showLoginView() {
        LoginView loginView = new LoginView(this); // Crear la vista del login
        loginView.show(primaryStage); // Mostrarla en el escenario principal
    }

    public void showMainMenuView() {
        MainMenuView mainMenuView = new MainMenuView(this); // Crear la vista del menú principal
        mainMenuView.show(primaryStage); // Mostrarla en el escenario principal
    }

    public Stage getStage () {
        return this.primaryStage;
    }
}
