package com.luis.facturacion.mvc_client;

import com.luis.facturacion.AppController;
import com.luis.facturacion.mvc_client.database.ClientEntity;
import com.luis.facturacion.utils.TabFunction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.util.List;

public class ClientController {
    public GridPane clientValues;
    private ClientModel clientModel;
    private AppController appController;
    private TabFunction tabFunction;

    @FXML
    private BorderPane rootPane;
    @FXML
    private TableView<ClientEntity> clientsTable;
    @FXML
    private TableColumn<ClientEntity, Integer> clientIdColumn;
    @FXML
    private TableColumn<ClientEntity, Integer> clientIndexColumn;
    @FXML
    private TableColumn<ClientEntity, String> clientNameColumn;

    @FXML
    private TextField clientIdField, clientIndexField, nameField, addressField,
            postalCodeField, cityField, provinceField,
            cifField, telField, tel2Field;
    @FXML
    private CheckBox equivalenceSurchargeCheck, invoiceByDeliveryNoteCheck;
    @FXML
    private ComboBox<String> clientTypeCombo;

    @FXML
    private Button newButton, editButton, deleteButton, exitButton;

    public ClientController() {
        System.out.println("Client Controller created");
        this.clientModel = ClientModel.getInstance();
        tabFunction = new TabFunction();
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
        clientModel.setController(this);
    }

    @FXML
    public void initialize() {
        clientIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        clientIndexColumn.setCellValueFactory(new PropertyValueFactory<>("index"));
        clientNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        clientTypeCombo.getItems().addAll("BASE", "BASE + IVA");

        tabFunction.configureTabFunction((GridPane) rootPane.getCenter());

        loadTableData();

        // Listener for table selection
        clientsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                showClientDetails(newSelection);
            }
        });
    }

    private void showClientDetails(ClientEntity cliente) {
        clientIdField.setText(String.valueOf(cliente.getId()));
        clientIndexField.setText(String.valueOf(cliente.getIndex()));
        nameField.setText(cliente.getName());
        addressField.setText(cliente.getAddress());
        postalCodeField.setText(cliente.getPostalCode());
        cityField.setText(cliente.getCity());
        provinceField.setText(cliente.getProvince());
        cifField.setText(cliente.getCif());
        telField.setText(cliente.getTel());
        tel2Field.setText(cliente.getTel2());

        // Checkbox & TINYINT fields
        equivalenceSurchargeCheck.setSelected(cliente.getEquivalenceSurcharge() == 1);
        // in showClientDetails()
        if (cliente.getClientType() == 1) {
            clientTypeCombo.setValue("BASE");
        } else if (cliente.getClientType() == 2) {
            clientTypeCombo.setValue("BASE + IVA");
        } else {
            clientTypeCombo.setValue(null);
        }
        invoiceByDeliveryNoteCheck.setSelected(cliente.getInvoiceByDeliveryNote() == 1);

    }

    @FXML
    public void handleNewButton(MouseEvent mouseEvent) {
        int tipoCliente = "BASE + IVA".equals(clientTypeCombo.getValue()) ? 2 : 1;

        boolean success = clientModel.addCliente(
                parseInteger(clientIndexField.getText()),
                nameField.getText(),
                addressField.getText(),
                postalCodeField.getText(),
                cityField.getText(),
                provinceField.getText(),
                cifField.getText(),
                telField.getText(),
                tel2Field.getText(),
                equivalenceSurchargeCheck.isSelected() ? 1 : 0,
                tipoCliente,
                invoiceByDeliveryNoteCheck.isSelected() ? 1 : 0
        );

        if (success) {
            cleanForm();
            loadTableData();
        }
    }

    private void loadTableData() {
        List<ClientEntity> clientesBD = clientModel.loadClientes();
        ObservableList<ClientEntity> clientesList = FXCollections.observableArrayList(clientesBD);
        clientsTable.setItems(clientesList);
    }

    private Integer parseInteger(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return 0; // empty field value
        }
    }

    private void cleanForm() {
        clientIdField.clear();
        clientIndexField.clear();
        nameField.clear();
        addressField.clear();
        postalCodeField.clear();
        cityField.clear();
        provinceField.clear();
        cifField.clear();
        telField.clear();
        tel2Field.clear();
        equivalenceSurchargeCheck.setSelected(false);
        clientTypeCombo.setValue(null);
        invoiceByDeliveryNoteCheck.setSelected(false);
    }

    @FXML
    public void handleEditButton(MouseEvent mouseEvent) {
        //int clientType = "BASE + IVA".equals(clientTypeCombo.getValue()) ? 2 : 1;
        ClientEntity selectedClient = clientsTable.getSelectionModel().getSelectedItem();
        if (selectedClient != null) {
            selectedClient.setIndex(parseInteger(clientIndexField.getText()));
            selectedClient.setName(nameField.getText());
            selectedClient.setAddress(addressField.getText());
            selectedClient.setPostalCode(postalCodeField.getText());
            selectedClient.setCity(cityField.getText());
            selectedClient.setProvince(provinceField.getText());
            selectedClient.setCif(cifField.getText());
            selectedClient.setTel(telField.getText());
            selectedClient.setTel2(tel2Field.getText());
            selectedClient.setEquivalenceSurcharge(equivalenceSurchargeCheck.isSelected() ? 1 : 0);
            //selectedClient.setClientType(parseInteger(clientType.getText()));
            selectedClient.setInvoiceByDeliveryNote(invoiceByDeliveryNoteCheck.isSelected() ? 1 : 0);

            //clientModel.updateClient(selectedClient);
            loadTableData();
        }
    }

    @FXML
    public void handleExitButton(MouseEvent mouseEvent) {
        exitButton.getScene().getWindow().hide();
    }

    public String getClienteById(Integer id) {
        return clientModel.getClienteById(id);
    }
}