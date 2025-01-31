package com.luis.facturacion.controller;

import com.luis.facturacion.view.ArticulosView;
import com.luis.facturacion.view.LoginView;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.awt.*;

public class MainMenuController {
    private AppController appController;
    private MainMenuController mainMenuController;
    private Stage primaryStage;

    public MainMenuController() {
        System.out.println("MainMenu Controller created");
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    @FXML
    public void handleArticulosClick(javafx.event.ActionEvent actionEvent) {
        System.out.println("El menú 'Articulos' fue seleccionado.");
        this.showArticuloView();
    }

    public void showArticuloView() {
        Stage stage = new Stage();
        ArticulosView articulosView = new ArticulosView(this); // Crear la vista del login
        articulosView.show(stage); // Mostrarla en el escenario principal
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
