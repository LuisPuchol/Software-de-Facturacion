package com.luis.facturacion.view;

import javafx.beans.property.*;

public class Articulo {
    private final IntegerProperty idArticulo = new SimpleIntegerProperty();
    private final StringProperty codigoArticulo = new SimpleStringProperty();
    private final StringProperty codigoBarrasArticulo = new SimpleStringProperty();
    private final StringProperty descripcionArticulo = new SimpleStringProperty();
    private final IntegerProperty familiaArticulo = new SimpleIntegerProperty();
    private final DoubleProperty costeArticulo = new SimpleDoubleProperty();
    private final DoubleProperty margenComercialArticulo = new SimpleDoubleProperty();
    private final DoubleProperty pvpArticulo = new SimpleDoubleProperty();
    private final IntegerProperty proveedorArticulo = new SimpleIntegerProperty();
    private final DoubleProperty stockArticulo = new SimpleDoubleProperty();
    private final StringProperty observacionesArticulo = new SimpleStringProperty();

    private final StringProperty codigo;
    private final StringProperty nombre;

    public Articulo(String codigo, String nombre) {
        this.codigo = new SimpleStringProperty(codigo);
        this.nombre = new SimpleStringProperty(nombre);
    }

    public String getCodigo() {
        return codigo.get();
    }

    public void setCodigo(String codigo) {
        this.codigo.set(codigo);
    }

    public StringProperty codigoProperty() {
        return codigo;
    }

    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    //los otros getters y setters


    public int getIdArticulo() {
        return idArticulo.get();
    }

    public IntegerProperty idArticuloProperty() {
        return idArticulo;
    }

    public String getCodigoArticulo() {
        return codigoArticulo.get();
    }

    public StringProperty codigoArticuloProperty() {
        return codigoArticulo;
    }

    public String getCodigoBarrasArticulo() {
        return codigoBarrasArticulo.get();
    }

    public StringProperty codigoBarrasArticuloProperty() {
        return codigoBarrasArticulo;
    }

    public String getDescripcionArticulo() {
        return descripcionArticulo.get();
    }

    public StringProperty descripcionArticuloProperty() {
        return descripcionArticulo;
    }

    public int getFamiliaArticulo() {
        return familiaArticulo.get();
    }

    public IntegerProperty familiaArticuloProperty() {
        return familiaArticulo;
    }

    public double getCosteArticulo() {
        return costeArticulo.get();
    }

    public DoubleProperty costeArticuloProperty() {
        return costeArticulo;
    }

    public double getMargenComercialArticulo() {
        return margenComercialArticulo.get();
    }

    public DoubleProperty margenComercialArticuloProperty() {
        return margenComercialArticulo;
    }

    public double getPvpArticulo() {
        return pvpArticulo.get();
    }

    public DoubleProperty pvpArticuloProperty() {
        return pvpArticulo;
    }

    public int getProveedorArticulo() {
        return proveedorArticulo.get();
    }

    public IntegerProperty proveedorArticuloProperty() {
        return proveedorArticulo;
    }

    public double getStockArticulo() {
        return stockArticulo.get();
    }

    public DoubleProperty stockArticuloProperty() {
        return stockArticulo;
    }

    public String getObservacionesArticulo() {
        return observacionesArticulo.get();
    }

    public StringProperty observacionesArticuloProperty() {
        return observacionesArticulo;
    }
}
