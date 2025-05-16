package com.luis.facturacion.mvc_invoiceList;

import com.luis.facturacion.mvc_invoice.database.InvoiceEntity;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Wrapper class for InvoiceEntity with additional display properties
 * needed for the list view without modifying the original entity.
 */
public class InvoiceListItem extends InvoiceEntity {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final StringProperty formattedDate = new SimpleStringProperty();
    private final StringProperty clientName = new SimpleStringProperty();
    private final StringProperty invoiceNumber = new SimpleStringProperty();
    private final StringProperty clientCode = new SimpleStringProperty();
    private final StringProperty baseAmount = new SimpleStringProperty();
    private final StringProperty vatAmount = new SimpleStringProperty();
    private final StringProperty totalAmount = new SimpleStringProperty();

    /**
     * Constructor that wraps an InvoiceEntity.
     *
     * @param entity The original InvoiceEntity
     */
    public InvoiceListItem(InvoiceEntity entity) {
        this.setId(entity.getId());
        this.setClientId(entity.getClientId());
        this.setDate(entity.getDate());
        this.setType(entity.getType());
        this.setTotalAmount(entity.getTotalAmount());

        updateFormattedDate();
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
     * Updates display properties based on entity properties.
     */
    private void updateDisplayProperties() {
        this.invoiceNumber.set(getId() != null ? getId().toString() : "");
        this.clientCode.set(getClientId() != null ? getClientId().toString() : "");
        this.totalAmount.set(getTotalAmount() != null ? String.format("%.2f", getTotalAmount()) : "0.00");
    }

    @Override
    public void setDate(LocalDate date) {
        super.setDate(date);
        updateFormattedDate();
    }

    @Override
    public void setId(Integer id) {
        super.setId(id);
        updateDisplayProperties();
    }

    @Override
    public void setClientId(Integer clientId) {
        super.setClientId(clientId);
        updateDisplayProperties();
    }

    @Override
    public void setTotalAmount(Double totalAmount) {
        super.setTotalAmount(totalAmount);
        updateDisplayProperties();
    }

    // Getters & Setters for display properties
    public String getFormattedDate() {
        return formattedDate.get();
    }

    public StringProperty formattedDateProperty() {
        return formattedDate;
    }

    public String getClientName() {
        return clientName.get();
    }

    public void setClientName(String name) {
        clientName.set(name);
    }

    public StringProperty clientNameProperty() {
        return clientName;
    }

    public String getInvoiceNumber() {
        return invoiceNumber.get();
    }

    public StringProperty invoiceNumberProperty() {
        return invoiceNumber;
    }

    public String getClientCode() {
        return clientCode.get();
    }

    public StringProperty clientCodeProperty() {
        return clientCode;
    }

    public String getBaseAmount() {
        return baseAmount.get();
    }

    public void setBaseAmount(String amount) {
        baseAmount.set(amount);
    }

    public StringProperty baseAmountProperty() {
        return baseAmount;
    }

    public String getVatAmount() {
        return vatAmount.get();
    }

    public void setVatAmount(String amount) {
        vatAmount.set(amount);
    }

    public StringProperty vatAmountProperty() {
        return vatAmount;
    }

    public String getTotalAmountFormatted() {
        return totalAmount.get();
    }

    public StringProperty totalAmountProperty() {
        return totalAmount;
    }
}