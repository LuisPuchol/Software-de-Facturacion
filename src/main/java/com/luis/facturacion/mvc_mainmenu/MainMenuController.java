package com.luis.facturacion.mvc_mainmenu;

import com.luis.facturacion.AppController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.InputStream;

public class MainMenuController {
    private AppController appController;
    private MainMenuController mainMenuController;
    private Stage primaryStage;

    @FXML
    private ImageView backgroundImage;

    public MainMenuController() {
        System.out.println("MainMenu Controller created");
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    @FXML
    public void initialize() {
        createDefaultBackground();
        setupFullScreen();
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


    private void createDefaultBackground() {
        backgroundImage.getParent().setStyle("-fx-background-color: linear-gradient(to bottom, #e3f2fd, #bbdefb);");
        System.out.println("✅ Usando fondo por defecto");
    }

    private void setupFullScreen() {
        Platform.runLater(() -> {
            try {
                Stage stage = (Stage) backgroundImage.getScene().getWindow();

                if (stage != null) {
                    stage.setMaximized(true);

                    Scene scene = stage.getScene();
                    backgroundImage.fitWidthProperty().bind(scene.widthProperty());
                    backgroundImage.fitHeightProperty().bind(scene.heightProperty());

                    System.out.println("✅ Ventana configurada para pantalla completa");
                }
            } catch (Exception e) {
                System.err.println("Error configurando pantalla completa: " + e.getMessage());
            }
        });
    }
}
