package com.luis.facturacion.utils;

import javafx.scene.control.Alert;

public class ShowAlert {
    /**
     * Displays an information alert with the given title and message.
     *
     * @param title   The title of the alert.
     * @param message The message to display in the alert.
     */
    public static void showInfo(String title, String message) {
        showAlert(Alert.AlertType.INFORMATION, title, message);
    }

    /**
     * Displays a warning alert with the given title and message.
     *
     * @param title   The title of the alert.
     * @param message The message to display in the alert.
     */
    public static void showWarning(String title, String message) {
        showAlert(Alert.AlertType.WARNING, title, message);
    }

    /**
     * Displays an error alert with the given title and message.
     *
     * @param title   The title of the alert.
     * @param message The message to display in the alert.
     */
    public static void showError(String title, String message) {
        showAlert(Alert.AlertType.ERROR, title, message);
    }

    /**
     * Displays a JavaFX alert with the specified type, title, and message.
     *
     * @param type    The type of the alert (e.g., INFORMATION, WARNING, ERROR).
     * @param title   The title of the alert.
     * @param message The message to display in the alert.
     */
    private static void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
