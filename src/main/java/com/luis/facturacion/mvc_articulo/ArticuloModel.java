package com.luis.facturacion.mvc_articulo;

import com.luis.facturacion.mvc_articulo.database.ArticuloDAO;
import com.luis.facturacion.mvc_articulo.database.ArticuloEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

public class ArticuloModel {
    private static ArticuloModel instance;
    private final ArticuloDAO articuloDAO;
    private final ObservableList<ArticuloEntity> listaArticulos;

    private ArticuloModel() {
        System.out.println("Model created");
        this.articuloDAO = new ArticuloDAO();
        this.listaArticulos = FXCollections.observableArrayList();
    }

    public static ArticuloModel getInstance() {
        if (instance == null) {
            instance = new ArticuloModel();
        }
        return instance;
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
            articuloEntity.setCodigoArticulo(codigo);
            articuloEntity.setCodigoBarrasArticulo(codigoBarras);
            articuloEntity.setDescripcionArticulo(descripcion);
            articuloEntity.setFamiliaArticulo(familiaArticulo);
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



    /**
     * Obtiene todos los artículos de la base de datos y los convierte en ArticuloFX para la interfaz.
     */
    public List<ArticuloEntity> cargarArticulos() {
        List<ArticuloEntity> articulosBD = articuloDAO.getAll();

        return articulosBD;
        //List<ArticuloFX> articulosFX = articulosBD.stream()
        //        .map(ArticuloAdapter::fromHibernate)
        //        .toList();
        //listaArticulos.setAll(articulosFX);
    }

    /**
     * Retorna la lista observable de artículos para la interfaz gráfica.
     */
    public ObservableList<ArticuloEntity> getListaArticulos() {
        return listaArticulos;
    }

    /**
     * Agrega un nuevo artículo, lo guarda en la base de datos y actualiza la lista.
     */
    public void agregarArticulo(ArticuloEntity articuloEntity) {
        //ArticuloEntity articuloEntity = ArticuloAdapter.toHibernate(articuloFX);
        //articuloDAO.save(articuloEntity);
        //cargarArticulos();
    }

    /**
     * Elimina un artículo de la base de datos y actualiza la lista.
     */
    public void eliminarArticuloSeleccionado(ArticuloEntity articuloEntity) {
        //if (seleccionado instanceof ArticuloFX articuloFX) {
        //    ArticuloEntity articuloEntity = ArticuloAdapter.toHibernate(articuloFX);
        //    articuloDAO.delete(articuloEntity);
        //    cargarArticulos();
        //}
    }
}
