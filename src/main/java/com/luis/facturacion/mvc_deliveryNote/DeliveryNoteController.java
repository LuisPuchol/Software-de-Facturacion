package com.luis.facturacion.mvc_deliveryNote;

import com.luis.facturacion.AppController;
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
import java.util.ArrayList;
import java.util.HashMap;
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
    private TextField clientField, clientInfoField, numField, totalField;

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
    private CheckBox printAlbaranCheck, createInvoiceCheck;
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
        }

        configureNavigation();
    }

    /**
     * Configures tab navigation order between UI components and sets custom actions
     * for client and code fields that automatically fetch and display related information.
     */
    private void configureNavigation() {
        TabFunction tabFunction = new TabFunction();

        List<Node> navigationOrder = List.of(
                dateField, clientField, codeField, trace1Field,
                trace2Field, quantityField, priceField, addButton
        );

        Map<Node, Runnable> customActions = Map.of(
                clientField, () -> clientInfoField.setText(getClientByInd(parseInt(clientField.getText()))),
                codeField, () -> conceptField.setText(getArticleByID(parseInt(codeField.getText())))
        );

        tabFunction.configureCircularNavigation(navigationOrder, codeField, addButton, customActions);
    }

    /**
     * Get the data from the TextFields and add the item in the table
     *
     * @param actionEvent
     */
    public void handleAddItem(ActionEvent actionEvent) {
        deliveryNoteItems.add(new DeliveryNoteItem(
                codeField.getText(),
                conceptField.getText(),
                trace1Field.getText(),
                trace2Field.getText(),
                Double.parseDouble(quantityField.getText()),
                new BigDecimal(priceField.getText())
        ));

        clearFields();
    }

    private void clearFields() {
        List.of(codeField, trace1Field, trace2Field, quantityField, priceField)
                .forEach(TextField::clear);
    }

    public void handleSave(ActionEvent actionEvent) {
    }

    public void handleExit(ActionEvent actionEvent) {
    }


    private String getClientByInd(int ID) {
        return appController.getClienteByID(ID);
    }

    public String getArticleByID(int ID) {
        return appController.getProductByID(ID);
    }
}
