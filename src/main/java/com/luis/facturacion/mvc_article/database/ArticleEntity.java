package com.luis.facturacion.mvc_article.database;

import jakarta.persistence.*;

@Entity
@Table(name = "articulos")
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int idArticulo;

    @Column(name = "ind", unique = true, nullable = false, length = 35)
    private int indice;

    @Column(name = "nombre", length = 35)
    private String nombre;

    // Getters y Setters

    public ArticleEntity() {
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int index) {
        this.indice = index;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String name) {
        this.nombre = name;
    }
}
