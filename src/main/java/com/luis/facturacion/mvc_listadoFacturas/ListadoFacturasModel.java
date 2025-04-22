package com.luis.facturacion.mvc_listadoFacturas;

import com.luis.facturacion.mvc_factura.FacturaController;
import com.luis.facturacion.mvc_factura.FacturaModel;
import com.luis.facturacion.mvc_factura.database.FacturaDAO;
import com.luis.facturacion.mvc_factura.database.FacturaEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ListadoFacturasModel {
    private static ListadoFacturasModel instance;
    private ListadoFacturasController listadoFacturasController;
    private final FacturaDAO facturaDAO;
    private final ObservableList<FacturaEntity> facturasList;

    public ListadoFacturasModel() {
        System.out.println("Factura Model Created");
        this.facturaDAO = new FacturaDAO();
        this.facturasList = FXCollections.observableArrayList();
    }

    public static ListadoFacturasModel getInstance() {
        if (instance == null) {
            instance = new ListadoFacturasModel();
        }
        return instance;
    }

    public void setController(ListadoFacturasController listadoFacturasController){
        if (this.listadoFacturasController == null){
            this.listadoFacturasController = listadoFacturasController;
        }
    }
}
