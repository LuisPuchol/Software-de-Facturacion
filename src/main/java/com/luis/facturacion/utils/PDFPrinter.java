package com.luis.facturacion.utils;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

/**
 * Utility class for printing PDF documents
 */
public class PDFPrinter {

    /**
     * Prints a PDF file to the default printer
     *
     * @param pdfFile File object of the PDF to print
     * @throws IOException if there is an error reading the PDF
     * @throws PrinterException if there is an error printing the document
     */
    public static void printPDF(File pdfFile) throws IOException, javax.print.PrintException, java.awt.print.PrinterException {
        printPDF(pdfFile, null);
    }

    /**
     * Prints a PDF file to a specified printer
     *
     * @param pdfFile File object of the PDF to print
     * @param printerName Name of the printer to use (null for default printer)
     * @throws IOException if there is an error reading the PDF
     * @throws PrinterException if there is an error printing the document
     */
    public static void printPDF(File pdfFile, String printerName) throws IOException, javax.print.PrintException, java.awt.print.PrinterException {
        // Load the PDF file
        PDDocument document = PDDocument.load(pdfFile);

        try {
            // Create print job
            PrinterJob job = PrinterJob.getPrinterJob();

            // Set the printer if specified
            if (printerName != null && !printerName.isEmpty()) {
                PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);

                for (PrintService service : printServices) {
                    if (service.getName().equalsIgnoreCase(printerName)) {
                        job.setPrintService(service);
                        break;
                    }
                }
            }

            // Set print attributes
            PrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();
            attributes.add(new Copies(1));

            // Set the document as pageable
            job.setPageable(new PDFPageable(document));

            // Print the document
            job.print(attributes);

        } finally {
            if (document != null) {
                document.close();
            }
        }
    }

    /**
     * Shows the print dialog and prints the PDF if the user confirms
     *
     * @param pdfFile File object of the PDF to print
     * @return true if the document was printed, false if the user canceled
     * @throws IOException if there is an error reading the PDF
     * @throws PrinterException if there is an error printing the document
     */
    public static boolean showPrintDialog(File pdfFile) throws IOException, java.awt.print.PrinterException {
        // Load the PDF file
        PDDocument document = PDDocument.load(pdfFile);

        try {
            // Create print job
            PrinterJob job = PrinterJob.getPrinterJob();

            // Set the document as pageable
            job.setPageable(new PDFPageable(document));

            // Show print dialog
            if (job.printDialog()) {
                // Print the document
                job.print();
                return true;
            } else {
                return false; // User canceled
            }
        } finally {
            if (document != null) {
                document.close();
            }
        }
    }
}