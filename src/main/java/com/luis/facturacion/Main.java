package com.luis.facturacion;

import com.luis.facturacion.utils.HibernateUtil;
import javafx.application.Application;
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
        HibernateUtil.initializeDatabase();
        AppController appController = new AppController(primaryStage);
        appController.showLoginView();
    }

}
