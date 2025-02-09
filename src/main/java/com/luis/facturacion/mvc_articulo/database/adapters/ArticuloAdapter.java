package com.luis.facturacion.mvc_articulo.database.adapters;

import com.luis.facturacion.mvc_articulo.database.entities_hibernate.ArticuloHibernate;
import com.luis.facturacion.mvc_articulo.database.entitiesfx.ArticuloFX;

public class ArticuloAdapter {

        // Convertir de ArticuloEntity a Articulo (JavaFX)
        public static ArticuloFX fromHibernate(ArticuloHibernate articuloHibernate) {
            ArticuloFX articulo = new ArticuloFX();
            articulo.idArticuloProperty().set(articuloHibernate.getIdArticulo());
            articulo.codigoArticuloProperty().set(articuloHibernate.getCodigoArticulo());
            articulo.codigoBarrasArticuloProperty().set(articuloHibernate.getCodigoBarrasArticulo());
            articulo.descripcionArticuloProperty().set(articuloHibernate.getDescripcionArticulo());
            articulo.familiaArticuloProperty().set(articuloHibernate.getFamiliaArticulo());
            articulo.costeArticuloProperty().set(articuloHibernate.getCosteArticulo());
            articulo.margenComercialArticuloProperty().set(articuloHibernate.getMargenComercialArticulo());
            articulo.pvpArticuloProperty().set(articuloHibernate.getPvpArticulo());
            articulo.proveedorArticuloProperty().set(articuloHibernate.getProveedorArticulo());
            articulo.stockArticuloProperty().set(articuloHibernate.getStockArticulo());
            articulo.observacionesArticuloProperty().set(articuloHibernate.getObservacionesArticulo());
            return articulo;
        }

        // Convertir de Articulo (JavaFX) a ArticuloEntity (Hibernate)
        public static ArticuloHibernate toHibernate(ArticuloFX articuloFX) {
            ArticuloHibernate articuloHibernate = new ArticuloHibernate();
            articuloHibernate.setIdArticulo(articuloFX.idArticuloProperty().get());
            articuloHibernate.setCodigoArticulo(articuloFX.codigoArticuloProperty().get());
            articuloHibernate.setCodigoBarrasArticulo(articuloFX.codigoBarrasArticuloProperty().get());
            articuloHibernate.setDescripcionArticulo(articuloFX.descripcionArticuloProperty().get());
            articuloHibernate.setFamiliaArticulo(articuloFX.familiaArticuloProperty().get());
            articuloHibernate.setCosteArticulo(articuloFX.costeArticuloProperty().get());
            articuloHibernate.setMargenComercialArticulo(articuloFX.margenComercialArticuloProperty().get());
            articuloHibernate.setPvpArticulo(articuloFX.pvpArticuloProperty().get());
            articuloHibernate.setProveedorArticulo(articuloFX.proveedorArticuloProperty().get());
            articuloHibernate.setStockArticulo(articuloFX.stockArticuloProperty().get());
            articuloHibernate.setObservacionesArticulo(articuloFX.observacionesArticuloProperty().get());
            return articuloHibernate;
        }


}
