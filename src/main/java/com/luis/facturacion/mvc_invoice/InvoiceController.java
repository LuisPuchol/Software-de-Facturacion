package com.luis.facturacion.mvc_invoice;

import com.luis.facturacion.AppController;
import com.luis.facturacion.utils.ShowAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.List;

public class InvoiceController {

    private AppController appController;
    private InvoiceModel model;
    private ObservableList<ClientInvoiceItem> clientInvoiceItems = FXCollections.observableArrayList();
    private ObservableList<DeliveryNoteInvoiceItem> deliveryNoteItems = FXCollections.observableArrayList();

    @FXML
    private DatePicker toDateField;

    @FXML
    private Button invoiceButton, exitButton;

    @FXML
    private TableView<ClientInvoiceItem> deliveryNotesTable;

    @FXML
    private TableColumn<ClientInvoiceItem, String> codeColumn, clientNameColumn,
            deliveryNoteCountColumn, amountColumn;

    @FXML
    private TableView<DeliveryNoteInvoiceItem> invoiceDetailsTable;

    @FXML
    private TableColumn<DeliveryNoteInvoiceItem, String> dateColumn,
            deliveryNoteNumberColumn, invoiceAmountColumn;

    /**
     * Constructor for the controller.
     */
    public InvoiceController() {
        this.model = InvoiceModel.getInstance();
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
     * Initializes UI components and configures the table.
     */
    @FXML
    public void initialize() {
        setupClientTable();
        setupDeliveryNoteTable();
        setupInitialDate();
        setupEventHandlers();
    }

    /**
     * Sets up the client table columns with property value factories.
     */
    private void setupClientTable() {
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        clientNameColumn.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        deliveryNoteCountColumn.setCellValueFactory(new PropertyValueFactory<>("deliveryNoteCount"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));

        deliveryNotesTable.setItems(clientInvoiceItems);
    }

    /**
     * Sets up the delivery note details table.
     */
    private void setupDeliveryNoteTable() {
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("formattedDate"));
        deliveryNoteNumberColumn.setCellValueFactory(new PropertyValueFactory<>("deliveryNoteNumber"));
        invoiceAmountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        invoiceDetailsTable.setItems(deliveryNoteItems);
    }

    /**
     * Sets up initial date value for the date picker.
     */
    private void setupInitialDate() {
        toDateField.setValue(LocalDate.now());
    }

    /**
     * Sets up event handlers for the tables.
     */
    private void setupEventHandlers() {
        // Handle client table selection
        deliveryNotesTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        loadDeliveryNotesForClient(newValue.getClientId());
                    } else {
                        deliveryNoteItems.clear();
                    }
                }
        );

        toDateField.setOnAction(event -> handleSearch());
    }

    /**
     * Loads delivery notes that can be invoiced up to the specified date.
     */
    private void handleSearch() {
        LocalDate toDate = toDateField.getValue();

        if (toDate == null) {
            ShowAlert.showError("Error", "Por favor, seleccione una fecha válida.");
            return;
        }

        loadClients(toDate);
    }

    /**
     * Loads clients with pending delivery notes from the model.
     *
     * @param toDate End date for the search
     */
    private void loadClients(LocalDate toDate) {
        clientInvoiceItems.clear();
        deliveryNoteItems.clear();

        List<ClientInvoiceItem> clients = model.getClientsWithDeliveryNotes(toDate);
        clientInvoiceItems.addAll(clients);

        if (clientInvoiceItems.isEmpty()) {
            ShowAlert.showInfo("Información", "No se encontraron albaranes pendientes de facturar hasta la fecha seleccionada.");
        }
    }

    /**
     * Loads delivery notes for a specific client.
     *
     * @param clientId ID of the client
     */
    private void loadDeliveryNotesForClient(String clientId) {
        deliveryNoteItems.clear();

        List<DeliveryNoteInvoiceItem> notes = model.getDeliveryNotesForClient(
                Integer.parseInt(clientId), toDateField.getValue());
        deliveryNoteItems.addAll(notes);

    }

    /**
     * Handles the invoice button action.
     * Creates invoices for the selected client's delivery notes.
     *
     * @param actionEvent The event that triggered this method
     */
    @FXML
    public void handleInvoice(ActionEvent actionEvent) {
        ClientInvoiceItem selectedClient = deliveryNotesTable.getSelectionModel().getSelectedItem();

        if (selectedClient == null) {
            ShowAlert.showError("Error", "Por favor, seleccione un cliente para facturar.");
            return;
        }

        try {
            Integer clientId = Integer.parseInt(selectedClient.getClientId());
            Integer invoiceId = model.createInvoiceForClient(clientId, toDateField.getValue());

            ShowAlert.showInfo("Éxito", "Factura creada con éxito. Número de factura: " + invoiceId);
            handleSearch();

        } catch (Exception e) {
            e.printStackTrace();
            ShowAlert.showError("Error", "Error inesperado: " + e.getMessage());
        }
    }


    /**
     * Closes the invoice window.
     *
     * @param actionEvent The event that triggered this method
     */
    @FXML
    public void handleExit(ActionEvent actionEvent) {
        exitButton.getScene().getWindow().hide();
    }
}