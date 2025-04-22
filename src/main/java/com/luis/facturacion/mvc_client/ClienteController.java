package com.luis.facturacion.mvc_client;

import com.luis.facturacion.AppController;
import com.luis.facturacion.mvc_articulo.database.ArticuloEntity;
import com.luis.facturacion.mvc_client.database.ClienteEntity;
import com.luis.facturacion.mvc_familiaArticulos.FamiliaArticulosController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class ClienteController {
    private ClienteModel clienteModel;
    private AppController appController;

    @FXML
    private TableView<ClienteEntity> clientesTable;
    @FXML
    private TableColumn<ClienteEntity, Integer> idClienteColumn;
    @FXML
    private TableColumn<ClienteEntity, String> nombreClienteColumn;
    @FXML
    private TableColumn<ClienteEntity, String> cifClienteColumn;

    @FXML
    private TextField idClienteField, nombreField, direccionField,
            cpField, poblacionField, provinciaField,
            paisField, cifField, telField,
            emailField, ibanField;
    @FXML
    private TextField riesgoField, descuentoField;
    @FXML
    private TextArea observacionesField;
    @FXML
    private Button nuevoButton, editarButton, eliminarButton, salirButton;

    public ClienteController() {
        System.out.println("Cliente created");
        this.clienteModel = ClienteModel.getInstance();
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
        clienteModel.setController(this);
    }

    @FXML
    public void initialize() {
        idClienteColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombreClienteColumn.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
        cifClienteColumn.setCellValueFactory(new PropertyValueFactory<>("cifCliente"));

        cargarDatos();
    }

    @FXML
    public void handleNuevoButton(MouseEvent mouseEvent) {
        clienteModel.addCliente(
                nombreField.getText(),
                direccionField.getText(),
                cpField.getText(),
                poblacionField.getText(),
                provinciaField.getText(),
                paisField.getText(),
                cifField.getText(),
                telField.getText(),
                emailField.getText(),
                ibanField.getText(),
                parseDouble(riesgoField.getText()),
                parseDouble(descuentoField.getText()),
                observacionesField.getText()
        );
        limpiarFormulario();
        cargarDatos();
    }

    private void cargarDatos() {
        List<ClienteEntity> articulosBD = clienteModel.loadClientes();
        ObservableList<ClienteEntity> clientesList = FXCollections.observableArrayList(articulosBD);
        clientesTable.setItems(clientesList);
    }

    private double parseDouble(String text) {
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return 0.0; // Valor por defecto si el campo está vacío o no es un número
        }
    }

    private void limpiarFormulario() {
        idClienteField.clear();
        nombreField.clear();
        direccionField.clear();
        cpField.clear();
        poblacionField.clear();
        provinciaField.clear();
        paisField.clear();
        cifField.clear();
        telField.clear();
        emailField.clear();
        ibanField.clear();
        riesgoField.clear();
        descuentoField.clear();
        observacionesField.clear();
    }


    @FXML
    public void handleEditarButton(MouseEvent mouseEvent) {
    }

    @FXML
    public void handleEliminarButton(MouseEvent mouseEvent) {
    }

    @FXML
    public void handleSalirButton(MouseEvent mouseEvent) {
        salirButton.getScene().getWindow().hide();
    }

    public String getClienteById(Integer id) {
        return clienteModel.getClienteById(id);
    }
}
