package com.luis.facturacion.mvc_client;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ClienteController {
    // Tabla de clientes
    @FXML
    private TableView<?> clientesTable;

    @FXML
    private TableColumn<?, Integer> idClienteColumn;

    @FXML
    private TableColumn<?, String> nombreClienteColumn;

    // Campos del formulario
    @FXML
    private TextField idClienteField;

    @FXML
    private TextField nombreField;

    @FXML
    private TextField direccionField;

    @FXML
    private TextField cpField;

    @FXML
    private TextField poblacionField;

    @FXML
    private TextField provinciaField;

    @FXML
    private TextField paisField;

    @FXML
    private TextField cifField;

    @FXML
    private TextField telField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField ibanField;

    @FXML
    private TextField riesgoField;

    @FXML
    private TextField descuentoField;

    @FXML
    private TextArea observacionesField;

    // Botones
    @FXML
    private Button nuevoButton;

    @FXML
    private Button editarButton;

    @FXML
    private Button eliminarButton;

    @FXML
    private Button salirButton;


    @FXML
    private void handleNuevoButton() {

    }

    @FXML
    private void handleEditarButton() {
        //
    }

    @FXML
    private void handleEliminarButton() {
    }


    @FXML
    private void handleSalirButton() {
        // Cerrar la ventana actual
        //salirButton.getScene().getWindow().hide();
    }
}
