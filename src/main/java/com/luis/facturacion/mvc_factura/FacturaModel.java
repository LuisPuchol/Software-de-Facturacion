package com.luis.facturacion.mvc_factura;

import com.luis.facturacion.mvc_factura.database.FacturaDAO;
import com.luis.facturacion.mvc_factura.database.FacturaEntity;
import com.luis.facturacion.mvc_familiaArticulos.FamiliaArticulosController;
import com.luis.facturacion.mvc_familiaArticulos.FamiliaArticulosModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FacturaModel {
    private static FacturaModel instance;
    private FacturaController facturaController;
    private final FacturaDAO facturaDAO;
    private final ObservableList<FacturaEntity> facturasList;

    public FacturaModel() {
        System.out.println("Factura Model Created");
        this.facturaDAO = new FacturaDAO();
        this.facturasList = FXCollections.observableArrayList();
    }

    public static FacturaModel getInstance() {
        if (instance == null) {
            instance = new FacturaModel();
        }
        return instance;
    }

    public void setController(FacturaController facturaController){
        if (this.facturaController == null){
            this.facturaController = facturaController;
        }
    }
}
