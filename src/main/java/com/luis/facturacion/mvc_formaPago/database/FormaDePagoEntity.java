package com.luis.facturacion.mvc_formaPago.database;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "formaPago")
public class FormaDePagoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idFormaPago")
    private int idFormaPago;

    @Column(name = "tipoFormaPago", length = 40, nullable = false)
    private String tipoFormaPago;

    @Column(name = "fechaCobroFormaPago", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaCobroFormaPago;

    @Column(name = "observacionesFormaPago", columnDefinition = "TEXT")
    private String observacionesFormaPago;

    public FormaDePagoEntity() {
    }

    public int getIdFormaPago() {
        return idFormaPago;
    }

    public void setIdFormaPago(int idFormaPago) {
        this.idFormaPago = idFormaPago;
    }

    public String getTipoFormaPago() {
        return tipoFormaPago;
    }

    public void setTipoFormaPago(String tipoFormaPago) {
        this.tipoFormaPago = tipoFormaPago;
    }

    public Date getFechaCobroFormaPago() {
        return fechaCobroFormaPago;
    }

    public void setFechaCobroFormaPago(Date fechaCobroFormaPago) {
        this.fechaCobroFormaPago = fechaCobroFormaPago;
    }

    public String getObservacionesFormaPago() {
        return observacionesFormaPago;
    }

    public void setObservacionesFormaPago(String observacionesFormaPago) {
        this.observacionesFormaPago = observacionesFormaPago;
    }
}

