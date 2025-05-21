package com.luis.facturacion.mvc_invoice;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Represents a client with delivery notes to be invoiced.
 * This class is used in the table showing clients pending for invoicing.
 */
public class ClientInvoiceItem {
    private final StringProperty clientId;
    private final StringProperty clientName;
    private final StringProperty deliveryNoteCount;
    private final StringProperty totalAmount;

    private int countValue;
    private double amountValue;

    public ClientInvoiceItem(String clientId, String clientName, String deliveryNoteCount, String totalAmount) {
        this.clientId = new SimpleStringProperty(clientId);
        this.clientName = new SimpleStringProperty(clientName);
        this.deliveryNoteCount = new SimpleStringProperty(deliveryNoteCount);
        this.totalAmount = new SimpleStringProperty(totalAmount);

        this.countValue = Integer.parseInt(deliveryNoteCount);
        this.amountValue = Double.parseDouble(totalAmount.replace(",", "."));
    }

    public void incrementDeliveryNoteCount() {
        this.countValue++;
        this.deliveryNoteCount.set(String.valueOf(this.countValue));
    }

    public void addToTotalAmount(Double amount) {
        if (amount != null) {
            this.amountValue += amount;
            this.totalAmount.set(String.format("%.2f", this.amountValue));
        }
    }

    public String getClientId() {
        return clientId.get();
    }

    public StringProperty clientIdProperty() {
        return clientId;
    }

    public String getClientName() {
        return clientName.get();
    }

    public StringProperty clientNameProperty() {
        return clientName;
    }

    public String getDeliveryNoteCount() {
        return deliveryNoteCount.get();
    }

    public StringProperty deliveryNoteCountProperty() {
        return deliveryNoteCount;
    }

    public String getTotalAmount() {
        return totalAmount.get();
    }

    public StringProperty totalAmountProperty() {
        return totalAmount;
    }
}