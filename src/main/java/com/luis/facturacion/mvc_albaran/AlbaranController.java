package com.luis.facturacion.mvc_albaran;

import com.luis.facturacion.AppController;
import com.luis.facturacion.utils.TabFunction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
import java.util.List;

public class AlbaranController {

    private AlbaranModel albaranModel;
    private AppController appController;

    @FXML
    private AnchorPane rootPane;

    // Campos de la primera fila
    @FXML private DatePicker dateField;
    @FXML private TextField clientField;
    @FXML private TextField clientInfoField;
    @FXML private TextField numField;
    @FXML private TextField totalField;

    // Campos de la segunda fila
    @FXML private TextField codeField;
    @FXML private TextField conceptField;
    @FXML private TextField trace1Field;
    @FXML private TextField trace2Field;
    @FXML private TextField quantityField;
    @FXML private TextField priceField;
    @FXML private Button addButton;

    // Tabla y columnas
    @FXML private TableView<AlbaranItem> itemsTable;
    @FXML private TableColumn<AlbaranItem, String> codeColumn;
    @FXML private TableColumn<AlbaranItem, String> conceptColumn;
    @FXML private TableColumn<AlbaranItem, String> trace1Column;
    @FXML private TableColumn<AlbaranItem, String> trace2Column;
    @FXML private TableColumn<AlbaranItem, Integer> quantityColumn;
    @FXML private TableColumn<AlbaranItem, Double> priceColumn;
    @FXML private TableColumn<AlbaranItem, Double> amountColumn;

    // Campos finales
    @FXML private TextField summaryField;
    @FXML private CheckBox printAlbaranCheck;
    @FXML private CheckBox createInvoiceCheck;
    @FXML private Button saveButton;
    @FXML private Button exitButton;

    public AlbaranController() {
        System.out.println("Alb Contr created");
        this.albaranModel = AlbaranModel.getInstance();
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
        albaranModel.setController(this);
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
        System.out.println("añadir a la tabla");
    }

    public void handleSave(ActionEvent actionEvent) {
    }

    public void handleExit(ActionEvent actionEvent) {
    }
}
