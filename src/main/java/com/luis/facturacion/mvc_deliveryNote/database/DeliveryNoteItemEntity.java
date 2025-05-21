package com.luis.facturacion.mvc_deliveryNote.database;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "registros")
public class DeliveryNoteItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "albaran_id")
    private Integer deliveryNoteID;

    @Column(name = "articulo_id")
    private Integer articleID;

    @Column(name = "tz1", length = 15)
    private String trace1;

    @Column(name = "tz2", length = 15)
    private String trace2;

    @Column(name = "cantidad", length = 15)
    private double quantity;

    @Column(name = "precio")
    private BigDecimal price;

    public DeliveryNoteItemEntity() {
    }

    public DeliveryNoteItemEntity(Integer deliveryNoteID, Integer articleID, String trace1,
                                  String trace2, double quantity, BigDecimal price) {
        this.deliveryNoteID = deliveryNoteID;
        this.articleID = articleID;
        this.trace1 = trace1;
        this.trace2 = trace2;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters y setters
    public Integer getId() {
        return id;
    }

    public Integer getDeliveryNoteID() {
        return deliveryNoteID;
    }

    public void setDeliveryNoteID(Integer deliveryNoteID) {
        this.deliveryNoteID = deliveryNoteID;
    }

    public Integer getArticleID() {
        return articleID;
    }

    public void setArticleID(Integer articleID) {
        this.articleID = articleID;
    }

    public String getTrace1() {
        return trace1;
    }

    public void setTrace1(String trace1) {
        this.trace1 = trace1;
    }

    public String getTrace2() {
        return trace2;
    }

    public void setTrace2(String trace2) {
        this.trace2 = trace2;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}