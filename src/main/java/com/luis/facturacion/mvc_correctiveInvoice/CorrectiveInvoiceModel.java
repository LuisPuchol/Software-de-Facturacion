package com.luis.facturacion.mvc_correctiveInvoice;

import com.luis.facturacion.mvc_correctiveInvoice.database.CorrectiveInvoiceDAO;
import com.luis.facturacion.mvc_correctiveInvoice.database.CorrectiveInvoiceEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CorrectiveInvoiceModel {
    private static CorrectiveInvoiceModel instance;
    private CorrectiveInvoiceController correctiveInvoiceController;
    private final CorrectiveInvoiceDAO correctiveInvoiceDAO;
    private final ObservableList<CorrectiveInvoiceEntity> correctiveInvoiceList;

    public CorrectiveInvoiceModel() {
        System.out.println("Corrective-Invoice Model Created");
        this.correctiveInvoiceDAO = new CorrectiveInvoiceDAO();
        this.correctiveInvoiceList = FXCollections.observableArrayList();
    }

    public static CorrectiveInvoiceModel getInstance() {
        if (instance == null) {
            instance = new CorrectiveInvoiceModel();
        }
        return instance;
    }

    public void setController(CorrectiveInvoiceController correctiveInvoiceController){
        if (this.correctiveInvoiceController == null){
            this.correctiveInvoiceController = correctiveInvoiceController;
        }
    }
}
