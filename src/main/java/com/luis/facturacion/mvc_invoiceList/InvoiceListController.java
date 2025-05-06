package com.luis.facturacion.mvc_invoiceList;

import com.luis.facturacion.AppController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

public class InvoiceListController {
    private InvoiceListModel invoiceListModel;
    private AppController appController;

    @FXML
    private TableView<?> invoiceTable;

    @FXML
    private TableColumn<?, Integer> columnId;

    @FXML
    private TableColumn<?, Integer> columnNumber;

    @FXML
    private TableColumn<?, String> columnClient;

    @FXML
    private TableColumn<?, Double> columnTotal;

    @FXML
    private TableColumn<?, Boolean> columnInvoiced;

    // Action buttons
    @FXML
    private Button createCorrectiveInvoice, exitButton;


    public InvoiceListController() {
        System.out.println("Invoice-List Controller created");
        this.invoiceListModel = InvoiceListModel.getInstance();
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
        invoiceListModel.setController(this);
    }
    @FXML
    public void handleExitButton(MouseEvent mouseEvent) {
        exitButton.getScene().getWindow().hide();
    }

    @FXML
    public void handleCreateCorrectiveInvoiceButton(MouseEvent mouseEvent) {
    }
}
