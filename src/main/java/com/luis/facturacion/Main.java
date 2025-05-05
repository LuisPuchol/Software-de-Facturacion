package com.luis.facturacion;

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
        AppController appController = new AppController(primaryStage);
        appController.showLoginView();
    }

}
