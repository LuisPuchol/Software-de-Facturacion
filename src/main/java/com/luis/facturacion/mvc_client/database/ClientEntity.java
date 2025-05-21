package com.luis.facturacion.mvc_client.database;
import jakarta.persistence.*;

@Entity
@Table(name = "clientes")
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "ind")
    private Integer index;

    @Column(name = "nombre", length = 30)
    private String name;

    @Column(name = "direccion", length = 30)
    private String address;

    @Column(name = "cod_postal", length = 5)
    private String postalCode;

    @Column(name = "poblacion", length = 20)
    private String city;

    @Column(name = "provincia", length = 20)
    private String province;

    @Column(name = "cif", length = 12)
    private String cif;

    @Column(name = "tel", length = 20)
    private String tel;

    @Column(name = "tel2", length = 20)
    private String tel2;
    // TODO Change table name on MYSQL
    @Column(name = "req_equivalencia")
    private Integer equivalenceSurcharge;

    @Column(name = "tipo_cliente")
    private Integer clientType;

    @Column(name = "factura_por_albaran")
    private Integer InvoiceByDeliveryNote;

    // Constructors
    public ClientEntity() {
    }

    public ClientEntity(Integer index, String name, String address, String postalCode, String city,
                        String province, String cif, String tel, String tel2, Integer equivalenceSurcharge,
                        Integer clientType, Integer InvoiceByDeliveryNote) {
        this.index = index;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
        this.province = province;
        this.cif = cif;
        this.tel = tel;
        this.tel2 = tel2;
        this.equivalenceSurcharge = equivalenceSurcharge;
        this.clientType = clientType;
        this.InvoiceByDeliveryNote = InvoiceByDeliveryNote;
    }

    // Getters & Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
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

    public Integer getEquivalenceSurcharge() {
        return equivalenceSurcharge;
    }

    public void setEquivalenceSurcharge(Integer equivalenceSurcharge) {
        this.equivalenceSurcharge = equivalenceSurcharge;
    }

    public Integer getClientType() {
        return clientType;
    }

    public void setClientType(Integer clientType) {
        this.clientType = clientType;
    }

    public Integer getInvoiceByDeliveryNote() {
        return InvoiceByDeliveryNote;
    }

    public void setInvoiceByDeliveryNote(Integer invoiceByDeliveryNote) {
        this.InvoiceByDeliveryNote = invoiceByDeliveryNote;
    }

    @Override
    public String toString() {
        return "ClienteEntity{" +
                "id=" + id +
                ", ind=" + index +
                ", nombre='" + name + '\'' +
                ", direccion='" + address + '\'' +
                ", codPostal='" + postalCode + '\'' +
                ", poblacion='" + city + '\'' +
                ", provincia='" + province + '\'' +
                ", cif='" + cif + '\'' +
                ", tel='" + tel + '\'' +
                ", tel2='" + tel2 + '\'' +
                ", reqEquivalencia=" + equivalenceSurcharge +
                ", tipoCliente=" + clientType +
                ", facturaPorAlbaran=" + InvoiceByDeliveryNote +
                '}';
    }
}