package com.luis.facturacion.mvc_client;

import com.luis.facturacion.AppController;
import com.luis.facturacion.mvc_client.database.ClienteEntity;
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

public class ClienteController {
    private ClienteModel clienteModel;
    private AppController appController;
    private TabFunction tabFunction;

    @FXML
    private BorderPane rootPane;
    @FXML
    private TableView<ClienteEntity> clientesTable;
    @FXML
    private TableColumn<ClienteEntity, Integer> idClienteColumn;
    @FXML
    private TableColumn<ClienteEntity, Integer> indClienteColumn;
    @FXML
    private TableColumn<ClienteEntity, String> nombreClienteColumn;

    @FXML
    private TextField idClienteField, indClienteField, nombreField, direccionField,
            cpField, poblacionField, provinciaField,
            cifField, telField, tel2Field;
    @FXML
    private CheckBox reqEquivalenciaCheck, facturaAlbaranCheck;
    @FXML
    private ComboBox<String> tipoClienteCombo;

    @FXML
    private Button nuevoButton, editarButton, eliminarButton, salirButton;

    public ClienteController() {
        System.out.println("Cliente created");
        this.clienteModel = ClienteModel.getInstance();
        tabFunction = new TabFunction();
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
        clienteModel.setController(this);
    }

    @FXML
    public void initialize() {
        idClienteColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        indClienteColumn.setCellValueFactory(new PropertyValueFactory<>("ind"));
        nombreClienteColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tipoClienteCombo.getItems().addAll("BASE", "BASE + IVA");

        tabFunction.configureTabFunction((GridPane) rootPane.getCenter());

        cargarDatos();

        // Listener para selección de tabla
        clientesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                mostrarDetallesCliente(newSelection);
            }
        });
    }

    private void mostrarDetallesCliente(ClienteEntity cliente) {
        idClienteField.setText(String.valueOf(cliente.getId()));
        indClienteField.setText(String.valueOf(cliente.getInd()));
        nombreField.setText(cliente.getNombre());
        direccionField.setText(cliente.getDireccion());
        cpField.setText(cliente.getCodPostal());
        poblacionField.setText(cliente.getPoblacion());
        provinciaField.setText(cliente.getProvincia());
        cifField.setText(cliente.getCif());
        telField.setText(cliente.getTel());
        tel2Field.setText(cliente.getTel2());

        // Checkbox y campos TINYINT
        reqEquivalenciaCheck.setSelected(cliente.getReqEquivalencia() == 1);
        // En mostrarDetallesCliente()
        if (cliente.getTipoCliente() == 1) {
            tipoClienteCombo.setValue("BASE");
        } else if (cliente.getTipoCliente() == 2) {
            tipoClienteCombo.setValue("BASE + IVA");
        } else {
            tipoClienteCombo.setValue(null);
        }
        facturaAlbaranCheck.setSelected(cliente.getFacturaPorAlbaran() == 1);
    }

    @FXML
    public void handleNuevoButton(MouseEvent mouseEvent) {
        // Obtener el valor seleccionado del ComboBox
        int tipoCliente = "BASE + IVA".equals(tipoClienteCombo.getValue()) ? 2 : 1;
        clienteModel.addCliente(
                parseInteger(indClienteField.getText()),
                nombreField.getText(),
                direccionField.getText(),
                cpField.getText(),
                poblacionField.getText(),
                provinciaField.getText(),
                cifField.getText(),
                telField.getText(),
                tel2Field.getText(),
                reqEquivalenciaCheck.isSelected() ? 1 : 0,
                tipoCliente,
                facturaAlbaranCheck.isSelected() ? 1 : 0
        );
        limpiarFormulario();
        cargarDatos();
    }

    private void cargarDatos() {
        List<ClienteEntity> clientesBD = clienteModel.loadClientes();
        ObservableList<ClienteEntity> clientesList = FXCollections.observableArrayList(clientesBD);
        clientesTable.setItems(clientesList);
    }

    private Integer parseInteger(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return 0; // Valor por defecto si el campo está vacío o no es un número
        }
    }

    private void limpiarFormulario() {
        idClienteField.clear();
        indClienteField.clear();
        nombreField.clear();
        direccionField.clear();
        cpField.clear();
        poblacionField.clear();
        provinciaField.clear();
        cifField.clear();
        telField.clear();
        tel2Field.clear();
        reqEquivalenciaCheck.setSelected(false);
        tipoClienteCombo.setValue(null);
        facturaAlbaranCheck.setSelected(false);
    }

    @FXML
    public void handleEditarButton(MouseEvent mouseEvent) {
        int tipoCliente = "BASE + IVA".equals(tipoClienteCombo.getValue()) ? 2 : 1;
        ClienteEntity clienteSeleccionado = clientesTable.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado != null) {
            clienteSeleccionado.setInd(parseInteger(indClienteField.getText()));
            clienteSeleccionado.setNombre(nombreField.getText());
            clienteSeleccionado.setDireccion(direccionField.getText());
            clienteSeleccionado.setCodPostal(cpField.getText());
            clienteSeleccionado.setPoblacion(poblacionField.getText());
            clienteSeleccionado.setProvincia(provinciaField.getText());
            clienteSeleccionado.setCif(cifField.getText());
            clienteSeleccionado.setTel(telField.getText());
            clienteSeleccionado.setTel2(tel2Field.getText());
            clienteSeleccionado.setReqEquivalencia(reqEquivalenciaCheck.isSelected() ? 1 : 0);
            //clienteSeleccionado.setTipoCliente(parseInteger(tipoCliente.getText()));
            clienteSeleccionado.setFacturaPorAlbaran(facturaAlbaranCheck.isSelected() ? 1 : 0);

            //clienteModel.updateCliente(clienteSeleccionado);
            cargarDatos();
        }
    }

    @FXML
    public void handleSalirButton(MouseEvent mouseEvent) {
        salirButton.getScene().getWindow().hide();
    }

    public String getClienteById(Integer id) {
        return clienteModel.getClienteById(id);
    }
}