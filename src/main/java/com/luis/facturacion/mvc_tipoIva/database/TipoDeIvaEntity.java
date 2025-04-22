package com.luis.facturacion.mvc_tipoIva.database;

import jakarta.persistence.*;

@Entity
@Table(name = "tiposIva")
public class TipoDeIvaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTipoIva")
    private int idTipoIva;

    @Column(name = "iva", nullable = false)
    private double iva;

    @Column(name = "observacionesTipoIva", columnDefinition = "TEXT")
    private String observacionesTipoIva;

    public TipoDeIvaEntity() {
    }

    public int getIdTipoIva() {
        return idTipoIva;
    }

    public void setIdTipoIva(int idTipoIva) {
        this.idTipoIva = idTipoIva;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public String getObservacionesTipoIva() {
        return observacionesTipoIva;
    }

    public void setObservacionesTipoIva(String observacionesTipoIva) {
        this.observacionesTipoIva = observacionesTipoIva;
    }
}

