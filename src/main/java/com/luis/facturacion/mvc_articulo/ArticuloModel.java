package com.luis.facturacion.mvc_articulo;

import com.luis.facturacion.mvc_articulo.database.adapters.ArticuloAdapter;
import com.luis.facturacion.mvc_articulo.database.dao.ArticuloDAO;
import com.luis.facturacion.mvc_articulo.database.entities_hibernate.ArticuloHibernate;
import com.luis.facturacion.mvc_articulo.database.entitiesfx.ArticuloFX;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

public class ArticuloModel {
    private static ArticuloModel instance;
    private final ArticuloDAO articuloDAO;
    private final ObservableList<ArticuloFX> listaArticulos;

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

        ArticuloFX articuloFX = new ArticuloFX();
        articuloFX.codigoArticuloProperty().set(codigo);
        articuloFX.codigoBarrasArticuloProperty().set(codigoBarras);
        articuloFX.descripcionArticuloProperty().set(descripcion);
        articuloFX.familiaArticuloProperty().set(Integer.parseInt(familia));
        articuloFX.costeArticuloProperty().set(Double.parseDouble(coste));
        articuloFX.margenComercialArticuloProperty().set(Double.parseDouble(margenComercial));
        articuloFX.pvpArticuloProperty().set(Double.parseDouble(pvp));
        articuloFX.proveedorArticuloProperty().set(Integer.parseInt(proveedor));
        articuloFX.stockArticuloProperty().set(Double.parseDouble(stock));
        articuloFX.observacionesArticuloProperty().set(observaciones);

        ArticuloHibernate articuloHibernate = ArticuloAdapter.toHibernate(articuloFX);
        articuloDAO.save(articuloHibernate);
        cargarArticulos(); // Recargar la lista después de agregar
    }

    /**
     * Obtiene todos los artículos de la base de datos y los convierte en ArticuloFX para la interfaz.
     */
    public void cargarArticulos() {
        List<ArticuloHibernate> articulosBD = articuloDAO.getAll();
        List<ArticuloFX> articulosFX = articulosBD.stream()
                .map(ArticuloAdapter::fromHibernate)
                .toList();
        listaArticulos.setAll(articulosFX);
    }

    /**
     * Retorna la lista observable de artículos para la interfaz gráfica.
     */
    public ObservableList<ArticuloFX> getListaArticulos() {
        return listaArticulos;
    }

    /**
     * Agrega un nuevo artículo, lo guarda en la base de datos y actualiza la lista.
     */
    public void agregarArticulo(ArticuloFX articuloFX) {
        ArticuloHibernate articuloHibernate = ArticuloAdapter.toHibernate(articuloFX);
        articuloDAO.save(articuloHibernate);
        cargarArticulos();
    }

    /**
     * Elimina un artículo de la base de datos y actualiza la lista.
     */
    public void eliminarArticuloSeleccionado(Object seleccionado) {
        if (seleccionado instanceof ArticuloFX articuloFX) {
            ArticuloHibernate articuloHibernate = ArticuloAdapter.toHibernate(articuloFX);
            articuloDAO.delete(articuloHibernate);
            cargarArticulos();
        }
    }
}
