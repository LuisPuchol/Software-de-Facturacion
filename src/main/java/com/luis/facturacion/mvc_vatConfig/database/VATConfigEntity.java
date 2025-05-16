package com.luis.facturacion.mvc_vatConfig.database;

import jakarta.persistence.*;

@Entity
@Table(name = "iva")
public class VATConfigEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "porcentaje")
    private Double vatRate;

    @Column(name = "recargo_equivalencia")
    private Double surchargeRate;

    public VATConfigEntity() {
    }

    public VATConfigEntity(Double vatRate, Double surchargeRate) {
        this.vatRate = vatRate;
        this.surchargeRate = surchargeRate;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getVatRate() {
        return vatRate;
    }

    public void setVatRate(Double vatRate) {
        this.vatRate = vatRate;
    }

    public Double getSurchargeRate() {
        return surchargeRate;
    }

    public void setSurchargeRate(Double surchargeRate) {
        this.surchargeRate = surchargeRate;
    }

    @Override
    public String toString() {
        return "VATConfigEntity{" +
                "id=" + id +
                ", vatRate=" + vatRate +
                ", surchargeRate=" + surchargeRate +
                '}';
    }
}
