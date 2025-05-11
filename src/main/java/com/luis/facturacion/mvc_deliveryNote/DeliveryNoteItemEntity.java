package com.luis.facturacion.mvc_deliveryNote;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "registros")
public class DeliveryNoteItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long index;

    @Column(name = "albaran_id")
    private Long deliveryNoteID;

    @Column(name = "articulo_id")
    private Long articleID;

    @Column(name = "tz1", length = 15)
    private String trace1;

    @Column(name = "tz2", length = 15)
    private String trace2;

    @Column(name = "cantidad", length = 15)
    private double quantity;

    @Column(name = "precio")
    private BigDecimal amount;

    public DeliveryNoteItemEntity() {
    }

    public DeliveryNoteItemEntity(Long index, Long deliveryNoteID, Long articleID, String trace1,
                                  String trace2, double quantity, BigDecimal amount) {
        this.index = index;
        this.deliveryNoteID = deliveryNoteID;
        this.articleID = articleID;
        this.trace1 = trace1;
        this.trace2 = trace2;
        this.quantity = quantity;
        this.amount = amount;
    }

    // Getters y setters
    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    public Long getDeliveryNoteID() {
        return deliveryNoteID;
    }

    public void setDeliveryNoteID(Long deliveryNoteID) {
        this.deliveryNoteID = deliveryNoteID;
    }

    public Long getArticleID() {
        return articleID;
    }

    public void setArticleID(Long articleID) {
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

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}