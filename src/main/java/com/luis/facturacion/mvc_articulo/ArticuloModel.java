package com.luis.facturacion.mvc_articulo;

import com.luis.facturacion.mvc_articulo.database.ArticuloDAO;
import com.luis.facturacion.mvc_articulo.database.ArticuloEntity;
import com.luis.facturacion.mvc_familiaArticulos.database.FamiliaArticulosEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

public class ArticuloModel {
    private static ArticuloModel instance;
    private ArticulosController articulosController;
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

    public void setController(ArticulosController articulosController){
        if (this.articulosController == null){
            this.articulosController = articulosController;
        }
    }

    public void agregarArticuloDesdeFormulario(
            String codigo, String codigoBarras, String descripcion, String familia,
            String coste, String margenComercial, String pvp, String proveedor, String stock, String observaciones) {

        try {
            // Validaciones básicas: asegurarse de que los valores obligatorios no sean nulos o vacíos
            if (codigo == null || codigo.trim().isEmpty()) {
                throw new IllegalArgumentException("El código del artículo es obligatorio.");
            }
            if (descripcion == null || descripcion.trim().isEmpty()) {
                throw new IllegalArgumentException("La descripción del artículo es obligatoria.");
            }

            // Conversión de tipos (de String a tipos numéricos)
            int familiaArticulo = convertirEntero(familia, "familia");
            double costeArticulo = convertirDouble(coste, "coste");
            double margenComercialArticulo = convertirDouble(margenComercial, "margen comercial");
            double pvpArticulo = convertirDouble(pvp, "pvp");
            int proveedorArticulo = convertirEntero(proveedor, "proveedor");
            double stockArticulo = convertirDouble(stock, "stock");

            // Creación del objeto ArticuloEntity con los valores convertidos
            ArticuloEntity articuloEntity = new ArticuloEntity();
            Object family = articulosController.getFamilyById(familiaArticulo);

            articuloEntity.setCodigoArticulo(codigo);
            articuloEntity.setCodigoBarrasArticulo(codigoBarras);
            articuloEntity.setDescripcionArticulo(descripcion);
            articuloEntity.setFamiliaArticulo((FamiliaArticulosEntity) family);
            articuloEntity.setCosteArticulo(costeArticulo);
            articuloEntity.setMargenComercialArticulo(margenComercialArticulo);
            articuloEntity.setPvpArticulo(pvpArticulo);
            articuloEntity.setProveedorArticulo(proveedorArticulo);
            articuloEntity.setStockArticulo(stockArticulo);
            articuloEntity.setObservacionesArticulo(observaciones);

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
        List<ArticuloEntity> articulosBD = articuloDAO.getAll();

        return articulosBD;
    }

}
