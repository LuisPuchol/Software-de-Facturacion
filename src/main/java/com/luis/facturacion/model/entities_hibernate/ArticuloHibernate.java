package com.luis.facturacion.model.entities_hibernate;


import jakarta.persistence.*;

@Entity
@Table(name = "articulos")
public class ArticuloHibernate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idArticulo;

    @Column(name = "codigo_articulo", nullable = false, unique = true)
    private String codigoArticulo;

    @Column(name = "codigo_barras", length = 80)
    private String codigoBarrasArticulo;

    @Column(name = "descripcion", length = 60)
    private String descripcionArticulo;

    @Column(name = "familia")
    private int familiaArticulo;

    @Column(name = "coste")
    private double costeArticulo;

    @Column(name = "margen_comercial")
    private double margenComercialArticulo;

    @Column(name = "pvp")
    private double pvpArticulo;

    @Column(name = "proveedor")
    private int proveedorArticulo;

    @Column(name = "stock")
    private double stockArticulo;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observacionesArticulo;

    // Getters y Setters

    public int getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(int idArticulo) {
        this.idArticulo = idArticulo;
    }

    public String getCodigoArticulo() {
        return codigoArticulo;
    }

    public void setCodigoArticulo(String codigoArticulo) {
        this.codigoArticulo = codigoArticulo;
    }

    public String getCodigoBarrasArticulo() {
        return codigoBarrasArticulo;
    }

    public void setCodigoBarrasArticulo(String codigoBarrasArticulo) {
        this.codigoBarrasArticulo = codigoBarrasArticulo;
    }

    public String getDescripcionArticulo() {
        return descripcionArticulo;
    }

    public void setDescripcionArticulo(String descripcionArticulo) {
        this.descripcionArticulo = descripcionArticulo;
    }

    public int getFamiliaArticulo() {
        return familiaArticulo;
    }

    public void setFamiliaArticulo(int familiaArticulo) {
        this.familiaArticulo = familiaArticulo;
    }

    public double getCosteArticulo() {
        return costeArticulo;
    }

    public void setCosteArticulo(double costeArticulo) {
        this.costeArticulo = costeArticulo;
    }

    public double getMargenComercialArticulo() {
        return margenComercialArticulo;
    }

    public void setMargenComercialArticulo(double margenComercialArticulo) {
        this.margenComercialArticulo = margenComercialArticulo;
    }

    public double getPvpArticulo() {
        return pvpArticulo;
    }

    public void setPvpArticulo(double pvpArticulo) {
        this.pvpArticulo = pvpArticulo;
    }

    public int getProveedorArticulo() {
        return proveedorArticulo;
    }

    public void setProveedorArticulo(int proveedorArticulo) {
        this.proveedorArticulo = proveedorArticulo;
    }

    public double getStockArticulo() {
        return stockArticulo;
    }

    public void setStockArticulo(double stockArticulo) {
        this.stockArticulo = stockArticulo;
    }

    public String getObservacionesArticulo() {
        return observacionesArticulo;
    }

    public void setObservacionesArticulo(String observacionesArticulo) {
        this.observacionesArticulo = observacionesArticulo;
    }
}
