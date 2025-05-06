package com.luis.facturacion.mvc_invoice;

import com.luis.facturacion.AppController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;

public class InvoiceController {
    private InvoiceModel invoiceModel;
    private AppController appController;

    @FXML
    private DatePicker invoiceDateField, invoiceCollectionDateField;
    @FXML
    private TextField taxableBaseField, invoiceVATField,
            invoiceTotalField, invoiceHashField, invoiceIdField, invoiceNumberField, invoiceClientField, paymentMethodField;
    @FXML
    private TextArea invoiceQRField, invoiceObservationsField;
    @FXML
    private CheckBox invoicePaidField;
    @FXML
    private Button newButton, editButton, deleteButton, exitButton;

    public InvoiceController() {
        System.out.println("Invoice Controller created");
        this.invoiceModel = InvoiceModel.getInstance();
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
        invoiceModel.setController(this);
    }

    public void handleAddButton(MouseEvent mouseEvent) {
        int numeroFactura = Integer.parseInt(invoiceNumberField.getText()); // Número de factura como entero
        LocalDate fechaFactura = invoiceDateField.getValue(); // Fecha de factura como LocalDate
        int clienteFactura = Integer.parseInt(invoiceClientField.getText()); // Cliente como String
        double ivaFactura = Double.parseDouble(invoiceVATField.getText()); // IVA como Double
        String observaciones = invoiceObservationsField.getText(); // Observaciones como String

        // Call method with inverted values (?)
        invoiceModel.startInvoice(numeroFactura, fechaFactura, clienteFactura, ivaFactura, observaciones);

        //CreateFacturaView createFacturaView = new CreateFacturaView(appController);
        //createFacturaView.show(facturasView.getPrimaryStage());
    }

    public void handleExitButton(MouseEvent mouseEvent) {
        exitButton.getScene().getWindow().hide();
    }

    public String getClientByID(Integer id) {
        return appController.getClienteByID(id);
    }

    public String getProductByID(Integer id) {
        return appController.getProductByID(id);
    }
}
