package com.luis.facturacion.mvc_formaPago;

import com.luis.facturacion.AppController;
import com.luis.facturacion.mvc_formaPago.database.FormaDePagoEntity;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.util.Date;

public class FormaDePagoController {
    private FormaDePagoModel formaPagoModel;
    private AppController appController;

    @FXML
    private TableView<FormaDePagoEntity> formaPagoTable;
    @FXML
    private TableColumn<FormaDePagoEntity, Integer> columnId;
    @FXML
    private TableColumn<FormaDePagoEntity, String> columnTipoFormaPago;
    @FXML
    private TableColumn<FormaDePagoEntity, Date> columnFechaCobro;
    @FXML
    private TableColumn<FormaDePagoEntity, String> columnObservaciones;
    @FXML
    private TextField idFormaPagoField, tipoFormaPagoField, observacionesFormaPagoField;
    @FXML
    private DatePicker fechaCobroFormaPagoField;
    @FXML
    private Button nuevoButton, editarButton, eliminarButton, salirButton;

    public FormaDePagoController() {
        System.out.println("Forma Pago created");
        this.formaPagoModel = FormaDePagoModel.getInstance();
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
        formaPagoModel.setController(this);
    }

    public void handleNuevoButton(MouseEvent mouseEvent) {
    }

    public void handleEditarButton(MouseEvent mouseEvent) {
    }

    public void handleEliminarButton(MouseEvent mouseEvent) {
    }

    public void handleSalirButton(MouseEvent mouseEvent) {
    }
}
