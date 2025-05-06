package com.luis.facturacion.mvc_invoiceList;

import com.luis.facturacion.mvc_invoice.database.InvoiceDAO;
import com.luis.facturacion.mvc_invoice.database.InvoiceEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class InvoiceListModel {
    private static InvoiceListModel instance;
    private InvoiceListController invoiceListController;
    private final InvoiceDAO invoiceDAO;
    private final ObservableList<InvoiceEntity> facturasList;

    public InvoiceListModel() {
        System.out.println("Invoice-List Model Created");
        this.invoiceDAO = new InvoiceDAO();
        this.facturasList = FXCollections.observableArrayList();
    }

    public static InvoiceListModel getInstance() {
        if (instance == null) {
            instance = new InvoiceListModel();
        }
        return instance;
    }

    public void setController(InvoiceListController invoiceListController){
        if (this.invoiceListController == null){
            this.invoiceListController = invoiceListController;
        }
    }
}
