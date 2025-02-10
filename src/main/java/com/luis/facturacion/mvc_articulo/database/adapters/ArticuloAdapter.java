package com.luis.facturacion.mvc_articulo.database.adapters;

import com.luis.facturacion.mvc_articulo.database.entities_hibernate.ArticuloEntity;
import com.luis.facturacion.mvc_articulo.database.entitiesfx.ArticuloFX;

public class ArticuloAdapter {

        // Convertir de ArticuloEntity a Articulo (JavaFX)
        public static ArticuloFX fromHibernate(ArticuloEntity articuloEntity) {
            ArticuloFX articulo = new ArticuloFX();
            articulo.idArticuloProperty().set(articuloEntity.getIdArticulo());
            articulo.codigoArticuloProperty().set(articuloEntity.getCodigoArticulo());
            articulo.codigoBarrasArticuloProperty().set(articuloEntity.getCodigoBarrasArticulo());
            articulo.descripcionArticuloProperty().set(articuloEntity.getDescripcionArticulo());
            articulo.familiaArticuloProperty().set(articuloEntity.getFamiliaArticulo());
            articulo.costeArticuloProperty().set(articuloEntity.getCosteArticulo());
            articulo.margenComercialArticuloProperty().set(articuloEntity.getMargenComercialArticulo());
            articulo.pvpArticuloProperty().set(articuloEntity.getPvpArticulo());
            articulo.proveedorArticuloProperty().set(articuloEntity.getProveedorArticulo());
            articulo.stockArticuloProperty().set(articuloEntity.getStockArticulo());
            articulo.observacionesArticuloProperty().set(articuloEntity.getObservacionesArticulo());
            return articulo;
        }

        // Convertir de Articulo (JavaFX) a ArticuloEntity (Hibernate)
        public static ArticuloEntity toHibernate(ArticuloFX articuloFX) {
            ArticuloEntity articuloEntity = new ArticuloEntity();
            articuloEntity.setIdArticulo(articuloFX.idArticuloProperty().get());
            articuloEntity.setCodigoArticulo(articuloFX.codigoArticuloProperty().get());
            articuloEntity.setCodigoBarrasArticulo(articuloFX.codigoBarrasArticuloProperty().get());
            articuloEntity.setDescripcionArticulo(articuloFX.descripcionArticuloProperty().get());
            articuloEntity.setFamiliaArticulo(articuloFX.familiaArticuloProperty().get());
            articuloEntity.setCosteArticulo(articuloFX.costeArticuloProperty().get());
            articuloEntity.setMargenComercialArticulo(articuloFX.margenComercialArticuloProperty().get());
            articuloEntity.setPvpArticulo(articuloFX.pvpArticuloProperty().get());
            articuloEntity.setProveedorArticulo(articuloFX.proveedorArticuloProperty().get());
            articuloEntity.setStockArticulo(articuloFX.stockArticuloProperty().get());
            articuloEntity.setObservacionesArticulo(articuloFX.observacionesArticuloProperty().get());
            return articuloEntity;
        }


}
