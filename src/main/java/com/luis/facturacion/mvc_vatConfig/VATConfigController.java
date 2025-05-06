package com.luis.facturacion.mvc_vatConfig;

import com.luis.facturacion.AppController;
import com.luis.facturacion.mvc_vatConfig.database.VATConfigEntity;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class VATConfigController {
    private VATConfigModel vatConfigModel;
    private AppController appController;

    @FXML
    private TableView<VATConfigEntity> VATConfigTable;
    @FXML
    private TableColumn<VATConfigEntity, Integer> columnId;
    @FXML
    private TableColumn<VATConfigEntity, Double> columnVAT;
    @FXML
    private TableColumn<VATConfigEntity, String> columnText;
    @FXML
    private TextField VATConfigIdField, VATConfigNumberField, VATConfigTextField;
    @FXML
    private Button newButton, editButton, deleteButton, exitButton;

    public VATConfigController() {
        System.out.println("VATConfig Controller created");
        this.vatConfigModel = vatConfigModel.getInstance();
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
        vatConfigModel.setController(this);
    }


    public void handleNuevoButton(MouseEvent mouseEvent) {
    }

    public void handleEditarButton(MouseEvent mouseEvent) {
    }

    public void handleEliminarButton(MouseEvent mouseEvent) {
    }

    public void handleSalirButton(MouseEvent mouseEvent) {
    }
}
