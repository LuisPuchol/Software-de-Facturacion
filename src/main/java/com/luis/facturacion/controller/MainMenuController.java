package com.luis.facturacion.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

public class MainMenuController {

    @FXML
    private BorderPane mainPane;

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
}
