package com.luis.facturacion.mvc_factura;

import com.luis.facturacion.AppController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.util.Date;

public class FacturaController {
    private FacturaModel facturaModel;
    private AppController appController;

    @FXML
    private DatePicker fechaFacturaField, fechaCobroFacturaField;
    @FXML
    private TextField baseImponibleFacturaField, ivaFacturaField,
            totalFacturaField, hashFacturaField, idFacturaField, numeroFacturaField, clienteFacturaField, formaCobroFacturaField;
    @FXML
    private TextArea qrFacturaField, observacionesFacturaField;
    @FXML
    private CheckBox cobradaFacturaField;
    @FXML
    private Button nuevoButton, editarButton, eliminarButton, salirButton;

    public FacturaController() {
        System.out.println("Factura created");
        this.facturaModel = FacturaModel.getInstance();
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
        facturaModel.setController(this);
    }

    public void handleNuevoButton(MouseEvent mouseEvent) {
    }

    public void handleSalirButton(MouseEvent mouseEvent) {
        salirButton.getScene().getWindow().hide();
    }
}
