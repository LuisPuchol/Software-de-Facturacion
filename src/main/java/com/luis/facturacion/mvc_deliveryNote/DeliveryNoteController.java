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

            itemsTable.setItems(deliveryNoteItems);
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
        Map<Node, Runnable> customActions = new HashMap<>();
        customActions.put(clientField, () -> {
            String clientID = clientField.getText();
            String clientInfo = getClientByInd(Integer.parseInt(clientID));
            clientInfoField.setText(clientInfo);
        } );
        customActions.put(codeField, this::getArticleByID);

        tabFunction.configureCircularNavigation(navigationOrder, codeField, addButton, customActions);
    }

    /**
     * pilla la info de los textfield
     * la pone en la tabla
     * guarda los items en una List, no en una BBDD
     * @param actionEvent
     */
    public void handleAddItem(ActionEvent actionEvent) {
        String code = codeField.getText();
        String concept = conceptField.getText();
        String trace1 = trace1Field.getText();
        String trace2 = trace2Field.getText();
        Integer quantity = Integer.parseInt(quantityField.getText());
        BigDecimal price = new BigDecimal(priceField.getText());

        deliveryNoteItem = new DeliveryNoteItem(code, concept, trace1, trace2, quantity, price);

        deliveryNoteItems.add(deliveryNoteItem);

        clearFields();
    }

    private void clearFields() {
        codeField.clear();
        trace1Field.clear();
        trace2Field.clear();
        quantityField.clear();
        priceField.clear();
    }

    public void handleSave(ActionEvent actionEvent) {
    }

    public void handleExit(ActionEvent actionEvent) {
    }


    private String getClientByInd(int ID){
        return appController.getClienteByID(ID);
    }

    public void getArticleByID(){
        System.out.println("hola");
    }
}
