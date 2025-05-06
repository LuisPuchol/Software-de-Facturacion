package com.luis.facturacion.mvc_login;

import com.luis.facturacion.AppController;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

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
        appController.showMainMenuView();
    }
}
