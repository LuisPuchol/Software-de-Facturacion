package com.luis.facturacion.mvc_client;

import com.luis.facturacion.mvc_client.database.ClienteDAO;
import com.luis.facturacion.mvc_client.database.ClienteEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class ClienteModel {
    private static ClienteModel instance;
    private ClienteController clienteController;
    private final ClienteDAO clienteDao;
    private final ObservableList<ClienteEntity> clienteList;

    private ClienteModel() {
        System.out.println("Cliente Model created");
        this.clienteDao = new ClienteDAO();
        this.clienteList = FXCollections.observableArrayList();
    }

    public static ClienteModel getInstance() {
        if (instance == null) {
            instance = new ClienteModel();
        }
        return instance;
    }

    public void setController(ClienteController clienteController) {
        if (this.clienteController == null) {
            this.clienteController = clienteController;
        }
    }

    public void addCliente(Integer ind, String nombre, String direccion, String cp, String poblacion,
                           String provincia, String cif, String telefono,
                           String tel2, Integer reqEquivalencia, Integer tipoCliente, Integer facturaPorAlbaran) {
        try {
            ClienteEntity cliente = new ClienteEntity();
            cliente.setInd(ind);
            cliente.setNombre(nombre);
            cliente.setDireccion(direccion);
            cliente.setCodPostal(cp);
            cliente.setPoblacion(poblacion);
            cliente.setProvincia(provincia);
            cliente.setCif(cif);
            cliente.setTel(telefono);
            cliente.setTel2(tel2);
            cliente.setReqEquivalencia(reqEquivalencia);
            cliente.setTipoCliente(tipoCliente);
            cliente.setFacturaPorAlbaran(facturaPorAlbaran);

            clienteDao.save(cliente);
            System.out.println("Cliente guardado correctamente");
        } catch (Exception e) {
            System.err.println("Error inesperado al agregar cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<ClienteEntity> loadClientes() {
        return clienteDao.getAll();
    }

    public String getClienteById(Integer id) {
        return clienteDao.getNameByID(id);
    }
}

