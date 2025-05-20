package com.luis.facturacion.mvc_deliveryNote;


import java.math.BigDecimal;
import java.math.RoundingMode;

import javafx.beans.property.*;

/**
 * Class that represents an item on a delivery note
 */

public class DeliveryNoteItem {
    private final StringProperty code;
    private final StringProperty concept;
    private final StringProperty trace1;
    private final StringProperty trace2;
    private final DoubleProperty quantity;
    private final ObjectProperty<BigDecimal> price;
    private final ObjectProperty<BigDecimal> amount;

    private Integer deliveryNoteID;
    private Integer articleID;

    /**
     * Main constructor
     */
    public DeliveryNoteItem(String code, String concept, String trace1, String trace2, double quantity, BigDecimal price) {
        this.code = new SimpleStringProperty(code);
        this.concept = new SimpleStringProperty(concept);
        this.trace1 = new SimpleStringProperty(trace1);
        this.trace2 = new SimpleStringProperty(trace2);
        this.quantity = new SimpleDoubleProperty(quantity);
        this.price = new SimpleObjectProperty<>(price);

        BigDecimal calculatedAmount = price.multiply(new BigDecimal(quantity))
                .setScale(2, RoundingMode.CEILING);
        this.amount = new SimpleObjectProperty<>(calculatedAmount);
    }

    public String getCode() {
        return code.get();
    }

    public void setCode(String value) {
        code.set(value);
    }

    public StringProperty codeProperty() {
        return code;
    }

    public String getConcept() {
        return concept.get();
    }

    public void setConcept(String value) {
        concept.set(value);
    }

    public StringProperty conceptProperty() {
        return concept;
    }

    public String getTrace1() {
        return trace1.get();
    }

    public void setTrace1(String value) {
        trace1.set(value);
    }

    public StringProperty trace1Property() {
        return trace1;
    }

    public String getTrace2() {
        return trace2.get();
    }

    public void setTrace2(String value) {
        trace2.set(value);
    }

    public StringProperty trace2Property() {
        return trace2;
    }

    public Double getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int value) {
        quantity.set(value);
        updateAmount();
    }

    public DoubleProperty quantityProperty() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price.get();
    }

    public void setPrice(BigDecimal value) {
        price.set(value);
        updateAmount();
    }

    public ObjectProperty<BigDecimal> priceProperty() {
        return price;
    }

    public BigDecimal getAmount() {
        return amount.get();
    }

    public ObjectProperty<BigDecimal> amountProperty() {
        return amount;
    }

    private void updateAmount() {
        BigDecimal newAmount = getPrice().multiply(new BigDecimal(getQuantity()));
        amount.set(newAmount);
    }

    public Integer getDeliveryNoteID() {
        return deliveryNoteID;
    }

    public void setDeliveryNoteID(Integer deliveryNoteID) {
        this.deliveryNoteID = deliveryNoteID;
    }

    public Integer getArticleID() {
        return articleID;
    }

    public void setArticleID(Integer articleID) {
        this.articleID = articleID;
    }

}