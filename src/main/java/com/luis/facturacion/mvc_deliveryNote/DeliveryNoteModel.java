package com.luis.facturacion.mvc_deliveryNote;

public class DeliveryNoteModel {
    private static DeliveryNoteModel instance;
    private DeliveryNoteController deliveryNoteController;
    //private final AlbaranDAO albaranDAO;
    //private final ObservableList<AlbaranEntity> albaranList;

    private DeliveryNoteModel() {
        System.out.println("Albaran Model created");
        //this.clienteDao = new ClienteDAO();
        //this.clienteList = FXCollections.observableArrayList();
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
}
