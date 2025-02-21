package com.luis.facturacion.mvc_tipoIva;

import com.luis.facturacion.mvc_tipoIva.database.TipoDeIvaDAO;
import com.luis.facturacion.mvc_tipoIva.database.TipoDeIvaEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TipoDeIvaModel {
    private static TipoDeIvaModel instance;
    private TipoDeIvaController tipoDeIvaController;
    private final TipoDeIvaDAO tipoDeIvaDAO;
    private final ObservableList<TipoDeIvaEntity> tipoDeIvaList;

    TipoDeIvaModel() {
        System.out.println("Model created");
        this.tipoDeIvaDAO = new TipoDeIvaDAO();
        this.tipoDeIvaList = FXCollections.observableArrayList();
    }

    public static TipoDeIvaModel getInstance() {
        if (instance == null) {
            instance = new TipoDeIvaModel();
        }
        return instance;
    }

    public void setController(TipoDeIvaController tipoDeIvaController){
        if (this.tipoDeIvaController == null){
            this.tipoDeIvaController = tipoDeIvaController;
        }
    }
}
