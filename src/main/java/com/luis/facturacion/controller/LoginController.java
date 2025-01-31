package com.luis.facturacion.controller;

import com.luis.facturacion.model.Model;
import com.luis.facturacion.view.LoginView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class LoginController {
    private AppController appController;

    public LoginController() {
        System.out.println("Login Controller created");
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    @FXML
    private void handleUserClick(MouseEvent event) {
        // Llamar al método para mostrar el menú principal
        appController.showMainMenuView();
    }
}
