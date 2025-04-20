package com.luis.facturacion.mvc_articulo.database;

import jakarta.persistence.*;

@Entity
@Table(name = "articulos")
public class ArticuloEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int idArticulo;

    @Column(name = "ind", unique = true, nullable = false, length = 35)
    private int indice;

    @Column(name = "nombre", length = 35)
    private String nombre;

    // Getters y Setters

    public ArticuloEntity() {
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
