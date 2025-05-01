package com.luis.facturacion.mvc_albaran;

import com.luis.facturacion.mvc_client.ClienteController;
import com.luis.facturacion.mvc_client.ClienteModel;
import com.luis.facturacion.mvc_client.database.ClienteDAO;
import com.luis.facturacion.mvc_client.database.ClienteEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AlbaranModel {
    private static AlbaranModel instance;
    private AlbaranController albaranController;
    //private final AlbaranDAO albaranDAO;
    //private final ObservableList<AlbaranEntity> albaranList;

    private AlbaranModel() {
        System.out.println("Albaran Model created");
        //this.clienteDao = new ClienteDAO();
        //this.clienteList = FXCollections.observableArrayList();
    }

    public static AlbaranModel getInstance() {
        if (instance == null) {
            instance = new AlbaranModel();
        }
        return instance;
    }

    public void setController(AlbaranController albaranController) {
        if (this.albaranController == null) {
            this.albaranController = albaranController;
        }
    }
}
