package com.luis.facturacion.mvc_client.database;
import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "clientes")
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "ind")
    private Integer ind;

    @Column(name = "nombre", length = 30)
    private String nombre;

    @Column(name = "direccion", length = 30)
    private String direccion;

    @Column(name = "cod_postal", length = 5)
    private String codPostal;

    @Column(name = "poblacion", length = 20)
    private String poblacion;

    @Column(name = "provincia", length = 20)
    private String provincia;
    @Column(name = "cif", length = 12)
    private String cif;

    @Column(name = "tel", length = 20)
    private String tel;

    @Column(name = "tel2", length = 20)
    private String tel2;


    @Column(name = "req_equivalencia")
    private Integer reqEquivalencia;

    @Column(name = "tipo_cliente")
    private Integer tipoCliente;

    @Column(name = "factura_por_albaran")
    private Integer facturaPorAlbaran;

    // Constructores
    public ClienteEntity() {
    }

    public ClienteEntity(Integer ind, String nombre, String direccion, String codPostal, String poblacion,
                         String provincia, String cif, String tel, String tel2, Integer reqEquivalencia,
                         Integer tipoCliente, Integer facturaPorAlbaran) {
        this.ind = ind;
        this.nombre = nombre;
        this.direccion = direccion;
        this.codPostal = codPostal;
        this.poblacion = poblacion;
        this.provincia = provincia;
        this.cif = cif;
        this.tel = tel;
        this.tel2 = tel2;
        this.reqEquivalencia = reqEquivalencia;
        this.tipoCliente = tipoCliente;
        this.facturaPorAlbaran = facturaPorAlbaran;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInd() {
        return ind;
    }

    public void setInd(Integer ind) {
        this.ind = ind;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    public Integer getReqEquivalencia() {
        return reqEquivalencia;
    }

    public void setReqEquivalencia(Integer reqEquivalencia) {
        this.reqEquivalencia = reqEquivalencia;
    }

    public Integer getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(Integer tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public Integer getFacturaPorAlbaran() {
        return facturaPorAlbaran;
    }

    public void setFacturaPorAlbaran(Integer facturaPorAlbaran) {
        this.facturaPorAlbaran = facturaPorAlbaran;
    }

    @Override
    public String toString() {
        return "ClienteEntity{" +
                "id=" + id +
                ", ind=" + ind +
                ", nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", codPostal='" + codPostal + '\'' +
                ", poblacion='" + poblacion + '\'' +
                ", provincia='" + provincia + '\'' +
                ", cif='" + cif + '\'' +
                ", tel='" + tel + '\'' +
                ", tel2='" + tel2 + '\'' +
                ", reqEquivalencia=" + reqEquivalencia +
                ", tipoCliente=" + tipoCliente +
                ", facturaPorAlbaran=" + facturaPorAlbaran +
                '}';
    }
}