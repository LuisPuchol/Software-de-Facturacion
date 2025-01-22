package com.luis.facturacion.controller;

import com.luis.facturacion.model.Model;
import com.luis.facturacion.view.MainView;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class MainMenuController {

    @FXML
    private BorderPane mainPane;
    private Model model;
    private MainView mainView;
    public MainMenuController() {
        this.model = new Model(this);
        this.mainView = new MainView(this);
    }
    @FXML
    private void handleMenuAction() {
        try {
            // Ejemplo: Cargar la vista de clientes
            Parent clientesView = FXMLLoader.load(getClass().getResource("/com/example/facturacion/view/clientes-view.fxml"));
            mainPane.setCenter(clientesView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Método para cambiar a MainView después del login
    public void showMainMenu(Stage stage) {
        mainView.startMainMenu(stage);
    }
}
