package com.luis.facturacion.model.dao;



import com.luis.facturacion.model.entities_hibernate.ArticuloHibernate;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ArticuloDAO{

    public ArticuloDAO() {
        System.out.println("ArticuloDAO inicializado");
    }

    public void save(ArticuloHibernate articulo) {
        // Método para guardar un artículo en la base de datos
    }

    public void update(ArticuloHibernate articulo) {
        // Método para actualizar un artículo en la base de datos
    }

    public void delete(ArticuloHibernate articulo) {
        // Método para eliminar un artículo de la base de datos
    }

    public ArticuloHibernate getById(int id) {
        // Método para obtener un artículo por su ID
        return null;
    }

    public List<ArticuloHibernate> getAll() {
        // Método para obtener todos los artículos de la base de datos
        return Collections.emptyList();
    }
}
