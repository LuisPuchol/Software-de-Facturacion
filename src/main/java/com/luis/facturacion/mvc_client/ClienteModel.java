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

    //public void addCliente(String nombre, String direccion, String cp, String poblacion,
    //                       String provincia, String pais, String cif, String telefono,
    //                       String email, String iban, double riesgo, double descuento, String observaciones) {
    //    try {
    //        ClienteEntity cliente = new ClienteEntity();
    //        cliente.setNombreCliente(nombre);
    //        cliente.setDireccionCliente(direccion);
    //        cliente.setCpCliente(cp);
    //        cliente.setPoblacionCliente(poblacion);
    //        cliente.setProvinciaCliente(provincia);
    //        cliente.setPaisCliente(pais);
    //        cliente.setCifCliente(cif);
    //        cliente.setTelCliente(telefono);
    //        cliente.setEmailCliente(email);
    //        cliente.setIbanCliente(iban);
    //        cliente.setRiesgoCliente(riesgo);
    //        cliente.setDescuentoCliente(descuento);
    //        cliente.setObservacionesCliente(observaciones);
    //
    //        clienteDao.save(cliente);
    //        System.out.println("Cliente guardado correctamente");
    //    } catch (Exception e) {
    //        System.err.println("Error inesperado al agregar cliente: " + e.getMessage());
    //        e.printStackTrace();
    //    }
    //}
    //
    //public List<ClienteEntity> loadClientes() {
    //    return clienteDao.getAll();
    //}
}

