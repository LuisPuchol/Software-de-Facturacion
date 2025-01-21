package com.luis.facturacion;

import com.luis.facturacion.view.MainView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
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


    // Método para cambiar a MainView después del login
    public void showMainMenu(Stage stage) {
        MainView mainView = new MainView();
        mainView.startMainMenu(stage);
    }



}
