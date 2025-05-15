package com.luis.facturacion.mvc_deliveryNoteList;

import com.luis.facturacion.mvc_deliveryNote.DeliveryNoteEntity;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

/**
 * Wrapper class for DeliveryNoteEntity with additional display properties.
 * This class extends DeliveryNoteEntity to provide additional fields
 * needed for the list view without modifying the original entity.
 */
public class DeliveryNoteListItem extends DeliveryNoteEntity {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private String clientName;
    private String totalAmount;

    /**
     * Constructor that wraps a DeliveryNoteEntity.
     *
     * @param entity The original DeliveryNoteEntity
     */
    public DeliveryNoteListItem(DeliveryNoteEntity entity) {
        // Copy all properties from the original entity
        this.setId(entity.getId());
        this.setIndex(entity.getIndex());
        this.setClientId(entity.getClientId());
        this.setDate(entity.getDate());
        this.setInvoiceNumber(entity.getInvoiceNumber());
    }

    /**
     * Returns the formatted date as a string.
     *
     * @return Formatted date string
     */
    public String getFormattedDate() {
        return getDate() != null ? getDate().format(DATE_FORMATTER) : "";
    }

    /**
     * Gets the client name.
     *
     * @return Client name
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * Sets the client name.
     *
     * @param clientName Client name
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    /**
     * Gets the total amount as a formatted string.
     *
     * @return Total amount
     */
    public String getTotalAmount() {
        return totalAmount;
    }

    /**
     * Sets the total amount.
     *
     * @param totalAmount Total amount as a string
     */
    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * Sets the total amount from a BigDecimal.
     *
     * @param amount Total amount as BigDecimal
     */
    public void setTotalAmount(BigDecimal amount) {
        this.totalAmount = amount != null ? amount.toString() : "0.00";
    }
}
