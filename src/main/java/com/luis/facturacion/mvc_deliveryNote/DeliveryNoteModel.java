package com.luis.facturacion.mvc_deliveryNote;

import com.luis.facturacion.mvc_deliveryNote.database.DeliveryNoteDAO;
import com.luis.facturacion.mvc_deliveryNote.database.DeliveryNoteEntity;
import com.luis.facturacion.mvc_deliveryNote.database.DeliveryNoteItemDAO;
import com.luis.facturacion.mvc_deliveryNote.database.DeliveryNoteItemEntity;
import com.luis.facturacion.utils.pdf.PDFGenerator;
import com.luis.facturacion.utils.pdf.PDFPrinter;
import com.luis.facturacion.utils.ShowAlert;
import javafx.scene.control.TextField;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class DeliveryNoteModel {
    private static DeliveryNoteModel instance;
    private DeliveryNoteController deliveryNoteController;

    private DeliveryNoteEntity currentDeliveryNoteEntity;
    private List<DeliveryNoteItemEntity> currentItems = new ArrayList<>();

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

        if (createInvoice) {
            // TODO: Create invoice object and save it to DDBB
        }
    }

    /**
     * Generates and prints a PDF for the current delivery note
     */
    void printDeliveryNote() {
        try {
            // Log how many items we're sending to the PDF generator
            System.out.println("Printing delivery note with " + currentItems.size() + " items");
            for (DeliveryNoteItemEntity item : currentItems) {
                System.out.println("Item in print: ArticleID=" + item.getArticleID() +
                        ", Trace1=" + item.getTrace1() +
                        ", Quantity=" + item.getQuantity());
            }

            // Generate the PDF file // no longer adds , currentItems as parameter
            File pdfFile = PDFGenerator.generateDeliveryNotePDF(currentDeliveryNoteEntity);

            // Print the PDF file
            boolean printed = PDFPrinter.showPrintDialog(pdfFile);

            // Delete the temporary file after printing
            if (!pdfFile.delete()) {
                pdfFile.deleteOnExit();
            }

            // Show confirmation message if printed successfully
            if (printed) {
                ShowAlert.showInfo("Impresión", "El albarán se ha enviado a la impresora correctamente.");
            }
        } catch (Exception e) {
            System.err.println("Error al imprimir el albarán: " + e.getMessage());
            e.printStackTrace();
            ShowAlert.showError("Error de impresión", "No se ha podido imprimir el albarán: " + e.getMessage());
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
            entity.setDeliveryNoteID(currentDeliveryNoteEntity.getIndex());
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
     * Creates an invoice for the current delivery note's client.
     * Uses the AppController to delegate to the InvoiceController.
     */
    public void createInvoice() {
        if (currentDeliveryNoteEntity == null) {
            ShowAlert.showError("Error", "No hay albarán creado para facturar.");
            return;
        }

        try {
            Integer clientId = currentDeliveryNoteEntity.getClientId();
            Integer invoiceId = deliveryNoteController.getAppController().createInvoiceForClient(clientId);

            if (invoiceId != null) {
                ShowAlert.showInfo("Éxito", "Factura creada correctamente con ID: " + invoiceId);
            } else {
                ShowAlert.showError("Error", "No se pudo crear la factura.");
            }

        } catch (Exception e) {
            System.err.println("Error creating invoice from DeliveryNote: " + e.getMessage());
            ShowAlert.showError("Error", "Error al crear la factura: " + e.getMessage());
        }
    }
}