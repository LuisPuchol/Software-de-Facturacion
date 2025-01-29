package com.luis.facturacion.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Articulo {
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
}
