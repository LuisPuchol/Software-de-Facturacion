package com.luis.facturacion.mvc_vatConfig.database;

import jakarta.persistence.*;

@Entity
@Table(name = "tiposIva")
public class VATConfigEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTipoIva")
    private int id;

    @Column(name = "iva", nullable = false)
    private double value;

    @Column(name = "observacionesTipoIva", columnDefinition = "TEXT")
    private String observationsVAT;

    public VATConfigEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getObservationsVAT() {
        return observationsVAT;
    }

    public void setObservationsVAT(String observationsVAT) {
        this.observationsVAT = observationsVAT;
    }
}

