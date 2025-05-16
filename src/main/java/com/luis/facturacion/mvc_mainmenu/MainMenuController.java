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
        appController.showArticleView();
    }

    @FXML
    public void handleClientsClick(javafx.event.ActionEvent actionEvent) {
        System.out.println("Clients selected");
        appController.showClientView();
    }

    @FXML
    public void handleVATConfigClick(javafx.event.ActionEvent actionEvent) {
        System.out.println("VAT Config selected");
        appController.showVatConfigView();
    }

    @FXML
    public void handleDeliveryNoteClick(ActionEvent actionEvent) {
        System.out.println("Delivery-Note Selected");
        appController.showDeliveryNoteView();
    }

    @FXML
    public void handleDeliveryNoteListClick(ActionEvent actionEvent) {
        System.out.println("Delivery-Notes-List Selected");
        appController.showDeliveryNoteListView();
    }

    @FXML
    public void handleInvoiceClick(ActionEvent actionEvent) {
        System.out.println("Create-Invoice Selected");
        appController.showInvoiceView();
    }

    @FXML
    public void handleInvoiceListClick(ActionEvent actionEvent) {
        System.out.println("Invoice-List selected");
        appController.showInvoiceListView();
    }

    @FXML
    public void handleCorrectiveInvoiceClick(ActionEvent actionEvent) {
        System.out.println("Corrective-Invoice selected");
        appController.showCorrectiveInvoiceView();
    }
}
