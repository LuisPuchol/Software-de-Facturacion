package com.luis.facturacion.mvc_correctiveInvoice;

import com.luis.facturacion.AppController;
import com.luis.facturacion.mvc_correctiveInvoice.database.CorrectiveInvoiceEntity;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Date;

public class CorrectiveInvoiceController {
    private CorrectiveInvoiceModel correctiveInvoiceModel;
    private AppController appController;

    @FXML
    private TableView<CorrectiveInvoiceEntity> CorrectiveInvoiceTable;
    @FXML
    private TableColumn<CorrectiveInvoiceEntity, Integer> columnId;
    @FXML
    private TableColumn<CorrectiveInvoiceEntity, Integer> columnNumber;
    @FXML
    private TableColumn<CorrectiveInvoiceEntity, Date> columnDate;
    @FXML
    private TableColumn<CorrectiveInvoiceEntity, Integer> columnClientID;

    @FXML
    private TextField correctiveInvoiceIdField, correctiveInvoiceNumberField, correctiveInvoiceClientField, correctiveInvoiceTaxableBaseField, correctiveInvoiceVATField,
            correctiveInvoiceTotalField, correctiveInvoiceHashField;
    @FXML
    private DatePicker correctiveInvoiceDateField;
    @FXML
    private TextArea correctiveInvoiceQRField, correctiveInvoiceObservationsField;
    @FXML
    private Button newButton, editButton, deleteButton, exitButton;

    public CorrectiveInvoiceController() {
        System.out.println("Corrective-Invoice Controller created");
        //this.rectificativaModel = RectificativaModel.getInstance();
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
        //rectificativaModel.setController(this);
    }


}