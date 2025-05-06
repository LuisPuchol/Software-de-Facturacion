package com.luis.facturacion.mvc_mainmenu;

import com.luis.facturacion.AppController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

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
    public void handleArticlesClick(javafx.event.ActionEvent actionEvent) {
        System.out.println("Articles selected");
        appController.showArticuloView();
    }

    @FXML
    public void handleClientsClick(javafx.event.ActionEvent actionEvent) {
        System.out.println("Clients selected");
        appController.showClienteView();
    }

    @FXML
    public void handleVATConfigClick(javafx.event.ActionEvent actionEvent) {
        System.out.println("VAT Config selected");
        appController.showTipoDeIvaView();
    }


    @FXML
    public void handleCorrectiveInvoiceClick(ActionEvent actionEvent) {
        System.out.println("Corrective-Invoice selected");
        appController.showRectificativaView();
    }

    @FXML
    public void handleInvoiceListClick(ActionEvent actionEvent) {
        System.out.println("Invoice-List selected");
        appController.showListadoFacturasView();
    }

    @FXML
    public void handleDeliveryNoteClick(ActionEvent actionEvent) {
        System.out.println("Delivery-Note Selected");
        appController.showAlbaranView();
    }

    @FXML
    public void handleInvoiceClick(ActionEvent actionEvent) {
        System.out.println("Creado crearFacturas");
        appController.showFacturaView();
    }
}
