package com.luis.facturacion.mvc_rectificativa;

import com.luis.facturacion.AppController;
import com.luis.facturacion.mvc_client.database.ClienteEntity;
import com.luis.facturacion.mvc_rectificativa.database.RectificativaEntity;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Date;

public class RectificativaController {
    private RectificativaModel rectificativaModel;
    private AppController appController;

    @FXML
    private TableView<RectificativaEntity> rectificativaTable;
    @FXML
    private TableColumn<RectificativaEntity, Integer> columnId;
    @FXML
    private TableColumn<RectificativaEntity, Integer> columnNumero;
    @FXML
    private TableColumn<RectificativaEntity, Date> columnFecha;
    @FXML
    private TableColumn<RectificativaEntity, Integer> columnIdCliente;

    @FXML
    private TextField idRectificativaField, numeroRectificativaField, clienteRectificativaField, baseImponibleRectificativaField, ivaRectificativaField,
            totalRectificativaField, hashRectificativaField;
    @FXML
    private DatePicker fechaRectificativaField;
    @FXML
    private TextArea qrRectificativaField, observacionesRectificativaField;
    @FXML
    private Button nuevoButton, editarButton, eliminarButton, salirButton;

    public RectificativaController() {
        System.out.println("Rectificativa created");
        this.rectificativaModel = RectificativaModel.getInstance();
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
        rectificativaModel.setController(this);
    }
}