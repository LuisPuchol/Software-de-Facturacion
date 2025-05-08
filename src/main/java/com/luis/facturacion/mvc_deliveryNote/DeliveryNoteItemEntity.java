package com.luis.facturacion.mvc_deliveryNote;

import java.math.BigDecimal;

public class DeliveryNoteItemEntity {
    private Long id;
    private Long albaranId;
    private Long articuloId;
    private String trazabilidad1;
    private String trazabilidad2;
    private double cantidad;
    private BigDecimal precio;

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getAlbaranId() { return albaranId; }
    public void setAlbaranId(Long albaranId) { this.albaranId = albaranId; }

    public Long getArticuloId() { return articuloId; }
    public void setArticuloId(Long articuloId) { this.articuloId = articuloId; }

    public String getTrazabilidad1() { return trazabilidad1; }
    public void setTrazabilidad1(String trazabilidad1) { this.trazabilidad1 = trazabilidad1; }

    public String getTrazabilidad2() { return trazabilidad2; }
    public void setTrazabilidad2(String trazabilidad2) { this.trazabilidad2 = trazabilidad2; }

    public double getCantidad() { return cantidad; }
    public void setCantidad(double cantidad) { this.cantidad = cantidad; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
}