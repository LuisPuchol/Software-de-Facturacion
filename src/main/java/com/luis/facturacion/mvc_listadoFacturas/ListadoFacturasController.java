package com.luis.facturacion.mvc_listadoFacturas;

import com.luis.facturacion.AppController;
import com.luis.facturacion.mvc_factura.FacturaModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

public class ListadoFacturasController {
    private ListadoFacturasModel listadoFacturasModel;
    private AppController appController;

    @FXML
    private TableView<?> facturaTable;

    @FXML
    private TableColumn<?, Integer> columnId;

    @FXML
    private TableColumn<?, Integer> columnNumero;

    @FXML
    private TableColumn<?, String> columnCliente;

    @FXML
    private TableColumn<?, Double> columnTotal;

    @FXML
    private TableColumn<?, Boolean> columnCobrado;

    // Botones de acción
    @FXML
    private Button generarAbonoButton, salirButton;


    public ListadoFacturasController() {
        System.out.println("Factura created");
        this.listadoFacturasModel = ListadoFacturasModel.getInstance();
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
        listadoFacturasModel.setController(this);
    }

    public void handleSalirButton(MouseEvent mouseEvent) {
        salirButton.getScene().getWindow().hide();
    }

    public void handleGenerarAbono(MouseEvent mouseEvent) {
    }
}
