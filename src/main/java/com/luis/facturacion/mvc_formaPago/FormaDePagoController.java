package com.luis.facturacion.mvc_formaPago;

import com.luis.facturacion.AppController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.util.Date;

public class FormaDePagoController {
    private FormaPagoModel formaPagoModel;
    private AppController appController;

    @FXML
    private TableView<FormaPagoEntity> formaPagoTable;
    @FXML
    private TableColumn<FormaPagoEntity, Integer> columnId;
    @FXML
    private TableColumn<FormaPagoEntity, String> columnTipoFormaPago;
    @FXML
    private TableColumn<FormaPagoEntity, Date> columnFechaCobro;
    @FXML
    private TableColumn<FormaPagoEntity, String> columnObservaciones;
    @FXML
    private TextField idFormaPagoField, tipoFormaPagoField, observacionesFormaPagoField;
    @FXML
    private DatePicker fechaCobroFormaPagoField;
    @FXML
    private Button nuevoButton, editarButton, eliminarButton, salirButton;

    public FormaDePagoController() {
        System.out.println("Forma Pago created");
        this.formaPagoModel = FormaPagoModel.getInstance();
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
        formaPagoModel.setController(this);
    }
}
