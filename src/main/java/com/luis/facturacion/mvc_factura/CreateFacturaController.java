package com.luis.facturacion.mvc_factura;

import com.luis.facturacion.AppController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;
import java.util.Optional;

public class CreateFacturaController {
    private FacturaModel facturaModel;
    private AppController appController;
    private FacturasView facturasView;
    @FXML
    private TextField txtCodigo, txtCantidad, txtPrecio;


    @FXML
    private Label lblNombre, txtImporte;

    @FXML
    private Button btnAceptar, btnCancelar;


    public CreateFacturaController() {
        System.out.println("Factura created");
        this.facturaModel = FacturaModel.getInstance();
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    public void setView(FacturasView facturasView){
        this.facturasView = facturasView;
    }

    public void handleAceptar(ActionEvent actionEvent) {
        int codigo = Integer.parseInt(txtCodigo.getText()); // Si el código es numérico
        int cantidad = Integer.parseInt(txtCantidad.getText());
        double precio = Double.parseDouble(txtPrecio.getText());

        facturaModel.addLine(codigo, cantidad, precio);
    }

    public void handleCancelar(ActionEvent actionEvent) {
        //Limpia los campos
    }

    // Ejemplo de método en FacturaController que genera el PDF al finalizar la factura
    @FXML
    private void handleFinalizar(ActionEvent actionEvent) {
        try {
            // Finalizar la factura (guardar en BD)
            facturaModel.finishFactura();

            // Generar el PDF
            String pdfPath = facturaModel.generatePDF();

            // Mostrar mensaje de éxito
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Factura Creada");
            alert.setHeaderText("Factura creada correctamente");
            alert.setContentText("Se ha generado un PDF en: " + pdfPath);

            // Añadir botón para abrir el PDF
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

            // Cerrar la ventana de factura o volver a la pantalla principal
            // Aquí deberías añadir tu código para cerrar la ventana o navegar

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al crear la factura");
            alert.setContentText("Se ha producido un error: " + e.getMessage());
            alert.showAndWait();
        }
    }
}
