package com.luis.facturacion.mvc_factura;

import com.luis.facturacion.AppController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.time.LocalDate;
import java.util.Optional;

public class FacturaController {
    private FacturaModel facturaModel;
    private AppController appController;
    private FacturasView facturasView;

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

    public void setView(FacturasView facturasView){
        this.facturasView = facturasView;
    }

    public void handleAgregarButton(MouseEvent mouseEvent) {
        int numeroFactura = Integer.parseInt(numeroFacturaField.getText()); // Número de factura como entero
        LocalDate fechaFactura = fechaFacturaField.getValue(); // Fecha de factura como LocalDate
        int clienteFactura = Integer.parseInt(clienteFacturaField.getText()); // Cliente como String
        double ivaFactura = Double.parseDouble(ivaFacturaField.getText()); // IVA como Double
        String observaciones = observacionesFacturaField.getText(); // Observaciones como String

        // Llamar al método con los valores convertidos
        facturaModel.startFactura(numeroFactura, fechaFactura, clienteFactura, ivaFactura, observaciones);

        CreateFacturaView createFacturaView = new CreateFacturaView(appController);
        createFacturaView.show(facturasView.getPrimaryStage());
    }

    public void handleSalirButton(MouseEvent mouseEvent) {
        salirButton.getScene().getWindow().hide();
    }

    //public String getClienteByID(Integer id) {
    //    return appController.getClienteByID(id);
    //}

    public String getProductByID(Integer id) {
        return appController.getProductByID(id);
    }
}
