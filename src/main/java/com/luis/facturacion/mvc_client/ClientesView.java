package com.luis.facturacion.mvc_client;

import com.luis.facturacion.AppController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientesView {
    private ClienteController clienteController;
    private AppController appController;

    public ClientesView(AppController appController) {
        System.out.println("ClienteView created");
        this.appController = appController;
    }

    public void show(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/luis/facturacion/cliente.fxml"));
            Parent root = loader.load();

            clienteController = loader.getController();
            clienteController.setAppController(appController);

            Scene scene = new Scene(root, 1000, 600);
            primaryStage.setTitle("Gestión de Clientes");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
