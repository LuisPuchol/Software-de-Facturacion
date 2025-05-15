package com.luis.facturacion.mvc_deliveryNoteList;

import com.luis.facturacion.mvc_deliveryNote.DeliveryNoteEntity;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Wrapper class for DeliveryNoteEntity with additional display properties
 * needed for the list view without modifying the original entity.
 */
public class DeliveryNoteListItem extends DeliveryNoteEntity {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final StringProperty formattedDate = new SimpleStringProperty();
    private final StringProperty clientName = new SimpleStringProperty();
    private final StringProperty totalAmount = new SimpleStringProperty();
    private final StringProperty displayInvoiceNumber = new SimpleStringProperty();
    private final StringProperty displayIndex = new SimpleStringProperty();
    private final StringProperty displayClientId = new SimpleStringProperty();

    /**
     * Constructor that wraps a DeliveryNoteEntity.
     *
     * @param entity The original DeliveryNoteEntity
     */
    public DeliveryNoteListItem(DeliveryNoteEntity entity) {
        this.setId(entity.getId());
        this.setIndex(entity.getIndex());
        this.setClientId(entity.getClientId());
        this.setDate(entity.getDate());
        this.setTotalAmount(entity.getTotalAmount());
        this.setInvoiceNumber(entity.getInvoiceNumber());

        updateFormattedDate();
        updateDisplayInvoiceNumber();
        updateDisplayProperties();
    }

    /**
     * Updates the formatted date property based on the entity date.
     */
    private void updateFormattedDate() {
        if (getDate() != null) {
            formattedDate.set(getDate().format(DATE_FORMATTER));
        } else {
            formattedDate.set("");
        }
    }

    /**
     * Updates the display invoice number property based on the entity invoice number.
     */
    private void updateDisplayInvoiceNumber() {
        if (getInvoiceNumber() != null) {
            displayInvoiceNumber.set(getInvoiceNumber().toString());
        } else {
            displayInvoiceNumber.set("");
        }
    }

    public void updateDisplayProperties() {
        this.displayIndex.set(getIndex() != null ? getIndex().toString() : "");
        this.displayClientId.set(getClientId() != null ? getClientId().toString() : "");
    }

    @Override
    public void setDate(LocalDate date) {
        super.setDate(date);
        updateFormattedDate();
    }

    @Override
    public void setInvoiceNumber(Integer invoiceNumber) {
        super.setInvoiceNumber(invoiceNumber);
        updateDisplayInvoiceNumber();
    }

    @Override
    public void setIndex(Integer index) {
        super.setIndex(index);
        updateDisplayProperties();
    }

    @Override
    public void setClientId(Integer clientId) {
        super.setClientId(clientId);
        updateDisplayProperties();
    }

    // Getters & Setters
    public StringProperty formattedDateProperty() {
        return formattedDate;
    }

    public String getFormattedDate() {
        return formattedDate.get();
    }

    public StringProperty clientNameProperty() {
        return clientName;
    }

    public String getClientName() {
        return clientName.get();
    }

    public void setClientName(String name) {
        clientName.set(name);
    }

    public StringProperty totalAmountProperty() {
        return totalAmount;
    }

    public Double getTotalAmount() {
        return Double.valueOf(totalAmount.get());
    }

    public void setTotalAmount(Double amount) {
        totalAmount.set(String.valueOf(amount));
    }

    public void setTotalAmount(BigDecimal amount) {
        this.totalAmount.set(amount != null ? amount.toString() : "0.00");
    }

    public StringProperty displayInvoiceNumberProperty() {
        return displayInvoiceNumber;
    }

    public String getDisplayInvoiceNumber() {
        return displayInvoiceNumber.get();
    }

    public StringProperty displayIndexProperty() {
        return displayIndex;
    }

    public String getDisplayIndex() {
        return displayIndex.get();
    }

    public StringProperty displayClientIdProperty() {
        return displayClientId;
    }

    public String getDisplayClientId() {
        return displayClientId.get();
    }
}