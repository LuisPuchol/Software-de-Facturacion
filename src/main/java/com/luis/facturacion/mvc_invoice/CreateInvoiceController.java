package com.luis.facturacion.mvc_invoice;

import com.luis.facturacion.AppController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;
import java.util.Optional;

public class CreateInvoiceController {
    private InvoiceModel invoiceModel;
    private AppController appController;

    @FXML
    private TextField IndField, QuantityField, priceField;
    @FXML
    private Label nameLabel, importLabel;
    @FXML
    private Button acceptButton, cancelButton;


    public CreateInvoiceController() {
        System.out.println("Create-Invoice created");
        this.invoiceModel = InvoiceModel.getInstance();
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    @FXML
    public void handleAccept(ActionEvent actionEvent) {
        int index = Integer.parseInt(IndField.getText()); // Si el código es numérico
        int quantity = Integer.parseInt(QuantityField.getText());
        double price = Double.parseDouble(priceField.getText());

        invoiceModel.addLine(index, quantity, price);
    }

    @FXML
    public void handleCancel(ActionEvent actionEvent) {
        //clean
    }
    
    @FXML
    private void handleFinish(ActionEvent actionEvent) {
        try {
            // finish and save
            invoiceModel.finishFactura();

            // PDF
            String pdfPath = invoiceModel.generatePDF();

            // good ending
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Factura Creada");
            alert.setHeaderText("Factura creada correctamente");
            alert.setContentText("Se ha generado un PDF en: " + pdfPath);

            // add buton to open the PDF
            ButtonType openPdfButton = new ButtonType("Abrir PDF");
            alert.getButtonTypes().add(openPdfButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == openPdfButton) {
                try {
                    File pdfFile = new File(pdfPath);
                    if (pdfFile.exists()) {
                        java.awt.Desktop.getDesktop().open(pdfFile);
                    }
                } catch (Exception e) {
                    System.err.println("Error al abrir el PDF: " + e.getMessage());
                }
            }

            // finish and close tab maybe

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al crear la factura");
            alert.setContentText("Se ha producido un error: " + e.getMessage());
            alert.showAndWait();
        }
    }
}
