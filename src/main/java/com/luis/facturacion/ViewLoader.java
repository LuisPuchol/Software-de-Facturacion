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
     * Carga una vista FXML en una nueva ventana.
     *
     * @param <T> Tipo del controlador
     * @param fxmlPath Ruta al archivo FXML
     * @param controllerClass Clase del controlador
     * @param controller Instancia del controlador
     * @param title Título para la nueva ventana
     * @return La instancia del controlador
     */
    public <T> T loadViewInNewStage(String fxmlPath, Class<T> controllerClass, T controller, String title) {
        Stage stage = new Stage();
        return loadView(fxmlPath, controllerClass, controller, stage, title);
    }


}
