package com.luis.facturacion.mvc_deliveryNote;

import com.luis.facturacion.AppController;
import com.luis.facturacion.mvc_article.database.ArticleDAO;
import com.luis.facturacion.mvc_client.database.ClientDAO;
import com.luis.facturacion.utils.TabFunction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class DeliveryNoteController {

    private DeliveryNoteModel model;
    private AppController appController;
    private DeliveryNoteItem selectedItem;
    private ObservableList<DeliveryNoteItem> deliveryNoteItems = FXCollections.observableArrayList();

    @FXML
    private AnchorPane rootPane;

    @FXML
    private DatePicker dateField;
    @FXML
    private TextField clientIdField, clientNameField, deliveryNoteNumberField, totalAmountField;

    @FXML
    private TextField codeField, conceptField, trace1Field, trace2Field, quantityField, priceField;
    @FXML
    private Button addButton;

    @FXML
    private TableView<DeliveryNoteItem> itemsTable;
    @FXML
    private TableColumn<DeliveryNoteItem, String> codeColumn, conceptColumn, trace1Column,
            trace2Column, quantityColumn, priceColumn, amountColumn;

    @FXML
    private TextField summaryField;
    @FXML
    private CheckBox printDeliveryNoteCheck, createInvoiceCheck;
    @FXML
    private Button saveButton, exitButton;

    /**
     * Initializes the controller with the delivery note model.
     */
    public DeliveryNoteController() {
        System.out.println("Delivery-Note Controller created");
        this.model = DeliveryNoteModel.getInstance();
    }

    /**
     * Sets the application controller and registers this controller with the model.
     */
    public void setAppController(AppController appController) {
        this.appController = appController;
        model.setController(this);
    }

    /**
     * Initializes UI components and configures table and event listeners.
     */
    @FXML
    public void initialize() {
        if (itemsTable != null) {
            setupTableColumns();
            setupTableEventHandlers();
        }
        configureNavigation();
        model.setDeliveryNoteNumber(deliveryNoteNumberField);
    }

    /**
     * Sets up the table columns with property value factories.
     */
    private void setupTableColumns() {
        Map<TableColumn<DeliveryNoteItem, String>, String> columnMappings = Map.of(
                codeColumn, "code",
                conceptColumn, "concept",
                trace1Column, "trace1",
                trace2Column, "trace2",
                quantityColumn, "quantity",
                priceColumn, "price",
                amountColumn, "amount"
        );
        columnMappings.forEach((column, property) ->
                column.setCellValueFactory(new PropertyValueFactory<>(property))
        );
        itemsTable.setItems(deliveryNoteItems);
    }

    /**
     * Sets up event handlers for the item table.
     */
    private void setupTableEventHandlers() {
        itemsTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        populateFormFields(newValue);
                    } else {
                        clearItemFields();
                        selectedItem = null;
                    }
                });

        itemsTable.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.DELETE || event.getCode() == KeyCode.BACK_SPACE) {
                deleteSelectedRow();
            }
        });
    }



    /**
     * Configures tab navigation between form fields.
     */
    private void configureNavigation() {
        TabFunction tabFunction = new TabFunction();

        List<Node> navigationOrder = List.of(
                dateField, clientIdField, codeField, trace1Field,
                trace2Field, quantityField, priceField, addButton
        );

        Map<Node, Runnable> customActions = Map.of(
                clientIdField, () -> clientNameField.setText(getClientName(parseInt(clientIdField.getText()))),
                codeField, () -> conceptField.setText(getArticleName(parseInt(codeField.getText())))
        );

        tabFunction.configureCircularNavigation(navigationOrder, codeField, addButton, customActions);
    }

    /**
     * Adds or updates an item in the delivery note.
     */
    @FXML
    public void handleAddItem(ActionEvent actionEvent) {
        DeliveryNoteItem newItem = createItemFromFields();

        if (selectedItem != null) {
            int index = deliveryNoteItems.indexOf(selectedItem);
            deliveryNoteItems.set(index, newItem);
            selectedItem = null;
            addButton.setText("Alta");
        } else {
            deliveryNoteItems.add(newItem);
        }

        calculateTotalAmount();
        clearItemFields();
    }

    /**
     * Creates a new DeliveryNoteItem from the current field values.
     */
    private DeliveryNoteItem createItemFromFields() {
        return new DeliveryNoteItem(
                codeField.getText(),
                conceptField.getText(),
                trace1Field.getText(),
                trace2Field.getText(),
                Double.parseDouble(quantityField.getText()),
                new BigDecimal(priceField.getText())
        );
    }

    /**
     * Calculates the sum of all items and updates the total amount field.
     */
    private void calculateTotalAmount() {
        BigDecimal totalAmount = deliveryNoteItems.stream()
                .map(DeliveryNoteItem::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        totalAmountField.setText(String.valueOf(totalAmount));
    }

    /**
     * Clears all item input fields and resets the editing state.
     */
    private void clearItemFields() {
        List<TextField> itemFields = List.of(
                codeField, conceptField, trace1Field, trace2Field, quantityField, priceField
        );
        itemFields.forEach(TextField::clear);

        selectedItem = null;
        addButton.setText("Alta");
    }

    /**
     * Populates form fields with data from the selected item.
     */
    private void populateFormFields(DeliveryNoteItem item) {
        codeField.setText(item.getCode());
        conceptField.setText(item.getConcept());
        trace1Field.setText(item.getTrace1());
        trace2Field.setText(item.getTrace2());
        quantityField.setText(String.valueOf(item.getQuantity()));
        priceField.setText(item.getPrice().toString());

        addButton.setText("Modificar");
        selectedItem = item;
    }

    /**
     * Deletes the currently selected row from the table.
     */
    private void deleteSelectedRow() {
        DeliveryNoteItem itemToDelete = itemsTable.getSelectionModel().getSelectedItem();
        if (itemToDelete != null) {
            deliveryNoteItems.remove(itemToDelete);
            calculateTotalAmount();
            clearItemFields();
        }
    }

    /**
     * Creates and saves a delivery note with all items to the database.
     */
    @FXML
    public void handleSave(ActionEvent actionEvent) {

        deliveryNoteItems.forEach(model::convertAndAddItemToDeliveryNote);

        model.createNewDeliveryNote(
                deliveryNoteNumberField.getText(),
                clientIdField.getText(),
                dateField.getValue(),
                totalAmountField.getText(),
                printDeliveryNoteCheck.isSelected(),
                createInvoiceCheck.isSelected()
        );

        model.saveDeliveryNoteWithItems();
        resetUI();
    }

    /**
     * Resets the UI to its initial state.
     */
    public void resetUI() {
        selectedItem = null;
        addButton.setText("Alta");

        // TODO | posible external class to clean TextFields, the combination of this
        // TODO | two lines would be a method clearAllFields or something
        List.of(clientIdField, clientNameField, deliveryNoteNumberField).forEach(TextField::clear);
        clearItemFields();

        totalAmountField.setText("0.00");
        deliveryNoteItems.clear();
        createInvoiceCheck.setSelected(false);
        model.setDeliveryNoteNumber(deliveryNoteNumberField);

        // This resets the selection in the table
        if (itemsTable != null && itemsTable.getSelectionModel() != null) {
            itemsTable.getSelectionModel().clearSelection();
        }
    }

    /**
     * Closes the delivery note window.
     */
    @FXML
    public void handleExit(ActionEvent actionEvent) {
        exitButton.getScene().getWindow().hide();
    }

    /**
     * Gets a client name by ID from the database.
     */
    private String getClientName(int id) {
        return ClientDAO.getInstance().getNameById(id);
    }

    /**
     * Gets an article name by ID from the database.
     */
    private String getArticleName(int id) {
        return ArticleDAO.getInstance().getNameById(id);
    }
}
