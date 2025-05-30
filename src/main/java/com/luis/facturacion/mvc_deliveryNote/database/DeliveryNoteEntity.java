package com.luis.facturacion.mvc_deliveryNote.database;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "albaranes")
public class DeliveryNoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "ind")
    private Integer index;

    @Column(name = "cliente_id")
    private Integer clientId;

    @Column(name = "fecha")
    private LocalDate date;

    @Column(name = "num_factura")
    private Integer invoiceNumber;

    @Column(name = "importe_total")
    private Double totalAmount;

    public DeliveryNoteEntity() {
    }

    public DeliveryNoteEntity(Integer index, Integer clientId, LocalDate date, Double totalAmount) {
        this.index = index;
        this.clientId = clientId;
        this.date = date;
        this.totalAmount = totalAmount;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(Integer invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "DeliveryNoteEntity{" +
                "id=" + id +
                ", index=" + index +
                ", clientId=" + clientId +
                ", date=" + date +
                ", invoiceNumber=" + invoiceNumber +
                '}';
    }
}