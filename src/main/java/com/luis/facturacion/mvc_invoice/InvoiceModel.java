package com.luis.facturacion.mvc_invoice;

import com.luis.facturacion.mvc_invoice.database.InvoiceDAO;
import com.luis.facturacion.mvc_invoice.database.InvoiceEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InvoiceModel {
    private static InvoiceModel instance;
    private InvoiceController invoiceController;
    private final InvoiceDAO invoiceDAO;
    private final ObservableList<InvoiceEntity> facturasList;

    // Data actual invoice
    private int invoiceNumber;
    private LocalDate invoiceDate;
    private String invoiceClient;
    private double invoiceVAT;
    private String invoiceObservations;

    // save lines from actual invoice
    private final List<Object[]> invoiceLines;

    public InvoiceModel() {
        System.out.println("Invoice Model Created");
        this.invoiceDAO = new InvoiceDAO();
        this.facturasList = FXCollections.observableArrayList();
        this.invoiceLines = new ArrayList<>();
    }

    public static InvoiceModel getInstance() {
        if (instance == null) {
            instance = new InvoiceModel();
        }
        return instance;
    }

    public void setController(InvoiceController invoiceController){
        if (this.invoiceController == null){
            this.invoiceController = invoiceController;
        }
    }

    public void startInvoice(int invoiceNumber, LocalDate invoiceDate, Integer clientID, double invoiceVAT, String invoiceObservations) {
        this.invoiceNumber = invoiceNumber;
        this.invoiceDate = invoiceDate;
        this.invoiceClient = invoiceController.getClientByID(clientID);
        this.invoiceVAT = invoiceVAT;
        this.invoiceObservations = invoiceObservations;

        this.invoiceLines.clear();
    }

    public void addLine(Integer id, Integer quantity, Double price) {
        String productName = invoiceController.getProductByID(id);

        // calculation
        double cost = quantity * price;

        // Create line add to array
        Object[] linea = new Object[] {id, productName, quantity, price, cost};
        invoiceLines.add(linea);
    }


    public void finishFactura() {
        // calculation without VAT
        double totalWithoutVAT = 0;
        for (Object[] line : invoiceLines) {
            totalWithoutVAT += (double) line[4]; // import on position 4
        }

        double importeIVA = totalWithoutVAT * (invoiceVAT / 100);

        double totalConIVA = totalWithoutVAT + importeIVA;

        // Crete entity
        //InvoiceEntity invoiceEntity = new InvoiceEntity();
        //invoiceEntity.setNumeroFactura(numeroFactura);
        //invoiceEntity.setFechaFactura(fechaFactura);
        //invoiceEntity.setClienteFactura(clienteFactura);
        //invoiceEntity.setIvaFactura(ivaFactura);
        //invoiceEntity.setObservaciones(observaciones);
        //invoiceEntity.setTotalSinIVA(totalWithoutVAT);
        //invoiceEntity.setTotalIVA(importeIVA);
        //invoiceEntity.setTotalConIVA(totalConIVA);

        // save on DDBB
        //facturaDAO.guardarFactura(facturaEntity);
    }

    /**
     * Create a PDF
     * @return path of the PDF
     */
    public String generatePDF() {
        // Crete directory if didnt exist
        String dirPath = "facturas";
        File directory = new File(dirPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // File Name
        String fileName = String.format("factura_%d.pdf", invoiceNumber);
        String filePath = dirPath + File.separator + fileName;

        // Create PDF
        InvoicePDFGenerator.generateFacturaPDF(
                invoiceNumber,
                invoiceDate,
                invoiceClient,
                invoiceLines,
                invoiceVAT,
                invoiceObservations,
                filePath
        );

        return filePath;
    }

    // Getters
    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public String getInvoiceClient() {
        return invoiceClient;
    }

    public double getInvoiceVAT() {
        return invoiceVAT;
    }

    public String getInvoiceObservations() {
        return invoiceObservations;
    }

    public List<Object[]> getInvoiceLines() {
        return invoiceLines;
    }
}