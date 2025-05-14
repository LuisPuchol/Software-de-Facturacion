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
import javafx.scene.layout.AnchorPane;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class DeliveryNoteController {

    private DeliveryNoteModel deliveryNoteModel;
    private AppController appController;
    private DeliveryNoteItem deliveryNoteItem;
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

    public DeliveryNoteController() {
        System.out.println("Delivery-Note Controller created");
        this.deliveryNoteModel = DeliveryNoteModel.getInstance();
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
        deliveryNoteModel.setController(this);
    }

    /**
     * Initializes UI components by setting up table columns with their respective
     * property values and configuring tab navigation between input fields.
     */
    @FXML
    public void initialize() {
        if (itemsTable != null) {
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

            itemsTable.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> {
                        if (newValue != null) {
                            populateFormFields(newValue);
                        }
                    });
        }
        configureNavigation();
    }

    /**
     * Populates the form fields with data from the selected item
     */
    private void populateFormFields(DeliveryNoteItem item) {
        codeField.setText(item.getCode());
        conceptField.setText(item.getConcept());
        trace1Field.setText(item.getTrace1());
        trace2Field.setText(item.getTrace2());
        quantityField.setText(String.valueOf(item.getQuantity()));
        priceField.setText(item.getPrice().toString());

        addButton.setText("Update");

        deliveryNoteItem = item;
    }

    /**
     * Configures tab navigation order between UI components and sets custom actions
     * for client and code fields that automatically fetch and display related information.
     */
    private void configureNavigation() {
        TabFunction tabFunction = new TabFunction();

        List<Node> navigationOrder = List.of(
                dateField, clientIdField, codeField, trace1Field,
                trace2Field, quantityField, priceField, addButton
        );

        Map<Node, Runnable> customActions = Map.of(
                clientIdField, () -> clientNameField.setText(getClientByInd(parseInt(clientIdField.getText()))),
                codeField, () -> conceptField.setText(getArticleByID(parseInt(codeField.getText())))
        );

        tabFunction.configureCircularNavigation(navigationOrder, codeField, addButton, customActions);

        deliveryNoteModel.setDeliveryNoteNumber(deliveryNoteNumberField);
    }

    /**
     * Handles adding a new item or updating an existing one based on selection state
     */
    public void handleAddItem(ActionEvent actionEvent) {
        if (deliveryNoteItem != null) {
            int index = deliveryNoteItems.indexOf(deliveryNoteItem);

            DeliveryNoteItem updatedItem = new DeliveryNoteItem(
                    codeField.getText(),
                    conceptField.getText(),
                    trace1Field.getText(),
                    trace2Field.getText(),
                    Double.parseDouble(quantityField.getText()),
                    new BigDecimal(priceField.getText())
            );
            deliveryNoteItems.set(index, updatedItem);

            deliveryNoteItem = null;
            addButton.setText("Add");
        } else {
            deliveryNoteItems.add(new DeliveryNoteItem(
                    codeField.getText(),
                    conceptField.getText(),
                    trace1Field.getText(),
                    trace2Field.getText(),
                    Double.parseDouble(quantityField.getText()),
                    new BigDecimal(priceField.getText())
            ));
        }

        calculateTotalAmountFromDeliveryNote();
        clearFields();
    }

    /**
     * Calculates the sum of all delivery note items and updates the total amount field.
     */
    private void calculateTotalAmountFromDeliveryNote() {
        BigDecimal totalAmount = deliveryNoteItems.stream()
                .map(DeliveryNoteItem::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        totalAmountField.setText(String.valueOf(totalAmount));
    }

    /**
     * Clears all item input fields and resets the editing state.
     */
    private void clearFields() {
        List.of(codeField, conceptField, trace1Field, trace2Field, quantityField, priceField)
                .forEach(TextField::clear);

        deliveryNoteItem = null;
        addButton.setText("Add");
    }

    /**
     * Creates and saves a new delivery note with all items from the table.
     * @param actionEvent The save button click event
     */
    public void handleSave(ActionEvent actionEvent) {
        deliveryNoteModel.createNewDeliveryNote(
                deliveryNoteNumberField.getText(),
                clientIdField.getText(),
                dateField.getValue(),
                printDeliveryNoteCheck.isSelected(),
                createInvoiceCheck.isSelected()
        );

        deliveryNoteItems.forEach(deliveryNoteModel::convertAndAddItemToDeliveryNote);

        deliveryNoteModel.saveDeliveryNoteWithItems();

        resetUI();
    }

    public void resetUI() {
        // TODO posible external class to clean TextFields
        List.of(clientIdField, clientNameField, deliveryNoteNumberField).forEach(TextField::clear);

        totalAmountField.setText("0.00");
        deliveryNoteItems.clear();
        createInvoiceCheck.setSelected(false);
        deliveryNoteModel.setDeliveryNoteNumber(deliveryNoteNumberField);
    }

    public void handleExit(ActionEvent actionEvent) {
        exitButton.getScene().getWindow().hide();
    }

    private String getClientByInd(int ID) {
        return ClientDAO.getInstance().getNameById(ID);
    }

    public String getArticleByID(int ID) {
        return ArticleDAO.getInstance().getNameById(ID);
    }
}
