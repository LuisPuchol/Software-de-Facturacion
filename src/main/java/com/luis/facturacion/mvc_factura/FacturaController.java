package com.luis.facturacion.mvc_factura;

import com.luis.facturacion.AppController;
import com.luis.facturacion.mvc_client.ClienteController;
import com.luis.facturacion.mvc_client.database.ClienteEntity;
import com.luis.facturacion.mvc_factura.database.FacturaEntity;
import com.luis.facturacion.mvc_formaPago.database.FormaDePagoEntity;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Date;

public class FacturaController {
    private FacturaModel facturaModel;
    private AppController appController;

    @FXML
    private TableView<FacturaEntity> facturaTable;
    @FXML
    private TableColumn<FacturaEntity, Integer> columnId;
    @FXML
    private TableColumn<FacturaEntity, Integer> columnNumero;
    @FXML
    private TableColumn<FacturaEntity, Date> columnFecha;
    @FXML
    private TableColumn<FacturaEntity, Integer> columnIdCliente;

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

    public void setUpModel(FacturaController facturaController){
        facturaModel.setController(facturaController);
    }
}
