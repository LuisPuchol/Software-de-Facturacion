package com.luis.facturacion.mvc_invoiceList;


import com.luis.facturacion.AppController;
import com.luis.facturacion.mvc_deliveryNoteList.DeliveryNoteListItem;
import com.luis.facturacion.utils.ShowAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;

import java.time.LocalDate;
import java.util.List;
public class InvoiceListController {

    private AppController appController;
    private InvoiceListModel model;
    private ObservableList<InvoiceListItem> invoiceItems = FXCollections.observableArrayList();
    private ObservableList<DeliveryNoteListItem> deliveryNoteItems = FXCollections.observableArrayList();

    @FXML
    private DatePicker fromDateField, toDateField;

    @FXML
    private Button searchButton, exitButton;

    @FXML
    private TableView<InvoiceListItem> invoicesTable;

    @FXML
    private TableColumn<InvoiceListItem, String> dateColumn, invoiceNumberColumn,
            clientCodeColumn, clientNameColumn, baseAmountColumn,
            vatAmountColumn, totalAmountColumn;

    @FXML
    private TableView<DeliveryNoteListItem> deliveryNotesTable;

    @FXML
    private TableColumn<DeliveryNoteListItem, String> noteDataColumn,
            noteNumberColumn, noteBaseAmountColumn;

    /**
     * Constructor for the controller.
     */
    public InvoiceListController() {
        this.model = InvoiceListModel.getInstance();
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
        setupInvoiceTable();
        setupDeliveryNoteTable();
        setupInitialDates();
        setupEventHandlers();
    }

    /**
     * Sets up the invoice table columns with property value factories.
     */
    private void setupInvoiceTable() {
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("formattedDate"));
        invoiceNumberColumn.setCellValueFactory(new PropertyValueFactory<>("invoiceNumber"));
        clientCodeColumn.setCellValueFactory(new PropertyValueFactory<>("clientCode"));
        clientNameColumn.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        baseAmountColumn.setCellValueFactory(new PropertyValueFactory<>("baseAmount"));
        vatAmountColumn.setCellValueFactory(new PropertyValueFactory<>("vatAmount"));
        totalAmountColumn.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));

        invoicesTable.setItems(invoiceItems);
    }

    /**
     * Sets up the delivery note details table.
     */
    private void setupDeliveryNoteTable() {
        noteDataColumn.setCellValueFactory(new PropertyValueFactory<>("formattedDate"));
        noteNumberColumn.setCellValueFactory(new PropertyValueFactory<>("displayIndex"));
        noteBaseAmountColumn.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));

        deliveryNotesTable.setItems(deliveryNoteItems);
    }

    /**
     * Sets up initial date values for the date pickers.
     */
    private void setupInitialDates() {
        LocalDate now = LocalDate.now();
        LocalDate firstDayOfMonth = LocalDate.of(now.getYear(), now.getMonth(), 1);

        fromDateField.setValue(firstDayOfMonth);
        toDateField.setValue(now);
    }

    /**
     * Sets up event handlers for the tables.
     */
    private void setupEventHandlers() {
        // One row selected
        invoicesTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        loadDeliveryNotesForInvoice(newValue.getId());
                    } else {
                        deliveryNoteItems.clear();
                    }
                }
        );

        // double-click on invoice
        invoicesTable.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                InvoiceListItem selectedInvoice = invoicesTable.getSelectionModel().getSelectedItem();
                if (selectedInvoice != null) {
                    model.generateAndShowInvoicePDF(selectedInvoice.getId());
                }
            }
        });
    }

    /**
     * Handles the search button action to find invoices within date range.
     *
     * @param actionEvent The event that triggered this method
     */
    @FXML
    public void handleSearch(ActionEvent actionEvent) {
        LocalDate fromDate = fromDateField.getValue();
        LocalDate toDate = toDateField.getValue();

        loadInvoices(fromDate, toDate);
    }

    /**
     * Loads invoices within the specified date range.
     *
     * @param fromDate Start date for the search
     * @param toDate End date for the search
     */
    private void loadInvoices(LocalDate fromDate, LocalDate toDate) {
        invoiceItems.clear();
        deliveryNoteItems.clear();

        List<InvoiceListItem> invoices = model.getInvoicesByDateRange(fromDate, toDate);
        invoiceItems.addAll(invoices);

        if (invoiceItems.isEmpty()) {
            ShowAlert.showInfo("Información", "No se encontraron facturas para el período seleccionado.");
        }
    }

    /**
     * Loads delivery notes for a specific invoice.
     *
     * @param invoiceId ID of the invoice
     */
    private void loadDeliveryNotesForInvoice(Integer invoiceId) {
        deliveryNoteItems.clear();

        List<DeliveryNoteListItem> notes = model.getDeliveryNotesForInvoice(invoiceId);
        deliveryNoteItems.addAll(notes);
    }

    /**
     * Closes the invoice list window.
     *
     * @param actionEvent The event that triggered this method
     */
    @FXML
    public void handleExit(ActionEvent actionEvent) {
        exitButton.getScene().getWindow().hide();
    }
}