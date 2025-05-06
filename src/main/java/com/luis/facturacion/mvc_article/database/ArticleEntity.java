package com.luis.facturacion.mvc_article.database;

import jakarta.persistence.*;

@Entity
@Table(name = "articulos")
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int articleId;

    @Column(name = "ind", unique = true, nullable = false, length = 35)
    private int index;

    @Column(name = "nombre", length = 35)
    private String name;

    // Getters y Setters

    public ArticleEntity() {
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
