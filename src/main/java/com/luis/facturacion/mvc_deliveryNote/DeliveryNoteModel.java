package com.luis.facturacion.mvc_deliveryNote;

import javafx.scene.control.TextField;

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
    public void createNewDeliveryNote(String number, String clientId, LocalDate date, String totalAmount,
                                      boolean printDeliveryNote, boolean createInvoice) {
        currentDeliveryNoteEntity = new DeliveryNoteEntity(
                Integer.valueOf(number),
                Integer.valueOf(clientId),
                date,
                Double.valueOf(totalAmount)
        );

        //TODO what i have to do with these. One Prints directly and the other creates the invoice directly
        //printDeliveryNote - > print DeliveryNotePDF
        //Create a invoice Object and save it to DDBB - >createInvoice

        currentItems.clear();
    }

    /**
     * Adds item to the current delivery note
     */
    public void convertAndAddItemToDeliveryNote(DeliveryNoteItem item) {
        DeliveryNoteItemEntity itemEntity = convertToEntity(item);
        currentItems.add(itemEntity);
    }

    /**
     * Converts DeliveryNoteItem (UI) to DeliveryNoteItemEntity (DB)
     */
    public DeliveryNoteItemEntity convertToEntity(DeliveryNoteItem uiItem) {
        return new DeliveryNoteItemEntity(
                uiItem.getDeliveryNoteID(),
                uiItem.getArticleID(),
                uiItem.getTrace1(),
                uiItem.getTrace2(),
                uiItem.getQuantity(),
                uiItem.getPrice()
        );
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
    }

    public void setDeliveryNoteNumber(TextField deliveryNoteNumberField) {
        String nextNumber = String.valueOf(DeliveryNoteDAO.getInstance().getNextDeliveryNoteNumber());
        deliveryNoteNumberField.setText(nextNumber);
    }

}
