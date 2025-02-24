package com.luis.facturacion.mvc_rectificativa;

import com.luis.facturacion.mvc_rectificativa.database.RectificativaDAO;
import com.luis.facturacion.mvc_rectificativa.database.RectificativaEntity;
import com.luis.facturacion.mvc_tipoIva.TipoDeIvaController;
import com.luis.facturacion.mvc_tipoIva.TipoDeIvaModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RectificativaModel {
    private static RectificativaModel instance;
    private RectificativaController rectificativaController;
    private final RectificativaDAO rectificativaDAO;
    private final ObservableList<RectificativaEntity> rectificativaList;

    public RectificativaModel() {
        System.out.println("Rectificativa Model Created");
        this.rectificativaDAO = new RectificativaDAO();
        this.rectificativaList = FXCollections.observableArrayList();
    }

    public static RectificativaModel getInstance() {
        if (instance == null) {
            instance = new RectificativaModel();
        }
        return instance;
    }

    public void setController(RectificativaController rectificativaController){
        if (this.rectificativaController == null){
            this.rectificativaController = rectificativaController;
        }
    }
}
