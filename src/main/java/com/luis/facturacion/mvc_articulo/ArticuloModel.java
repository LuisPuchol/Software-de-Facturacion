package com.luis.facturacion.mvc_articulo;

import com.luis.facturacion.mvc_articulo.database.ArticuloDAO;
import com.luis.facturacion.mvc_articulo.database.ArticuloEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

public class ArticuloModel {
    private static ArticuloModel instance;
    private ArticuloController articuloController;
    private final ArticuloDAO articuloDAO;
    private final ObservableList<ArticuloEntity> articulosList;

    ArticuloModel() {
        System.out.println("Model created");
        this.articuloDAO = new ArticuloDAO();
        this.articulosList = FXCollections.observableArrayList();
    }

    public static ArticuloModel getInstance() {
        if (instance == null) {
            instance = new ArticuloModel();
        }
        return instance;
    }

    public void setController(ArticuloController articuloController){
        if (this.articuloController == null){
            this.articuloController = articuloController;
        }
    }

    public void agregarArticuloDesdeFormulario(String ind, String name) {

        try {
            // Validaciones básicas: asegurarse de que los valores obligatorios no sean nulos o vacíos
            if (ind == null || ind.trim().isEmpty()) {
                throw new IllegalArgumentException("El indice del artículo es obligatorio.");
            }
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("El nombre del artículo es obligatorio.");
            }

            // Conversión de tipos (de String a tipos numéricos)
            int indice = convertirEntero(ind, "Indice");

            // Creación del objeto ArticuloEntity con los valores convertidos
            ArticuloEntity articuloEntity = new ArticuloEntity();

            articuloEntity.setIndice(indice);
            articuloEntity.setNombre(name);

            // Guardar en la base de datos
            articuloDAO.save(articuloEntity);

            System.out.println("Artículo guardado correctamente.");

        } catch (IllegalArgumentException e) {
            System.err.println("Error al agregar artículo: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado al agregar artículo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private int convertirEntero(String valor, String campo) {
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El campo '" + campo + "' debe ser un número entero válido.");
        }
    }

    private double convertirDouble(String valor, String campo) {
        try {
            return Double.parseDouble(valor);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El campo '" + campo + "' debe ser un número decimal válido.");
        }
    }

    public List<ArticuloEntity> cargarArticulos() {
        return articuloDAO.getAll();
    }

    public String getProductByID(Integer id) {
        return articuloDAO.getProductNameById(id);
    }
}
