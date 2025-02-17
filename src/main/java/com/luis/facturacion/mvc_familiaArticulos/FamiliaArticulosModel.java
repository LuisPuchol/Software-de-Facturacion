package com.luis.facturacion.mvc_familiaArticulos;

import com.luis.facturacion.mvc_familiaArticulos.database.FamiliaArticulosDAO;
import com.luis.facturacion.mvc_familiaArticulos.database.FamiliaArticulosEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class FamiliaArticulosModel {
    private static FamiliaArticulosModel instance;
    private final FamiliaArticulosDAO familiaDao;
    private final ObservableList<FamiliaArticulosEntity> familiaArticulosList;

    public FamiliaArticulosModel() {
        System.out.println("Familia Model created");
        this.familiaDao = new FamiliaArticulosDAO();
        this.familiaArticulosList = FXCollections.observableArrayList();
    }

    public static FamiliaArticulosModel getInstance() {
        if (instance == null) {
            instance = new FamiliaArticulosModel();
        }
        return instance;
    }

    public void addFamiliaArticulo(String codigo, String denominacion) {
        try {
            FamiliaArticulosEntity familiaArticulosEntity = new FamiliaArticulosEntity();
            familiaArticulosEntity.setCodigoFamiliaArticulos(codigo);
            familiaArticulosEntity.setDenominacionFamilias(denominacion);
            familiaDao.save(familiaArticulosEntity);

            System.out.println("Familia guardada correctamente");
        } catch (IllegalArgumentException e) {
            System.err.println("Error al agregar la familia: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado al agregar familia: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<FamiliaArticulosEntity> loadFamiliaArticulos() {
        List<FamiliaArticulosEntity> familiaDB = familiaDao.getAll();
        return familiaDB;
    }

}
