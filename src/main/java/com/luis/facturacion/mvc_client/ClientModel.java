package com.luis.facturacion.mvc_client;

import com.luis.facturacion.mvc_client.database.ClientDAO;
import com.luis.facturacion.mvc_client.database.ClientEntity;
import com.luis.facturacion.utils.ShowAlert;
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

    public boolean addCliente(Integer index, String name, String address, String postalCode, String city,
                           String province, String cif, String tel,
                           String tel2, Integer eqSurcharge, Integer ClientType, Integer InvoiceByDeliveryNote) {

        if (!validateRequiredFields(index, name, address, cif)) {
            return false;
        }

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
            ShowAlert.showInfo("Éxito", "Cliente guardado correctamente.");
            return true;

        } catch (Exception e) {
            System.err.println("Error while saving: " + e.getMessage());
            ShowAlert.showError("Error", "Error al guardar el cliente: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Validates required fields and shows alerts for any errors.
     *
     * @param index   Client index
     * @param name    Client name
     * @param address Client address
     * @param cif     Client CIF
     * @return true if all validations pass, false otherwise
     */
    private boolean validateRequiredFields(Integer index, String name, String address, String cif) {
        if (index == null || index <= 0) {
            ShowAlert.showError("Campo Requerido", "El campo 'Índice' es obligatorio y debe ser mayor que 0.");
            return false;
        }

        if (name == null || name.trim().isEmpty()) {
            ShowAlert.showError("Campo Requerido", "El campo 'Nombre' es obligatorio.");
            return false;
        }

        if (address == null || address.trim().isEmpty()) {
            ShowAlert.showError("Campo Requerido", "El campo 'Dirección' es obligatorio.");
            return false;
        }

        if (cif == null || cif.trim().isEmpty()) {
            ShowAlert.showError("Campo Requerido", "El campo 'CIF' es obligatorio.");
            return false;
        }

        return true;
    }

    public List<ClientEntity> loadClientes() {
        return clientDao.getAll();
    }

    public String getClienteById(Integer id) {
        return clientDao.getNameById(id);
    }
}