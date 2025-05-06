package com.luis.facturacion;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewLoader {
    public <T> T loadView(String fxmlPath, Class<T> controllerClass, T controller, Stage stage, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));

            loader.setControllerFactory(clazz -> {
                if (clazz == controllerClass) {
                    return controller;
                }
                try {
                    return clazz.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();

            return controller;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while loading view: " + fxmlPath, e);
        }
    }

    /**
     * Loads an FXML view in a new window.
     *
     * @param <T> Controller type
     * @param fxmlPath Path to the FXML file
     * @param controllerClass Controller class
     * @param controller Controller instance
     * @param title Title for the new window
     * @return The controller instance
     */
    public <T> T loadViewInNewStage(String fxmlPath, Class<T> controllerClass, T controller, String title) {
        Stage stage = new Stage();
        return loadView(fxmlPath, controllerClass, controller, stage, title);
    }


}
