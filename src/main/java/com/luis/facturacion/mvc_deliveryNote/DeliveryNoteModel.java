package com.luis.facturacion.mvc_deliveryNote;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class DeliveryNoteModel {
    private static DeliveryNoteModel instance;
    private DeliveryNoteController deliveryNoteController;

    //persistance
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
     * New deliveryNote with tha basic data
     */
    public void createNewDeliveryNote(String number, String clientId,
                                      LocalDate date, boolean printDeliveryNote, boolean createInvoice) {
        currentDeliveryNoteEntity = new DeliveryNoteEntity();
        currentDeliveryNoteEntity.setIndex(Integer.valueOf(number));
        currentDeliveryNoteEntity.setClientId(Integer.valueOf(clientId));
        currentDeliveryNoteEntity.setDate(date);


        //Do what i have to do with these. One Prints directly and the other creates the invoice directly
        //currentDeliveryNoteEntity.setPrintAlbaran(printDeliveryNote);
        //currentDeliveryNoteEntity.setCreateInvoice(createInvoice);

        // Limpiar items anteriores
        currentItems.clear();
    }

    /**
     * add item to the actual deliveryNote
     */
    public void addItemToDeliveryNote(DeliveryNoteItemEntity item) {
        currentItems.add(item);
    }

    /**
     * DeliveryNoteItem (UI) to DeliveryNoteItemEntity (DDBB)
     */
    public DeliveryNoteItemEntity convertToEntity(DeliveryNoteItem uiItem) {
        DeliveryNoteItemEntity entity = new DeliveryNoteItemEntity();

        //TODO deliveryNoteID value comes from the DDBB picks the higher, if its null, then set it to 1
        //entity.setDeliveryNoteID(uiItem.getDeliveryNoteID());
        entity.setArticleID(uiItem.getArticleID());
        entity.setTrace1(uiItem.getTrace1());
        entity.setTrace2(uiItem.getTrace2());
        entity.setQuantity(uiItem.getQuantity());
        entity.setPrice(uiItem.getPrice());
        return entity;
    }

    /**
     * Guarda el albarán y sus items usando threads y guarded blocks
     */
    public void saveDeliveryNoteWithItems() {
        final int threadCount = 2;
        CountDownLatch readyLatch = new CountDownLatch(threadCount);
        CountDownLatch startLatch = new CountDownLatch(1);

        // Thread save DeliveryNote
        Thread noteThread = new Thread(() -> {
            try {
                // Ready
                readyLatch.countDown();
                // W8 to the other
                startLatch.await();

                // Save
                deliveryNoteDAO.save(currentDeliveryNoteEntity);


            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Thread save items
        Thread itemsThread = new Thread(() -> {
            try {
                // Ready
                readyLatch.countDown();
                // W8 to the other
                startLatch.await();

                // Save
                currentItems.forEach(deliveryNoteItemDAO::save);


            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Start threads
        noteThread.start();
        itemsThread.start();

        try {
            // W8 al threads
            readyLatch.await();

            // Start persistence
            startLatch.countDown();

            // W8 until they finish
            noteThread.join();
            itemsThread.join();


        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Operación interrumpida", e);
        }
    }

    /**
     * Amount of albaran
     */
    public BigDecimal calculateTotal() {
        return currentItems.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
