package com.luis.facturacion.mvc_login;

import com.luis.facturacion.AppController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginView {
    private final AppController appController;

    public LoginView(AppController appController) {
        System.out.println("Loginview created");
        this.appController = appController;
    }

    public void show(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/luis/facturacion/login-menu.fxml"));
            Parent root = loader.load();

            // Obtener el controlador y configurarlo
            LoginController loginController = loader.getController();
            loginController.setAppController(appController);

            // Configurar la escena y el escenario
            Scene scene = new Scene(root);
            stage.setTitle("Login");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

