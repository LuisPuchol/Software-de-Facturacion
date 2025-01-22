package com.luis.facturacion;

import com.luis.facturacion.controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * @author Luis
 *
 * Main se encargara de:
 *
 *
 * Iniciar la logica completa con la llamada al new LoginController
 * Iniciar la primera interfaz, en este caso el Login
 * Cargar los datos de configuración de diferentes usuarios
 *
 * Conexion base de datos??? posiblemente clase aparte
 */
public class Main extends Application {

    public static void main(String[] args) {
        new LoginController();
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("login-menu.fxml"));
        Scene scene = new Scene(root);

        stage.getIcons().add(new Image("/logo.png"));
        stage.setTitle("Frutas");
        stage.setScene(scene);
        stage.show();
    }


}
