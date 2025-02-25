package com.luis.facturacion.mvc_client;

import com.luis.facturacion.AppController;
import com.luis.facturacion.mvc_client.database.ClienteEntity;
import com.luis.facturacion.mvc_familiaArticulos.FamiliaArticulosController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class ClienteController {
    private ClienteModel clienteModel;
    private AppController appController;

    @FXML
    private TableView<ClienteEntity> clienteTable;
    @FXML
    private TableColumn<ClienteEntity, Integer> columnId;
    @FXML
    private TableColumn<ClienteEntity, String> columnNombre;
    @FXML
    private TableColumn<ClienteEntity, String> columnCif;

    @FXML
    private TextField idField, nombreClienteField, direccionClienteField,
            cpClienteField, poblacionClienteField, provinciaClienteField,
            paisClienteField, cifClienteField, telClienteField,
            emailClienteField, ibanClienteField;
    @FXML
    private TextField riesgoClienteField, descuentoClienteField;
    @FXML
    private TextArea observacionesClienteField;
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

    public void handleNuevoButton(MouseEvent mouseEvent) {
    }

    public void handleEditarButton(MouseEvent mouseEvent) {
    }

    public void handleEliminarButton(MouseEvent mouseEvent) {
    }

    public void handleSalirButton(MouseEvent mouseEvent) {
        salirButton.getScene().getWindow().hide();
    }
}
