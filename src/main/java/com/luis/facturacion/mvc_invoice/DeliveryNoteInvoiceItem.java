package com.luis.facturacion.mvc_invoice;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Represents a delivery note item for a specific client to be included in an invoice.
 * This class is used in the table showing delivery notes details.
 */
public class DeliveryNoteInvoiceItem {
    private final StringProperty formattedDate;
    private final StringProperty deliveryNoteNumber;
    private final StringProperty amount;

    public DeliveryNoteInvoiceItem(String date, String deliveryNoteNumber, String amount) {
        this.formattedDate = new SimpleStringProperty(date);
        this.deliveryNoteNumber = new SimpleStringProperty(deliveryNoteNumber);
        this.amount = new SimpleStringProperty(amount);
    }

    // Getters and property accessors
    public String getFormattedDate() {
        return formattedDate.get();
    }

    public StringProperty formattedDateProperty() {
        return formattedDate;
    }

    public String getDeliveryNoteNumber() {
        return deliveryNoteNumber.get();
    }

    public StringProperty deliveryNoteNumberProperty() {
        return deliveryNoteNumber;
    }

    public String getAmount() {
        return amount.get();
    }

    public StringProperty amountProperty() {
        return amount;
    }
}