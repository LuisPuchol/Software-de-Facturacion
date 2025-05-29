package com.luis.facturacion.mvc_deliveryNoteList;

import com.luis.facturacion.AppController;
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
import java.util.Map;

/**
 * Controller for the Delivery Note List view.
 * Handles displaying a list of delivery notes based on date filters.
 */
public class DeliveryNoteListController {

    private AppController appController;
    private DeliveryNoteListModel model;
    private ObservableList<DeliveryNoteListItem> deliveryNoteItems = FXCollections.observableArrayList();

    @FXML
    private DatePicker fromDateField, toDateField;

    @FXML
    private CheckBox includeInvoicesCheck;

    @FXML
    private Button searchButton, exitButton;

    @FXML
    private TableView<DeliveryNoteListItem> deliveryNotesTable;

    @FXML
    private TableColumn<DeliveryNoteListItem, String> dateColumn, deliveryNoteNumberColumn,
            codeColumn, clientNameColumn, amountColumn, invoiceNumberColumn;

    /**
     * Constructor for the controller.
     */
    public DeliveryNoteListController() {
        this.model = DeliveryNoteListModel.getInstance();
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
        setupTableColumns();
        setupInitialDates();
        setupEventHandlers();
    }

    /**
     * Sets up the table columns with property value factories.
     */
    private void setupTableColumns() {
        Map<TableColumn<DeliveryNoteListItem, String>, String> columnMappings = Map.of(
                dateColumn, "formattedDate",
                deliveryNoteNumberColumn, "displayIndex",
                codeColumn, "displayClientId",
                clientNameColumn, "clientName",
                amountColumn, "totalAmount",
                invoiceNumberColumn, "displayInvoiceNumber"
        );

        columnMappings.forEach((column, property) ->
                column.setCellValueFactory(new PropertyValueFactory<>(property))
        );

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
        deliveryNotesTable.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                DeliveryNoteListItem deliveryNoteListItem = deliveryNotesTable.getSelectionModel().getSelectedItem();
                if (deliveryNoteListItem != null) {
                    model.generateAndShowDeliveryNotePDF(deliveryNoteListItem.getId());
                }
            }
        });
    }

    /**
     * Handles the search button action.
     * Retrieves delivery notes based on the date range and filter options.
     *
     * @param actionEvent The event that triggered this method
     */
    @FXML
    public void handleSearch(ActionEvent actionEvent) {
        LocalDate fromDate = fromDateField.getValue();
        LocalDate toDate = toDateField.getValue();
        boolean includeInvoices = includeInvoicesCheck.isSelected();

        if (fromDate == null || toDate == null) {
            ShowAlert.showError("Error", "Por favor, seleccione un rango de fechas válido.");
            return;
        }

        if (fromDate.isAfter(toDate)) {
            ShowAlert.showError("Error", "La fecha 'Desde' no puede ser posterior a la fecha 'Hasta'.");
            return;
        }

        loadDeliveryNotes(fromDate, toDate, includeInvoices);
    }

    /**
     * Loads delivery notes from the model based on specified criteria.
     *
     * @param fromDate        Start date for the search
     * @param toDate          End date for the search
     * @param includeInvoices Whether to include only notes with associated invoices
     */
    private void loadDeliveryNotes(LocalDate fromDate, LocalDate toDate, boolean includeInvoices) {
        deliveryNoteItems.clear();

        List<DeliveryNoteListItem> notes = model.getDeliveryNotesByDateRange(fromDate, toDate, includeInvoices);
        deliveryNoteItems.addAll(notes);

        if (deliveryNoteItems.isEmpty()) {
            ShowAlert.showInfo("Información", "No se encontraron albaranes para el período seleccionado.");
        }
    }


    /**
     * Closes the list window.
     *
     * @param actionEvent The event that triggered this method
     */
    @FXML
    public void handleExit(ActionEvent actionEvent) {
        exitButton.getScene().getWindow().hide();
    }
}