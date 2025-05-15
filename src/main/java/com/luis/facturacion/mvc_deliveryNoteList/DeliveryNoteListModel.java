package com.luis.facturacion.mvc_deliveryNoteList;

import com.luis.facturacion.mvc_deliveryNote.DeliveryNoteDAO;
import com.luis.facturacion.mvc_deliveryNote.DeliveryNoteEntity;

import java.time.LocalDate;
import java.util.List;

/**
 * Model for the Delivery Note List view.
 * Handles business logic and data access for listing delivery notes.
 */
public class DeliveryNoteListModel {
    private static DeliveryNoteListModel instance;
    private DeliveryNoteListController controller;

    // Database access objects
    private DeliveryNoteDAO deliveryNoteDAO;

    /**
     * Private constructor for Singleton pattern.
     */
    private DeliveryNoteListModel() {
        System.out.println("Delivery-Note List Model created");
        this.deliveryNoteDAO = DeliveryNoteDAO.getInstance();
    }

    /**
     * Gets the singleton instance of DeliveryNoteListModel.
     */
    public static DeliveryNoteListModel getInstance() {
        if (instance == null) {
            instance = new DeliveryNoteListModel();
        }
        return instance;
    }

    /**
     * Sets the controller reference.
     */
    public void setController(DeliveryNoteListController controller) {
        if (this.controller == null) {
            this.controller = controller;
        }
    }

    /**
     * Retrieves delivery notes from the database within the specified date range.
     * Optionally includes only those with associated invoices.
     *
     * @param fromDate Start date for the search
     * @param toDate End date for the search
     * @param includeInvoices Whether to include only notes with associated invoices
     * @return List of DeliveryNoteEntity objects
     */
    public List<DeliveryNoteEntity> getDeliveryNotesByDateRange(
            LocalDate fromDate, LocalDate toDate, boolean includeInvoices) {

        // Delegate to the DAO to retrieve delivery notes
        List<DeliveryNoteEntity> deliveryNotes = deliveryNoteDAO.getDeliveryNotesByDateRange(fromDate, toDate);

        // If we only want notes with invoices, filter the list
        if (includeInvoices) {
            deliveryNotes.removeIf(note -> note.getInvoiceNumber() == null || note.getInvoiceNumber().isEmpty());
        }

        // Calculate and set the total amount for each delivery note
        deliveryNotes.forEach(this::calculateTotalAmount);

        return deliveryNotes;
    }

    /**
     * Calculates the total amount for a delivery note by summing its items.
     * This would typically involve fetching the items from the database.
     *
     * @param deliveryNote The delivery note to calculate total for
     */
    private void calculateTotalAmount(DeliveryNoteEntity deliveryNote) {
        // In a real implementation, this would fetch the items from the database
        // and calculate the total amount

        // For now, we'll assume the total is already set or use a placeholder
        if (deliveryNote.getTotalAmount() == null) {
            // If total amount is not set, we could calculate it from items
            // deliveryNote.setTotalAmount(deliveryNoteDAO.calculateTotalAmount(deliveryNote.getNumber()));

            // For demo purposes, set a placeholder if not already set
            deliveryNote.setTotalAmount("0.00");
        }
    }
}