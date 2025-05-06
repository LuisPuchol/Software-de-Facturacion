package com.luis.facturacion.mvc_client;

import com.luis.facturacion.mvc_client.database.ClientDAO;
import com.luis.facturacion.mvc_client.database.ClientEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class ClientModel {
    private static ClientModel instance;
    private ClientController clientController;
    private final ClientDAO clientDao;
    private final ObservableList<ClientEntity> clientList;

    private ClientModel() {
        System.out.println("Client Model created");
        this.clientDao = new ClientDAO();
        this.clientList = FXCollections.observableArrayList();
    }

    public static ClientModel getInstance() {
        if (instance == null) {
            instance = new ClientModel();
        }
        return instance;
    }

    public void setController(ClientController clientController) {
        if (this.clientController == null) {
            this.clientController = clientController;
        }
    }

    public void addCliente(Integer index, String name, String address, String postalCode, String city,
                           String province, String cif, String tel,
                           String tel2, Integer eqSurcharge, Integer ClientType, Integer InvoiceByDeliveryNote) {
        try {
            ClientEntity client = new ClientEntity();
            client.setIndex(index);
            client.setName(name);
            client.setAddress(address);
            client.setPostalCode(postalCode);
            client.setCity(city);
            client.setProvince(province);
            client.setCif(cif);
            client.setTel(tel);
            client.setTel2(tel2);
            client.setEquivalenceSurcharge(eqSurcharge);
            client.setClientType(ClientType);
            client.setInvoiceByDeliveryNote(InvoiceByDeliveryNote);

            clientDao.save(client);
            System.out.println("Client saved");
        } catch (Exception e) {
            System.err.println("Error while saving: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<ClientEntity> loadClientes() {
        return clientDao.getAll();
    }

    public String getClienteById(Integer id) {
        return clientDao.getNameByID(id);
    }
}

