package com.luis.facturacion;

import com.luis.facturacion.utils.HibernateUtil;
import com.luis.facturacion.utils.ShowAlert;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;


/**
 * @author Luis
 *
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // Debug to check Mysql
            if (!HibernateUtil.isMySQLAvailable()) {
                ShowAlert.showError("MySQL Error",
                        "No se puede conectar a MySQL.\n\n" +
                                "Verifica que:\n" +
                                "1. MySQL esté instalado\n" +
                                "2. El servicio MySQL esté ejecutándose\n" +
                                "3. Las credenciales (root/1234) sean correctas");
                Platform.exit();
                return;
            }

            // Start DDBB
            HibernateUtil.initializeDatabase();

            // Continue
            AppController appController = new AppController(primaryStage);
            appController.showLoginView();

        } catch (Exception e) {
            e.printStackTrace();
            ShowAlert.showError("Initialization Error",
                    "Error al inicializar la aplicación: " + e.getMessage());
            Platform.exit();
        }
    }

    @Override
    public void stop() {
        HibernateUtil.shutdown();
    }
}
