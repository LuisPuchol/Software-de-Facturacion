package com.luis.facturacion.mvc_formaPago;

import com.luis.facturacion.mvc_formaPago.database.FormaDePagoDAO;
import com.luis.facturacion.mvc_formaPago.database.FormaDePagoEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FormaDePagoModel {
    private static FormaDePagoModel instance;
    private FormaDePagoController formaDePagoController;
    private final FormaDePagoDAO formaDePagoDAO;
    private final ObservableList<FormaDePagoEntity> formaDePagoList;

    FormaDePagoModel() {
        System.out.println("Model created");
        this.formaDePagoDAO = new FormaDePagoDAO();
        this.formaDePagoList = FXCollections.observableArrayList();
    }

    public static FormaDePagoModel getInstance() {
        if (instance == null) {
            instance = new FormaDePagoModel();
        }
        return instance;
    }

    public void setController(FormaDePagoController formaDePagoController){
        if (this.formaDePagoController == null){
            this.formaDePagoController = formaDePagoController;
        }
    }
}
