package com.luis.facturacion.utils.pdf;

import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import javax.print.PrintException;
import javax.print.PrintServiceLookup;
import java.util.Arrays;
import java.util.Optional;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

/**
 * Utility class for printing PDF documents
 */
public class PDFPrinter {
    /**
     * Shows the print dialog and prints the PDF if the user confirms.
     * Returns true if printed, false if cancelled.
     */
    public static boolean showPrintDialog(File pdfFile) throws IOException, java.awt.print.PrinterException {
        try (PDDocument document = PDDocument.load(pdfFile)) {
            PrinterJob job = createPrinterJob(document, null);
            return job.printDialog() && executePrint(job);
        } catch (PrintException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates and configures a PrinterJob for the given document and printer.
     * Sets printer service if printerName is specified.
     */
    private static PrinterJob createPrinterJob(PDDocument document, String printerName) throws javax.print.PrintException {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPageable(new PDFPageable(document));

        Optional.ofNullable(printerName)
                .filter(name -> !name.isEmpty())
                .ifPresent(name -> setPrinterService(job, name));

        return job;
    }

    /**
     * Sets the printer service for the job based on printer name.
     * Searches available print services for a matching name.
     */
    private static void setPrinterService(PrinterJob job, String printerName) {
        Arrays.stream(PrintServiceLookup.lookupPrintServices(null, null))
                .filter(service -> service.getName().equalsIgnoreCase(printerName))
                .findFirst()
                .ifPresent(service -> {
                    try {
                        job.setPrintService(service);
                    } catch (java.awt.print.PrinterException e) {
                        System.err.println("Error setting printer service: " + e.getMessage());
                    }
                });
    }

    /**
     * Executes the print job safely.
     * Returns true if successful, false otherwise.
     */
    private static boolean executePrint(PrinterJob job) {
        try {
            job.print();
            return true;
        } catch (java.awt.print.PrinterException e) {
            System.err.println("Error executing print job: " + e.getMessage());
            return false;
        }
    }
}