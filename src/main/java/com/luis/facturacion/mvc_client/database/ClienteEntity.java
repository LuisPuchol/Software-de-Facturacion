package com.luis.facturacion.mvc_client.database;
import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "clientes")
public class ClienteEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombreCliente", length = 80, nullable = false, unique = true)
    private String nombreCliente;

    @Column(name = "direccionCliente", length = 80)
    private String direccionCliente;

    @Column(name = "cpCliente", length = 10)
    private String cpCliente;

    @Column(name = "poblacionCliente", length = 80)
    private String poblacionCliente;

    @Column(name = "provinciaCliente", length = 60)
    private String provinciaCliente;

    @Column(name = "paisCliente", length = 60)
    private String paisCliente;

    @Column(name = "cifCliente", length = 12, nullable = false, unique = true)
    private String cifCliente;

    @Column(name = "telCliente", length = 16)
    private String telCliente;

    @Column(name = "emailCliente", length = 80)
    private String emailCliente;

    @Column(name = "ibanCliente", length = 40)
    private String ibanCliente;

    @Column(name = "riesgoCliente")
    private Double riesgoCliente;

    @Column(name = "descuentoCliente")
    private Double descuentoCliente;

    @Column(name = "observacionesCliente", columnDefinition = "TEXT")
    private String observacionesCliente;

    // Constructores
    public ClienteEntity() {
    }

    public ClienteEntity(String nombreCliente, String direccionCliente, String cpCliente, String poblacionCliente,
                         String provinciaCliente, String paisCliente, String cifCliente, String telCliente,
                         String emailCliente, String ibanCliente, Double riesgoCliente, Double descuentoCliente,
                         String observacionesCliente) {
        this.nombreCliente = nombreCliente;
        this.direccionCliente = direccionCliente;
        this.cpCliente = cpCliente;
        this.poblacionCliente = poblacionCliente;
        this.provinciaCliente = provinciaCliente;
        this.paisCliente = paisCliente;
        this.cifCliente = cifCliente;
        this.telCliente = telCliente;
        this.emailCliente = emailCliente;
        this.ibanCliente = ibanCliente;
        this.riesgoCliente = riesgoCliente;
        this.descuentoCliente = descuentoCliente;
        this.observacionesCliente = observacionesCliente;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    public String getCpCliente() {
        return cpCliente;
    }

    public void setCpCliente(String cpCliente) {
        this.cpCliente = cpCliente;
    }

    public String getPoblacionCliente() {
        return poblacionCliente;
    }

    public void setPoblacionCliente(String poblacionCliente) {
        this.poblacionCliente = poblacionCliente;
    }

    public String getProvinciaCliente() {
        return provinciaCliente;
    }

    public void setProvinciaCliente(String provinciaCliente) {
        this.provinciaCliente = provinciaCliente;
    }

    public String getPaisCliente() {
        return paisCliente;
    }

    public void setPaisCliente(String paisCliente) {
        this.paisCliente = paisCliente;
    }

    public String getCifCliente() {
        return cifCliente;
    }

    public void setCifCliente(String cifCliente) {
        this.cifCliente = cifCliente;
    }

    public String getTelCliente() {
        return telCliente;
    }

    public void setTelCliente(String telCliente) {
        this.telCliente = telCliente;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public String getIbanCliente() {
        return ibanCliente;
    }

    public void setIbanCliente(String ibanCliente) {
        this.ibanCliente = ibanCliente;
    }

    public Double getRiesgoCliente() {
        return riesgoCliente;
    }

    public void setRiesgoCliente(Double riesgoCliente) {
        this.riesgoCliente = riesgoCliente;
    }

    public Double getDescuentoCliente() {
        return descuentoCliente;
    }

    public void setDescuentoCliente(Double descuentoCliente) {
        this.descuentoCliente = descuentoCliente;
    }

    public String getObservacionesCliente() {
        return observacionesCliente;
    }

    public void setObservacionesCliente(String observacionesCliente) {
        this.observacionesCliente = observacionesCliente;
    }

    @Override
    public String toString() {
        return "ClienteEntity{" +
                "id=" + id +
                ", nombreCliente='" + nombreCliente + '\'' +
                ", direccionCliente='" + direccionCliente + '\'' +
                ", cpCliente='" + cpCliente + '\'' +
                ", poblacionCliente='" + poblacionCliente + '\'' +
                ", provinciaCliente='" + provinciaCliente + '\'' +
                ", paisCliente='" + paisCliente + '\'' +
                ", cifCliente='" + cifCliente + '\'' +
                ", telCliente='" + telCliente + '\'' +
                ", emailCliente='" + emailCliente + '\'' +
                ", ibanCliente='" + ibanCliente + '\'' +
                ", riesgoCliente=" + riesgoCliente +
                ", descuentoCliente=" + descuentoCliente +
                ", observacionesCliente='" + observacionesCliente + '\'' +
                '}';
    }
}

