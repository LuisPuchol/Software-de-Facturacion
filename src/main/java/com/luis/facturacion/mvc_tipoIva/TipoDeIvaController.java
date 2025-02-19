package com.luis.facturacion.mvc_tipoIva;

import com.luis.facturacion.AppController;
import com.luis.facturacion.mvc_tipoIva.database.TipoDeIvaEntity;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class TipoDeIvaController {
    private TipoDeIvaModel tipoIvaModel;
    private AppController appController;

    @FXML
    private TableView<TipoDeIvaEntity> tipoIvaTable;
    @FXML
    private TableColumn<TipoDeIvaEntity, Integer> columnId;
    @FXML
    private TableColumn<TipoDeIvaEntity, Double> columnIva;
    @FXML
    private TableColumn<TipoDeIvaEntity, String> columnObservaciones;
    @FXML
    private TextField idTipoIvaField, ivaField, observacionesTipoIvaField;
    @FXML
    private Button nuevoButton, editarButton, eliminarButton, salirButton;

    public TipoDeIvaController() {
        System.out.println("Tipo IVA created");
        this.tipoIvaModel = TipoDeIvaModel.getInstance();
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
        tipoIvaModel.setController(this);
    }

    public void setUpModel(TipoDeIvaController tipoDeIvaController){
        tipoIvaModel.setController(tipoDeIvaController);
    }
}
