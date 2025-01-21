package com.luis.facturacion.controller;

import com.luis.facturacion.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

public class LoginController {

    @FXML
    private Button loginButton;

    @FXML
    private void handleUserClick(MouseEvent event) {
        // Obtener el Stage actual
        Stage stage = (Stage) ((HBox) event.getSource()).getScene().getWindow();

        // Llamar al método para mostrar el menú principal
        Main mainApp = new Main();
        mainApp.showMainMenu(stage);
    }
}
