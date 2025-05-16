package com.luis.facturacion.mvc_invoice.database;
import jakarta.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "facturas")
public class InvoiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "cliente_id")
    private Integer clientId;

    @Column(name = "fecha")
    private LocalDate date;

    @Column(name = "tipo")
    private Integer type;

    @Column(name = "importe_total")
    private Double totalAmount;

    public InvoiceEntity() {
    }

    public InvoiceEntity(Integer clientId, LocalDate date, Integer type, Double totalAmount) {
        this.clientId = clientId;
        this.date = date;
        this.type = type;
        this.totalAmount = totalAmount;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "InvoiceEntity{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", date=" + date +
                ", type=" + type +
                ", totalAmount=" + totalAmount +
                '}';
    }
}