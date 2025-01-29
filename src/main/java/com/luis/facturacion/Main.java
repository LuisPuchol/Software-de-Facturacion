package com.luis.facturacion;

import com.luis.facturacion.controller.AppController;
import javafx.application.Application;
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
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        AppController appController = new AppController(primaryStage);
        appController.showLoginView(); // Mostrar la ventana de login inicialmente
    }

}
