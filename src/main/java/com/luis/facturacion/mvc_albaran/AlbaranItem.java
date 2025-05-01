package com.luis.facturacion.mvc_albaran;


import javafx.beans.property.*;

/**
 * Clase que representa un ítem en un albarán
 */
public class AlbaranItem {
    private final StringProperty code;
    private final StringProperty concept;
    private final StringProperty trace1;
    private final StringProperty trace2;
    private final IntegerProperty quantity;
    private final DoubleProperty price;
    private final DoubleProperty amount;

    /**
     * Constructor principal
     */
    public AlbaranItem(String code, String concept, String trace1, String trace2, int quantity, double price) {
        this.code = new SimpleStringProperty(code);
        this.concept = new SimpleStringProperty(concept);
        this.trace1 = new SimpleStringProperty(trace1);
        this.trace2 = new SimpleStringProperty(trace2);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.price = new SimpleDoubleProperty(price);
        this.amount = new SimpleDoubleProperty(calculateAmount());
    }

    /**
     * Calcula el importe (cantidad * precio)
     */
    private double calculateAmount() {
        return getQuantity() * getPrice();
    }

    // Getters y setters
    public String getCode() {
        return code.get();
    }

    public StringProperty codeProperty() {
        return code;
    }

    public void setCode(String code) {
        this.code.set(code);
    }

    public String getConcept() {
        return concept.get();
    }

    public StringProperty conceptProperty() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept.set(concept);
    }

    public String getTrace1() {
        return trace1.get();
    }

    public StringProperty trace1Property() {
        return trace1;
    }

    public void setTrace1(String trace1) {
        this.trace1.set(trace1);
    }

    public String getTrace2() {
        return trace2.get();
    }

    public StringProperty trace2Property() {
        return trace2;
    }

    public void setTrace2(String trace2) {
        this.trace2.set(trace2);
    }

    public int getQuantity() {
        return quantity.get();
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
        this.amount.set(calculateAmount());
    }

    public double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
        this.amount.set(calculateAmount());
    }

    public double getAmount() {
        return amount.get();
    }

    public DoubleProperty amountProperty() {
        return amount;
    }
}
