package com.luis.facturacion.utils;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.luis.facturacion.mvc_article.database.ArticleDAO;
import com.luis.facturacion.mvc_client.database.ClientDAO;
import com.luis.facturacion.mvc_client.database.ClientEntity;
import com.luis.facturacion.mvc_deliveryNote.DeliveryNoteEntity;
import com.luis.facturacion.mvc_deliveryNote.DeliveryNoteItemEntity;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Utility class for generating PDF documents
 */
public class PDFGenerator {

    private static final String COMPANY_NAME = "Mi Empresa, S.L.";
    private static final String COMPANY_NIF = "B12345678";
    private static final String COMPANY_ADDRESS = "Calle Principal 123, 07001 Palma";

    /**
     * Generates a PDF for a delivery note
     *
     * @param deliveryNote Delivery note entity
     * @param items List of delivery note items
     * @return File object of the generated PDF
     * @throws IOException if there is an error generating the PDF
     */
    public static File generateDeliveryNotePDF(DeliveryNoteEntity deliveryNote, List<DeliveryNoteItemEntity> items) throws IOException {
        // Create temporary file
        File tempFile = File.createTempFile("albaran_" + deliveryNote.getIndex() + "_", ".pdf");

        // Initialize PDF writer and document
        PdfWriter writer = new PdfWriter(tempFile);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        try {
            // Add header with company and client information
            addHeader(document, deliveryNote);

            // Add items table
            addItemsTable(document, items);

            // Add total amount
            addTotalAmount(document, deliveryNote.getTotalAmount());

            // Add footer (optional)
            addFooter(document);

            return tempFile;
        } finally {
            document.close();
        }
    }

    /**
     * Adds header section with company and client information
     */
    private static void addHeader(Document document, DeliveryNoteEntity deliveryNote) {
        // Create table for layout (2 columns)
        Table headerTable = new Table(UnitValue.createPercentArray(new float[]{50, 50}));
        headerTable.setWidth(UnitValue.createPercentValue(100));

        // Company information (left column)
        Cell companyCell = new Cell();
        companyCell.setBorder(Border.NO_BORDER);

        companyCell.add(new Paragraph(COMPANY_NAME).setBold());
        companyCell.add(new Paragraph("NIF: " + COMPANY_NIF));
        companyCell.add(new Paragraph(COMPANY_ADDRESS));

        // Client information (right column)
        Cell clientCell = new Cell();
        clientCell.setBorder(new SolidBorder(ColorConstants.BLACK, 1));

        // Get client information
        ClientEntity client = ClientDAO.getInstance().getById(deliveryNote.getClientId());
        String clientName = (client != null) ? client.getName() : "Cliente ID: " + deliveryNote.getClientId();
        String clientAddress = (client != null && client.getAddress() != null) ? client.getAddress() : "";
        String clientNif = (client != null && client.getCif() != null) ? client.getCif() : "";

        // Delivery note information
        String dateFormatted = deliveryNote.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        // Add delivery note number and date
        Table infoTable = new Table(UnitValue.createPercentArray(new float[]{50, 50}));
        infoTable.setWidth(UnitValue.createPercentValue(100));

        Cell albaranCell = new Cell();
        albaranCell.setBorder(Border.NO_BORDER);
        albaranCell.add(new Paragraph("Albarán nº: " + deliveryNote.getIndex()).setBold());

        Cell dateCell = new Cell();
        dateCell.setBorder(Border.NO_BORDER);
        dateCell.setTextAlignment(TextAlignment.RIGHT);
        dateCell.add(new Paragraph("Fecha: " + dateFormatted).setBold());

        infoTable.addCell(albaranCell);
        infoTable.addCell(dateCell);

        clientCell.add(infoTable);

        // Add client information
        clientCell.add(new Paragraph("Cliente: " + clientName).setMarginTop(10));
        clientCell.add(new Paragraph("Dirección: " + clientAddress));
        clientCell.add(new Paragraph("CIF/NIF: " + clientNif));

        headerTable.addCell(companyCell);
        headerTable.addCell(clientCell);

        document.add(headerTable);
        document.add(new Paragraph(" ").setMarginBottom(20)); // Add some spacing
    }

    /**
     * Adds the table with delivery note items
     */
    private static void addItemsTable(Document document, List<DeliveryNoteItemEntity> items) {
        // Create table with 7 columns
        Table itemsTable = new Table(UnitValue.createPercentArray(new float[]{10, 30, 10, 10, 10, 15, 15}));
        itemsTable.setWidth(UnitValue.createPercentValue(100));

        // Add header row
        String[] headers = {"Código", "Concepto", "Trazabilidad 1", "Trazabilidad 2", "Cantidad", "Precio", "Importe"};
        for (String header : headers) {
            Cell cell = new Cell();
            cell.add(new Paragraph(header).setBold());
            cell.setBackgroundColor(ColorConstants.LIGHT_GRAY);
            itemsTable.addHeaderCell(cell);
        }

        // Add item rows
        for (DeliveryNoteItemEntity item : items) {
            // Debug information
            System.out.println("Processing item with ArticleID: " + item.getArticleID());

            // Get article name (concept)
            Integer articleID = item.getArticleID();
            String articleName = articleID != null ? ArticleDAO.getInstance().getNameById(articleID) : "";
            System.out.println("Article name: " + articleName);

            // Calculate amount (quantity * price)
            BigDecimal amount = item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));

            // Add cells to the table
            itemsTable.addCell(new Cell().add(new Paragraph(articleID != null ? articleID.toString() : "")));
            itemsTable.addCell(new Cell().add(new Paragraph(articleName != null ? articleName : "")));
            itemsTable.addCell(new Cell().add(new Paragraph(item.getTrace1() != null ? item.getTrace1() : "")));
            itemsTable.addCell(new Cell().add(new Paragraph(item.getTrace2() != null ? item.getTrace2() : "")));
            itemsTable.addCell(new Cell().add(new Paragraph(String.valueOf(item.getQuantity()))));
            itemsTable.addCell(new Cell().add(new Paragraph(item.getPrice().toString() + " €")));
            itemsTable.addCell(new Cell().add(new Paragraph(amount.toString() + " €")));
        }

        document.add(itemsTable);
    }

    /**
     * Adds the total amount section
     */
    private static void addTotalAmount(Document document, Double totalAmount) {
        // Create total table (right-aligned)
        Table totalTable = new Table(UnitValue.createPercentArray(new float[]{70, 30}));
        totalTable.setWidth(UnitValue.createPercentValue(100));
        totalTable.setMarginTop(20);

        // Empty cell
        Cell emptyCell = new Cell();
        emptyCell.setBorder(Border.NO_BORDER);
        totalTable.addCell(emptyCell);

        // Total amount cell
        Cell totalCell = new Cell();
        totalCell.setBorder(new SolidBorder(ColorConstants.BLACK, 1));
        totalCell.add(new Paragraph("TOTAL: " + String.format("%.2f €", totalAmount)).setBold());
        totalCell.setTextAlignment(TextAlignment.RIGHT);
        totalTable.addCell(totalCell);

        document.add(totalTable);
    }

    /**
     * Adds a footer to the document
     */
    private static void addFooter(Document document) {
        document.add(new Paragraph(" ").setMarginBottom(20)); // Add some spacing

        Paragraph footerText = new Paragraph("Gracias por su confianza");
        footerText.setTextAlignment(TextAlignment.CENTER);
        footerText.setFontSize(10);
        document.add(footerText);
    }
}