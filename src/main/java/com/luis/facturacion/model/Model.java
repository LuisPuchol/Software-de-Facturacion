package com.luis.facturacion.model;

import com.luis.facturacion.controller.LoginController;
import com.luis.facturacion.controller.MainMenuController;
import com.luis.facturacion.model.adapters.ArticuloAdapter;
import com.luis.facturacion.model.dao.ArticuloDAO;
import com.luis.facturacion.model.entities_hibernate.ArticuloHibernate;
import com.luis.facturacion.model.entitiesfx.ArticuloFX;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

public class Model {
    private static Model instance;
    private final ArticuloDAO articuloDAO;
    private final ObservableList<ArticuloFX> listaArticulos;

    public Model() {
        System.out.println("Model created");
        this.articuloDAO = new ArticuloDAO();
        this.listaArticulos = FXCollections.observableArrayList();
    }

    public static Model getInstance() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }

    /**
     * Obtiene todos los artículos de la base de datos y los convierte en ArticuloFX para la interfaz.
     */
    public void cargarArticulos() {
        List<ArticuloHibernate> articulosBD = articuloDAO.getAll();
        List<ArticuloFX> articulosFX = articulosBD.stream()
                .map(ArticuloAdapter::fromEntity)
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
    public void eliminarArticulo(ArticuloFX articuloFX) {
        ArticuloHibernate articuloHibernate = ArticuloAdapter.toHibernate(articuloFX);
        articuloDAO.delete(articuloHibernate);
        cargarArticulos();
    }
}
