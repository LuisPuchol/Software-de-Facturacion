package com.luis.facturacion.mvc_deliveryNote;


import java.math.BigDecimal;
import javafx.beans.property.*;

/**
 * Class that represents an item on a delivery note
 */
public class DeliveryNoteItem {
    // Campos para la tabla en JavaFX
    private final StringProperty code;
    private final StringProperty concept;
    private final StringProperty trace1;
    private final StringProperty trace2;
    private final IntegerProperty quantity;
    private final ObjectProperty<BigDecimal> price;
    private final ObjectProperty<BigDecimal> amount;

    private Long id;
    private Long albaranId;
    private Long articuloId;

    /**
     * Main constructor
     */
    public DeliveryNoteItem(String code, String concept, String trace1, String trace2, int quantity, BigDecimal price) {
        this.code = new SimpleStringProperty(code);
        this.concept = new SimpleStringProperty(concept);
        this.trace1 = new SimpleStringProperty(trace1);
        this.trace2 = new SimpleStringProperty(trace2);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.price = new SimpleObjectProperty<>(price);

        // Calcular el monto (precio * cantidad)
        BigDecimal calculatedAmount = price.multiply(new BigDecimal(quantity));
        this.amount = new SimpleObjectProperty<>(calculatedAmount);
    }

    // Getters y setters para code
    public String getCode() { return code.get(); }
    public void setCode(String value) { code.set(value); }
    public StringProperty codeProperty() { return code; }

    // Getters y setters para concept
    public String getConcept() { return concept.get(); }
    public void setConcept(String value) { concept.set(value); }
    public StringProperty conceptProperty() { return concept; }

    // Getters y setters para trace1
    public String getTrace1() { return trace1.get(); }
    public void setTrace1(String value) { trace1.set(value); }
    public StringProperty trace1Property() { return trace1; }

    // Getters y setters para trace2
    public String getTrace2() { return trace2.get(); }
    public void setTrace2(String value) { trace2.set(value); }
    public StringProperty trace2Property() { return trace2; }

    // Getters y setters para quantity
    public int getQuantity() { return quantity.get(); }
    public void setQuantity(int value) {
        quantity.set(value);
        updateAmount();
    }
    public IntegerProperty quantityProperty() { return quantity; }

    // Getters y setters para price
    public BigDecimal getPrice() { return price.get(); }
    public void setPrice(BigDecimal value) {
        price.set(value);
        updateAmount();
    }
    public ObjectProperty<BigDecimal> priceProperty() { return price; }

    // Getters y setters para amount
    public BigDecimal getAmount() { return amount.get(); }
    public ObjectProperty<BigDecimal> amountProperty() { return amount; }

    // Método privado para actualizar el monto cuando cambia el precio o la cantidad
    private void updateAmount() {
        BigDecimal newAmount = getPrice().multiply(new BigDecimal(getQuantity()));
        amount.set(newAmount);
    }

    // Getters y setters para los campos de la BBDD (que se usarán más tarde)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getAlbaranId() { return albaranId; }
    public void setAlbaranId(Long albaranId) { this.albaranId = albaranId; }

    public Long getArticuloId() { return articuloId; }
    public void setArticuloId(Long articuloId) { this.articuloId = articuloId; }

    // Método para preparar el objeto para guardarlo en la BBDD
    public DeliveryNoteItemEntity toEntity() {
        // Esto es un ejemplo, tendrías que implementar tu propia lógica de conversión
        DeliveryNoteItemEntity entity = new DeliveryNoteItemEntity();
        entity.setId(id);
        entity.setAlbaranId(albaranId);
        entity.setArticuloId(articuloId); // Este valor tendría que venir de algún lado
        entity.setTrazabilidad1(getTrace1());
        entity.setTrazabilidad2(getTrace2());
        entity.setCantidad(getQuantity());
        entity.setPrecio(getPrice());
        return entity;
    }


}