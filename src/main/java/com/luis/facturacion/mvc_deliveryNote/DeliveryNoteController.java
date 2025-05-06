package com.luis.facturacion.mvc_deliveryNote;

import com.luis.facturacion.AppController;
import com.luis.facturacion.utils.TabFunction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;

public class DeliveryNoteController {

    private DeliveryNoteModel deliveryNoteModel;
    private AppController appController;

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

    @FXML
    public void initialize() {
        if (itemsTable != null) {
            codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
            conceptColumn.setCellValueFactory(new PropertyValueFactory<>("concept"));
            trace1Column.setCellValueFactory(new PropertyValueFactory<>("trace1"));
            trace2Column.setCellValueFactory(new PropertyValueFactory<>("trace2"));
            quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
            amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        }

        configureNavigation();
    }

    private void configureNavigation() {
        TabFunction tabFunction = new TabFunction();

        List<Node> navigationOrder = new ArrayList<>();
        navigationOrder.add(dateField);
        navigationOrder.add(clientField);
        navigationOrder.add(codeField);
        navigationOrder.add(trace1Field);
        navigationOrder.add(trace2Field);
        navigationOrder.add(quantityField);
        navigationOrder.add(priceField);
        navigationOrder.add(addButton);

        tabFunction.configureCircularNavigation(navigationOrder, addButton, codeField);
    }

    public void handleAddItem(ActionEvent actionEvent) {
        System.out.println("Add to table");
    }

    public void handleSave(ActionEvent actionEvent) {
    }

    public void handleExit(ActionEvent actionEvent) {
    }
}
