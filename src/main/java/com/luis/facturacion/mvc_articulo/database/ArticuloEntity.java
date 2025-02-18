package com.luis.facturacion.mvc_articulo.database;


import com.luis.facturacion.mvc_familiaArticulos.database.FamiliaArticulosEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "articulos")
public class ArticuloEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idArticulo")
    private int idArticulo;

    @Column(name = "codigoArticulo", unique = true, nullable = false, length = 40)
    private String codigoArticulo;

    @Column(name = "codigoBarrasArticulo", length = 80)
    private String codigoBarrasArticulo;

    @Column(name = "descripcionArticulo", length = 60)
    private String descripcionArticulo;

    @ManyToOne
    @JoinColumn(name = "familiaArticulo", referencedColumnName = "idFamiliaArticulos", nullable = false)
    private FamiliaArticulosEntity familiaArticulo;

    @Column(name = "costeArticulo")
    private double costeArticulo;

    @Column(name = "margenComercialArticulo")
    private double margenComercialArticulo;

    @Column(name = "pvpArticulo")
    private double pvpArticulo;

    @Column(name = "proveedorArticulo")
    private int proveedorArticulo;

    @Column(name = "stockArticulo")
    private double stockArticulo;

    @Column(name = "observacionesArticulo", columnDefinition = "TEXT")
    private String observacionesArticulo;

    // Getters y Setters


    public ArticuloEntity() {
    }

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

    public FamiliaArticulosEntity getFamiliaArticulo() {
        return familiaArticulo;
    }

    public void setFamiliaArticulo(FamiliaArticulosEntity familiaArticulo) {
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
