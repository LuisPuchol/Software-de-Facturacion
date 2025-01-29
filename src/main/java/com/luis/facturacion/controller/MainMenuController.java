package com.luis.facturacion.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.ActionEvent;

public class MainMenuController {
    private AppController appController;

    public MainMenuController() {

    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    @FXML
    public void handleArticulosClick(javafx.event.ActionEvent actionEvent) {
        System.out.println("El menú 'Clientes' fue seleccionado.");
    }

    @FXML
    public void handleClientesClick(javafx.event.ActionEvent actionEvent) {
        System.out.println("El menú 'Clientes' fue seleccionado.");
    }

    @FXML
    public void handleConfigIVA(javafx.event.ActionEvent actionEvent) {
        System.out.println("El menú 'Configuración IVA' fue seleccionado.");
    }
}
