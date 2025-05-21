package com.luis.facturacion;

import com.luis.facturacion.utils.DebugHelper;
import javafx.application.Platform;

/**
 * Enhanced DiagnosticMain with DebugHelper
 */
public class DiagnosticMain {

    public static void main(String[] args) {
        try {
            // Start logging through our helper
            DebugHelper.log("DiagnosticMain started");

            // Check if JavaFX is available
            try {
                Class.forName("javafx.application.Platform");
                DebugHelper.log("JavaFX Platform class found");
                Platform.startup(() -> {}); // Try to initialize JavaFX
                DebugHelper.log("JavaFX Platform initialized successfully");
            } catch (ClassNotFoundException e) {
                DebugHelper.log("JavaFX Platform class NOT found!", e);
            } catch (Exception e) {
                DebugHelper.log("Failed to initialize JavaFX Platform", e);
            }

            // Check for FXML resources
            checkResource("/com/luis/facturacion/mvc_login/login.fxml");
            // Add checks for other important FXML files

            // Now try to start the real application
            DebugHelper.log("Starting Main application...");
            Main.main(args);

        } catch (Throwable t) {
            DebugHelper.log("FATAL ERROR in DiagnosticMain", t);
            System.exit(1);
        }
    }

    private static void checkResource(String path) {
        if (DiagnosticMain.class.getResource(path) != null) {
            DebugHelper.log("Resource found: " + path);
        } else {
            DebugHelper.log("Resource NOT found: " + path);
        }
    }
}