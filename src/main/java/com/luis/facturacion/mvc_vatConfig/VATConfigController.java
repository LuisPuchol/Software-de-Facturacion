package com.luis.facturacion.mvc_vatConfig;

import com.luis.facturacion.AppController;
import com.luis.facturacion.mvc_vatConfig.database.VATConfigEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class VATConfigController {

    private VATConfigModel model;
    private AppController appController;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField vatField;

    @FXML
    private TextField surchargeField;

    @FXML
    private Button saveButton;

    /**
     * Constructor for the controller.
     */
    public VATConfigController() {
        this.model = VATConfigModel.getInstance();
    }

    /**
     * Sets the application controller and registers this controller with the model.
     *
     * @param appController The main application controller
     */
    public void setAppController(AppController appController) {
        this.appController = appController;
        model.setController(this);
    }

    /**
     * Initializes UI components and loads any existing configuration.
     */
    @FXML
    public void initialize() {
        loadExistingConfiguration();
    }

    /**
     * Loads existing VAT configuration if available.
     */
    private void loadExistingConfiguration() {
        VATConfigEntity config = model.getCurrentVATConfig();
        if (config != null) {
            vatField.setText(String.valueOf(config.getVatRate()));
            surchargeField.setText(String.valueOf(config.getSurchargeRate()));
        }
    }

    /**
     * Handles the save button action.
     * Validates input and saves the VAT configuration.
     *
     * @param actionEvent The event that triggered this method
     */
    @FXML
    public void handleSave(ActionEvent actionEvent) {
        if (!validateInput()) {
            return;
        }

        double vatRate = Double.parseDouble(vatField.getText().replace(",", "."));
        double surchargeRate = Double.parseDouble(surchargeField.getText().replace(",", "."));

        model.saveVATConfiguration(vatRate, surchargeRate);
        showAlert("Información", "Configuración de IVA guardada correctamente.");
        saveButton.getScene().getWindow().hide();
    }

    /**
     * Validates the input values in the form fields.
     *
     * @return true if input is valid, false otherwise
     */
    private boolean validateInput() {
        String vatText = vatField.getText().trim().replace(",", ".");
        String surchargeText = surchargeField.getText().trim().replace(",", ".");

        if (vatText.isEmpty() || surchargeText.isEmpty()) {
            showAlert("Error", "Ambos campos son obligatorios.");
            return false;
        }

        try {
            double vatRate = Double.parseDouble(vatText);
            double surchargeRate = Double.parseDouble(surchargeText);

            if (vatRate < 0 || vatRate > 100 || surchargeRate < 0 || surchargeRate > 100) {
                showAlert("Error", "Los valores deben estar entre 0 y 100.");
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Por favor, introduzca valores numéricos válidos.");
            return false;
        }

        return true;
    }

    /**
     * Displays an alert dialog with the specified title and message.
     *
     * @param title   The title of the alert
     * @param message The message to display
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}