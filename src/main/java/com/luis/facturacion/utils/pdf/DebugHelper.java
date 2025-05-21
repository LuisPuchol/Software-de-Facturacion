package com.luis.facturacion.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Debug helper class for diagnosing application issues
 * Add this to your project in the utils package
 */
public class DebugHelper {

    private static String logFilePath;
    private static FileWriter fileWriter;
    private static PrintWriter printWriter;

    static {
        try {
            // Create log file in user's home directory
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            logFilePath = Paths.get(System.getProperty("user.home"), "facturacion_debug_" + timeStamp + ".log").toString();

            // Create the file and writers
            fileWriter = new FileWriter(logFilePath, true);
            printWriter = new PrintWriter(fileWriter, true);

            // Log start message
            log("=== Debug Log Started ===");
            log("Time: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            log("Log file: " + logFilePath);

            // Add shutdown hook to close resources
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    log("=== Application Shutting Down ===");
                    if (printWriter != null) printWriter.close();
                    if (fileWriter != null) fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));

            // Log some basic system information
            logSystemInfo();

        } catch (IOException e) {
            System.err.println("Failed to create debug log file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Log a message to the debug file
     */
    public static void log(String message) {
        if (printWriter != null) {
            printWriter.println(message);
            printWriter.flush();
        }

        // Also print to console if possible
        System.out.println("[DEBUG] " + message);
    }

    /**
     * Log an exception with stack trace
     */
    public static void log(String message, Throwable t) {
        log(message);
        if (printWriter != null) {
            t.printStackTrace(printWriter);
            printWriter.flush();
        }
        t.printStackTrace(System.err);
    }

    /**
     * Log basic system information
     */
    private static void logSystemInfo() {
        log("=== System Information ===");
        log("OS: " + System.getProperty("os.name") + " " + System.getProperty("os.version"));
        log("Java version: " + System.getProperty("java.version"));
        log("JavaFX version: " + System.getProperty("javafx.version", "Unknown"));
        log("User home: " + System.getProperty("user.home"));
        log("Working directory: " + System.getProperty("user.dir"));
        log("Temp directory: " + System.getProperty("java.io.tmpdir"));

        // Check for database drivers
        log("\n=== Database Drivers ===");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            log("MySQL JDBC driver found.");
        } catch (ClassNotFoundException e) {
            log("MySQL JDBC driver NOT found!");
        }

        // Check for important directories
        log("\n=== Directory Check ===");
        checkDirectory(System.getProperty("user.dir"));
        checkDirectory(Paths.get(System.getProperty("user.dir"), "app").toString());
        checkDirectory(Paths.get(System.getProperty("user.dir"), "runtime").toString());
    }

    /**
     * Check and log contents of a directory
     */
    private static void checkDirectory(String dirPath) {
        File dir = new File(dirPath);
        if (!dir.exists()) {
            log("Directory does not exist: " + dirPath);
            return;
        }

        log("Directory: " + dirPath);
        File[] files = dir.listFiles();
        if (files == null) {
            log("  Cannot list files in directory");
            return;
        }

        for (File file : files) {
            log("  " + file.getName() + (file.isDirectory() ? " [DIR]" : " [FILE]"));
        }
    }
}