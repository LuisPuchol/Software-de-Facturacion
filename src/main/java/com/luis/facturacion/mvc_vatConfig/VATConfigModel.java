package com.luis.facturacion.mvc_vatConfig;

import com.luis.facturacion.mvc_vatConfig.database.VATConfigDAO;
import com.luis.facturacion.mvc_vatConfig.database.VATConfigEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class VATConfigModel {
    private static VATConfigModel instance;
    private VATConfigController vatConfigController;
    private final VATConfigDAO vatConfigDAO;
    private final ObservableList<VATConfigEntity> vatConfigList;

    VATConfigModel() {
        System.out.println("VATConfig Model created");
        this.vatConfigDAO = new VATConfigDAO();
        this.vatConfigList = FXCollections.observableArrayList();
    }

    public static VATConfigModel getInstance() {
        if (instance == null) {
            instance = new VATConfigModel();
        }
        return instance;
    }

    public void setController(VATConfigController VATConfigController){
        if (this.vatConfigController == null){
            this.vatConfigController = VATConfigController;
        }
    }
}
