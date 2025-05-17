package com.luis.facturacion.utils;

import com.luis.facturacion.mvc_deliveryNote.DeliveryNoteEntity;
import com.luis.facturacion.mvc_invoice.database.InvoiceEntity;
import com.luis.facturacion.utils.PDFGenerator;
import com.luis.facturacion.utils.PDFPrinter;
import javafx.scene.control.Alert;

import java.io.File;
import java.util.List;

/**
 * Utility class to demonstrate how to generate and print invoice PDFs
 */
public class PDFManager {

    /**
     * Generates a PDF for an invoice
     *
     * @param invoice The invoice entity
     * @param deliveryNotes List of delivery notes included in the invoice
     * @return PDF file object or null if there was an error
     */
    public static File generateInvoicePDF(InvoiceEntity invoice, List<DeliveryNoteEntity> deliveryNotes) {
        try {
            System.out.println("Generating PDF for invoice #" + invoice.getId() + " with " + deliveryNotes.size() + " delivery notes");

            // Generate the PDF file
            File pdfFile = PDFGenerator.generateInvoicePDF(invoice, deliveryNotes);

            return pdfFile;
        } catch (Exception e) {
            System.err.println("Error generating invoice PDF: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Generates and shows a PDF for an invoice
     *
     * @param invoice The invoice entity
     * @param deliveryNotes List of delivery notes included in the invoice
     * @return true if the PDF was generated and shown, false otherwise
     */
    public static boolean showInvoicePDF(InvoiceEntity invoice, List<DeliveryNoteEntity> deliveryNotes) {
        File pdfFile = generateInvoicePDF(invoice, deliveryNotes);

        if (pdfFile != null) {
            // You could launch a PDF viewer here
            System.out.println("Invoice PDF generated: " + pdfFile.getAbsolutePath());

            // Example: Open with default system viewer (Java 9+)
            try {
                java.awt.Desktop.getDesktop().open(pdfFile);
                return true;
            } catch (Exception e) {
                System.err.println("Error opening PDF: " + e.getMessage());
                showErrorAlert("Error", "No se pudo abrir el PDF: " + e.getMessage());
                return false;
            }
        }

        return false;
    }

    /**
     * Generates and prints a PDF for an invoice
     *
     * @param invoice The invoice entity
     * @param deliveryNotes List of delivery notes included in the invoice
     * @return true if the PDF was generated and printed, false otherwise
     */
    public static boolean printInvoicePDF(InvoiceEntity invoice, List<DeliveryNoteEntity> deliveryNotes) {
        File pdfFile = generateInvoicePDF(invoice, deliveryNotes);

        if (pdfFile != null) {
            try {
                // Show print dialog and print
                boolean printed = PDFPrinter.showPrintDialog(pdfFile);

                // Delete the temporary file after printing
                if (!pdfFile.delete()) {
                    pdfFile.deleteOnExit();
                }

                if (printed) {
                    showInfoAlert("Impresión", "La factura se ha enviado a la impresora correctamente.");
                }

                return printed;
            } catch (Exception e) {
                System.err.println("Error printing invoice: " + e.getMessage());
                e.printStackTrace();
                showErrorAlert("Error de impresión", "No se ha podido imprimir la factura: " + e.getMessage());
                return false;
            }
        }

        return false;
    }

    /**
     * Shows an information alert
     */
    private static void showInfoAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Shows an error alert
     */
    private static void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
