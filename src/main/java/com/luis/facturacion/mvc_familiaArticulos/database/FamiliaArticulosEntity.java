package com.luis.facturacion.mvc_familiaArticulos.database;

import jakarta.persistence.*;

@Entity
@Table(name = "familiaarticulos")
public class FamiliaArticulosEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idFamiliaArticulos")
    private int idFamiliaArticulos;

    @Column(name = "codigoFamiliaArticulos", length = 40)
    private String codigoFamiliaArticulos;

    @Column(name = "denominacionFamilias", length = 80)
    private String denominacionFamilias;

    public FamiliaArticulosEntity() {
    }

    public int getIdFamiliaArticulos() {
        return idFamiliaArticulos;
    }

    public void setIdFamiliaArticulos(int idFamiliaArticulos) {
        this.idFamiliaArticulos = idFamiliaArticulos;
    }

    public String getCodigoFamiliaArticulos() {
        return codigoFamiliaArticulos;
    }

    public void setCodigoFamiliaArticulos(String codigoFamiliaArticulos) {
        this.codigoFamiliaArticulos = codigoFamiliaArticulos;
    }

    public String getDenominacionFamilias() {
        return denominacionFamilias;
    }

    public void setDenominacionFamilias(String denominacionFamilias) {
        this.denominacionFamilias = denominacionFamilias;
    }
}
