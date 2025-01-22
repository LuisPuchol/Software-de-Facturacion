package com.luis.facturacion.controller;

import com.luis.facturacion.Main;
import com.luis.facturacion.model.Model;
import com.luis.facturacion.view.MainView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

public class LoginController {
    private Model model;
    private MainView mainView;
    private MainMenuController mainMenuController;
    @FXML
    private Button loginButton;

    public LoginController() {
        this.model = new Model(this);
        this.mainMenuController = new MainMenuController();
        //this.mainView = new MainView(this);
    }

    @FXML
    private void handleUserClick(MouseEvent event) {
        // Obtener el Stage actual
        Stage stage = (Stage) ((HBox) event.getSource()).getScene().getWindow();

        // Llamar al método para mostrar el menú principal
        mainMenuController.showMainMenu(stage);
    }
}
