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
import com.luis.facturacion.mvc_invoice.database.InvoiceEntity;
import com.luis.facturacion.mvc_vatConfig.database.VATConfigDAO;
import com.luis.facturacion.mvc_vatConfig.database.VATConfigEntity;

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
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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
     * Generates a PDF for an invoice that includes multiple delivery notes
     *
     * @param invoice Invoice entity
     * @param deliveryNotes List of delivery notes included in this invoice
     * @return File object of the generated PDF
     * @throws IOException if there is an error generating the PDF
     */
    public static File generateInvoicePDF(InvoiceEntity invoice, List<DeliveryNoteEntity> deliveryNotes) throws IOException {
        // Create temporary file
        File tempFile = File.createTempFile("factura_" + invoice.getId() + "_", ".pdf");

        // Initialize PDF writer and document
        PdfWriter writer = new PdfWriter(tempFile);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        try {
            // Add header with company and client information
            addInvoiceHeader(document, invoice);

            // Add delivery notes and their items
            addDeliveryNotesSection(document, deliveryNotes);

            // Add totals section with VAT
            addInvoiceTotalsSection(document, invoice);

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
        String dateFormatted = deliveryNote.getDate().format(DATE_FORMATTER);

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
     * Adds header section with company and client information for an invoice
     */
    private static void addInvoiceHeader(Document document, InvoiceEntity invoice) {
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
        ClientEntity client = ClientDAO.getInstance().getById(invoice.getClientId());
        String clientName = (client != null) ? client.getName() : "Cliente ID: " + invoice.getClientId();
        String clientAddress = (client != null && client.getAddress() != null) ? client.getAddress() : "";
        String clientNif = (client != null && client.getCif() != null) ? client.getCif() : "";

        // Invoice information
        String dateFormatted = invoice.getDate().format(DATE_FORMATTER);

        // Add invoice number and date
        Table infoTable = new Table(UnitValue.createPercentArray(new float[]{50, 50}));
        infoTable.setWidth(UnitValue.createPercentValue(100));

        Cell invoiceCell = new Cell();
        invoiceCell.setBorder(Border.NO_BORDER);
        invoiceCell.add(new Paragraph("Factura nº: " + invoice.getId()).setBold());

        Cell dateCell = new Cell();
        dateCell.setBorder(Border.NO_BORDER);
        dateCell.setTextAlignment(TextAlignment.RIGHT);
        dateCell.add(new Paragraph("Fecha: " + dateFormatted).setBold());

        infoTable.addCell(invoiceCell);
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
     * Adds the delivery notes section with all items from each delivery note
     */
    private static void addDeliveryNotesSection(Document document, List<DeliveryNoteEntity> deliveryNotes) {
        // Create table with 7 columns (same as items table)
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

        // Process each delivery note
        for (DeliveryNoteEntity deliveryNote : deliveryNotes) {
            // Add delivery note header (spans all columns)
            Cell noteHeaderCell = new Cell(1, 7);
            noteHeaderCell.setBackgroundColor(ColorConstants.LIGHT_GRAY);

            String headerText = "Albarán nº " + deliveryNote.getIndex() + " de fecha " +
                    deliveryNote.getDate().format(DATE_FORMATTER);

            noteHeaderCell.add(new Paragraph(headerText).setBold());
            itemsTable.addCell(noteHeaderCell);

            // Get all items for this delivery note
            List<DeliveryNoteItemEntity> items = getItemsForDeliveryNote(deliveryNote.getId());

            // Add all items for this delivery note
            for (DeliveryNoteItemEntity item : items) {
                // Get article name (concept)
                Integer articleID = item.getArticleID();
                String articleName = articleID != null ? ArticleDAO.getInstance().getNameById(articleID) : "";

                // Calculate amount (quantity * price)
                BigDecimal amount = item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));

                // Add item cells to the table
                itemsTable.addCell(new Cell().add(new Paragraph(articleID != null ? articleID.toString() : "")));
                itemsTable.addCell(new Cell().add(new Paragraph(articleName != null ? articleName : "")));
                itemsTable.addCell(new Cell().add(new Paragraph(item.getTrace1() != null ? item.getTrace1() : "")));
                itemsTable.addCell(new Cell().add(new Paragraph(item.getTrace2() != null ? item.getTrace2() : "")));
                itemsTable.addCell(new Cell().add(new Paragraph(String.valueOf(item.getQuantity()))));
                itemsTable.addCell(new Cell().add(new Paragraph(item.getPrice().toString() + " €")));
                itemsTable.addCell(new Cell().add(new Paragraph(amount.toString() + " €")));
            }
        }

        document.add(itemsTable);
    }

    /**
     * Helper method to get all items for a specific delivery note
     */
    private static List<DeliveryNoteItemEntity> getItemsForDeliveryNote(Integer deliveryNoteId) {
        try (org.hibernate.Session session = HibernateUtil.getSessionFactory().openSession()) {
            org.hibernate.query.Query<DeliveryNoteItemEntity> query = session.createQuery(
                    "FROM DeliveryNoteItemEntity WHERE deliveryNoteID = :id",
                    DeliveryNoteItemEntity.class
            );
            query.setParameter("id", deliveryNoteId);
            return query.list();
        } catch (Exception e) {
            System.err.println("Error getting items for delivery note: " + e.getMessage());
            e.printStackTrace();
            return List.of(); // Return empty list in case of error
        }
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
     * Adds the invoice totals section with VAT information
     */
    private static void addInvoiceTotalsSection(Document document, InvoiceEntity invoice) {
        // Get VAT configuration
        VATConfigEntity vatConfig = VATConfigDAO.getInstance().getCurrentConfig();
        double vatRate = (vatConfig != null) ? vatConfig.getVatRate() : 21.0; // Default to 21% if no config

        // Get client type to determine if we apply VAT
        ClientEntity client = ClientDAO.getInstance().getById(invoice.getClientId());
        boolean applyVAT = (client != null && client.getClientType() != null && client.getClientType() == 1);

        // Calculate base amount and VAT
        double baseAmount = invoice.getTotalAmount();
        double vatAmount = 0.0;

        if (applyVAT) {
            // If totalAmount already includes VAT, we need to calculate backwards
            // Otherwise, we calculate VAT from the base amount
            if (invoice.getType() != null && invoice.getType() == 1) {
                // Type 1 means VAT included
                baseAmount = baseAmount / (1 + (vatRate / 100));
                vatAmount = invoice.getTotalAmount() - baseAmount;
            } else {
                // VAT not included, calculate it
                vatAmount = baseAmount * (vatRate / 100);
            }
        }

        // Calculate total amount
        double totalWithVAT = baseAmount + vatAmount;

        // Create totals table with 5 columns
        document.add(new Paragraph(" ").setMarginBottom(20)); // Add some spacing

        Table totalsTable = new Table(UnitValue.createPercentArray(new float[]{20, 20, 20, 20, 20}));
        totalsTable.setWidth(UnitValue.createPercentValue(100));

        // Add headers
        String[] headers = {"Base Imponible", "IVA", "Total Factura", "", "Total a Pagar"};
        for (String header : headers) {
            Cell cell = new Cell();
            cell.add(new Paragraph(header).setBold());
            cell.setBackgroundColor(ColorConstants.LIGHT_GRAY);
            cell.setTextAlignment(TextAlignment.CENTER);
            totalsTable.addCell(cell);
        }

        // Add values
        String baseAmountFormatted = String.format("%.2f €", baseAmount);
        String vatAmountFormatted = String.format("%.2f €", vatAmount);
        String totalAmountFormatted = String.format("%.2f €", totalWithVAT);

        Cell baseCell = new Cell();
        baseCell.add(new Paragraph(baseAmountFormatted));
        baseCell.setTextAlignment(TextAlignment.CENTER);
        baseCell.setBorder(new SolidBorder(ColorConstants.BLACK, 1));

        Cell vatCell = new Cell();
        vatCell.add(new Paragraph(vatAmountFormatted));
        vatCell.setTextAlignment(TextAlignment.CENTER);
        vatCell.setBorder(new SolidBorder(ColorConstants.BLACK, 1));

        Cell totalCell = new Cell();
        totalCell.add(new Paragraph(totalAmountFormatted));
        totalCell.setTextAlignment(TextAlignment.CENTER);
        totalCell.setBorder(new SolidBorder(ColorConstants.BLACK, 1));

        Cell emptyCell = new Cell();
        emptyCell.setBorder(new SolidBorder(ColorConstants.BLACK, 1));

        Cell totalPayCell = new Cell();
        totalPayCell.add(new Paragraph(totalAmountFormatted));
        totalPayCell.setTextAlignment(TextAlignment.CENTER);
        totalPayCell.setBorder(new SolidBorder(ColorConstants.BLACK, 1));

        totalsTable.addCell(baseCell);
        totalsTable.addCell(vatCell);
        totalsTable.addCell(totalCell);
        totalsTable.addCell(emptyCell);
        totalsTable.addCell(totalPayCell);

        document.add(totalsTable);
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