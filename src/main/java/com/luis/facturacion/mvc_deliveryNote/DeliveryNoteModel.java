package com.luis.facturacion.mvc_deliveryNote;

import com.luis.facturacion.utils.PDFGenerator;
import com.luis.facturacion.utils.PDFPrinter;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class DeliveryNoteModel {
    private static DeliveryNoteModel instance;
    private DeliveryNoteController deliveryNoteController;

    private DeliveryNoteEntity currentDeliveryNoteEntity;
    private List<DeliveryNoteItemEntity> currentItems = new ArrayList<>();

    //DDBB
    private DeliveryNoteDAO deliveryNoteDAO;
    private DeliveryNoteItemDAO deliveryNoteItemDAO;

    private DeliveryNoteModel() {
        System.out.println("Delivery-Note Model created");
        this.deliveryNoteDAO = DeliveryNoteDAO.getInstance();
        this.deliveryNoteItemDAO = DeliveryNoteItemDAO.getInstance();
    }

    public static DeliveryNoteModel getInstance() {
        if (instance == null) {
            instance = new DeliveryNoteModel();
        }
        return instance;
    }

    public void setController(DeliveryNoteController deliveryNoteController) {
        if (this.deliveryNoteController == null) {
            this.deliveryNoteController = deliveryNoteController;
        }
    }

    /**
     * Creates new delivery note with basic data
     */
    public void createNewDeliveryNote(String DeliveryNoteNumber, String clientId, LocalDate date, String totalAmount,
                                      boolean printDeliveryNote, boolean createInvoice) {
        currentDeliveryNoteEntity = new DeliveryNoteEntity(
                Integer.valueOf(DeliveryNoteNumber),
                Integer.valueOf(clientId),
                date,
                Double.valueOf(totalAmount)
        );

        // Establecer deliveryNoteID en todos los elementos
        for (DeliveryNoteItemEntity item : currentItems) {
            item.setDeliveryNoteID(currentDeliveryNoteEntity.getIndex());
        }

        if (printDeliveryNote) {
            printDeliveryNote();
        }

        if (createInvoice) {
            // TODO: Create invoice object and save it to DDBB
        }
    }

    /**
     * Generates and prints a PDF for the current delivery note
     */
    private void printDeliveryNote() {
        try {
            // Log how many items we're sending to the PDF generator
            System.out.println("Printing delivery note with " + currentItems.size() + " items");
            for (DeliveryNoteItemEntity item : currentItems) {
                System.out.println("Item in print: ArticleID=" + item.getArticleID() +
                        ", Trace1=" + item.getTrace1() +
                        ", Quantity=" + item.getQuantity());
            }

            // Generate the PDF file
            File pdfFile = PDFGenerator.generateDeliveryNotePDF(currentDeliveryNoteEntity, currentItems);

            // Print the PDF file
            boolean printed = PDFPrinter.showPrintDialog(pdfFile);

            // Delete the temporary file after printing
            if (!pdfFile.delete()) {
                pdfFile.deleteOnExit();
            }

            // Show confirmation message if printed successfully
            if (printed) {
                showInfoAlert("Impresión", "El albarán se ha enviado a la impresora correctamente.");
            }
        } catch (Exception e) {
            System.err.println("Error al imprimir el albarán: " + e.getMessage());
            e.printStackTrace();
            showErrorAlert("Error de impresión", "No se ha podido imprimir el albarán: " + e.getMessage());
        }
    }

    /**
     * Adds item to the current delivery note
     */
    public void convertAndAddItemToDeliveryNote(DeliveryNoteItem item) {
        DeliveryNoteItemEntity itemEntity = convertToEntity(item);
        // Print debug information
        System.out.println("Adding item to delivery note: " + item.getCode() + " - " + item.getConcept());
        System.out.println("Converted entity: ArticleID=" + itemEntity.getArticleID() + ", Trace1=" + itemEntity.getTrace1());
        currentItems.add(itemEntity);
    }

    /**
     * Converts DeliveryNoteItem (UI) to DeliveryNoteItemEntity (DB)
     */
    public DeliveryNoteItemEntity convertToEntity(DeliveryNoteItem uiItem) {
        // Debug information
        System.out.println("Converting UI item: " + uiItem.getCode() + " - " + uiItem.getConcept());

        // Convert String code to Integer
        Integer articleID = null;
        try {
            articleID = Integer.parseInt(uiItem.getCode());
            System.out.println("Parsed articleID: " + articleID);
        } catch (NumberFormatException e) {
            System.err.println("Error parsing article code: " + e.getMessage());
        }

        DeliveryNoteItemEntity entity = new DeliveryNoteItemEntity(
                null, // Se asignará al guardar
                articleID,
                uiItem.getTrace1(),
                uiItem.getTrace2(),
                uiItem.getQuantity(),
                uiItem.getPrice()
        );

        // Establecer el ID del albarán si está disponible
        if (currentDeliveryNoteEntity != null && currentDeliveryNoteEntity.getId() != null) {
            entity.setDeliveryNoteID(currentDeliveryNoteEntity.getId());
        } else if (currentDeliveryNoteEntity != null) {
            entity.setDeliveryNoteID(currentDeliveryNoteEntity.getIndex());
        }

        return entity;
    }

    /**
     * Saves the delivery note and its items using threads and guarded blocks
     */
    public void saveDeliveryNoteWithItems() {
        final int threadCount = 2;
        CountDownLatch readyLatch = new CountDownLatch(threadCount);
        CountDownLatch startLatch = new CountDownLatch(1);

        Thread noteThread = new Thread(() -> {
            try {
                readyLatch.countDown();
                startLatch.await();

                deliveryNoteDAO.save(currentDeliveryNoteEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Thread itemsThread = new Thread(() -> {
            try {
                readyLatch.countDown();
                startLatch.await();

                currentItems.forEach(deliveryNoteItemDAO::save);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        noteThread.start();
        itemsThread.start();

        try {
            readyLatch.await();
            startLatch.countDown();

            noteThread.join();
            itemsThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Operation interrupted", e);
        }

        currentItems.clear();
    }

    public void setDeliveryNoteNumber(TextField deliveryNoteNumberField) {
        String nextNumber = String.valueOf(DeliveryNoteDAO.getInstance().getNextDeliveryNoteNumber());
        deliveryNoteNumberField.setText(nextNumber);
    }

    /**
     * Shows an information alert
     */
    private void showInfoAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Shows an error alert
     */
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}