package com.luis.facturacion.mvc_tipoIva;

import com.luis.facturacion.AppController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TiposDeIvaView {
    private TipoDeIvaController tipoIvaController;
    private AppController appController;

    public TiposDeIvaView(AppController appController) {
        System.out.println("TipoDeIvaView created");
        this.appController = appController;
    }

    public void show(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/luis/facturacion/tipoIva.fxml"));
            Parent root = loader.load();

            tipoIvaController = loader.getController();
            tipoIvaController.setAppController(appController);

            Scene scene = new Scene(root, 1000, 600);
            primaryStage.setTitle("Gestión de Tipos de IVA");
            primaryStage.setScene(scene);
            primaryStage.show();

            tipoIvaController.setUpModel(tipoIvaController);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}