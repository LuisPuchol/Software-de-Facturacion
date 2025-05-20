package com.luis.facturacion.utils.pdf;

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
import com.luis.facturacion.mvc_deliveryNote.DeliveryNoteItemDAO;
import com.luis.facturacion.mvc_deliveryNote.DeliveryNoteItemEntity;
import com.luis.facturacion.mvc_invoice.database.InvoiceEntity;
import com.luis.facturacion.utils.HibernateUtil;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for generating PDF documents
 */
public class PDFGenerator {
    private static final Logger LOGGER = Logger.getLogger(PDFGenerator.class.getName());

    private static final String COMPANY_NAME = "Frutas Puchol, S.L.";
    private static final String COMPANY_NIF = "X1422821H";
    private static final String COMPANY_ADDRESS = "Mercado Pedro Garau Puestos 41, 42 - 07007 Palma de mallorca";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final VATCalculator VAT_CALCULATOR = new VATCalculator();

    /**
     * Generates a PDF for a delivery note
     */
    public static File generateDeliveryNotePDF(DeliveryNoteEntity deliveryNote, List<DeliveryNoteItemEntity> items) throws IOException {
        File tempFile = createTempFile("albaran_" + deliveryNote.getIndex());

        try (PdfWriter writer = new PdfWriter(tempFile);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {

            ClientEntity client = ClientDAO.getInstance().getById(deliveryNote.getClientId());

            addDocumentHeader(document, "Albarán nº: " + deliveryNote.getIndex(),
                    deliveryNote.getDate().format(DATE_FORMATTER), client);
            addItemsTable(document, List.of(deliveryNote), false);
            addSimpleTotal(document, deliveryNote.getTotalAmount());
            addFooter(document);

            return tempFile;

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error generating delivery note PDF", e);
            if (tempFile.exists()) {
                tempFile.delete();
            }
            throw new IOException("Failed to generate delivery note PDF", e);
        }
    }

    /**
     * Generates a PDF for an invoice
     */
    public static File generateInvoicePDF(InvoiceEntity invoice, List<DeliveryNoteEntity> deliveryNotes) throws IOException {
        File tempFile = createTempFile("factura_" + invoice.getId());

        try (PdfWriter writer = new PdfWriter(tempFile);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {

            ClientEntity client = ClientDAO.getInstance().getById(invoice.getClientId());

            addDocumentHeader(document, "Factura nº: " + invoice.getId(),
                    invoice.getDate().format(DATE_FORMATTER), client);
            addItemsTable(document, deliveryNotes, true);
            addInvoiceTotals(document, invoice, client);

            return tempFile;

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error generating invoice PDF", e);
            if (tempFile.exists()) {
                tempFile.delete();
            }
            throw new IOException("Failed to generate invoice PDF", e);
        }
    }

    private static void addDocumentHeader(Document document, String documentTitle, String date, ClientEntity client) {
        Table headerTable = new Table(UnitValue.createPercentArray(new float[]{50, 50}));
        headerTable.setWidth(UnitValue.createPercentValue(100));

        // Company info
        Cell companyCell = createBorderlessCell();
        companyCell.add(new Paragraph(COMPANY_NAME).setBold());
        companyCell.add(new Paragraph("NIF: " + COMPANY_NIF));
        companyCell.add(new Paragraph(COMPANY_ADDRESS));

        // Client info
        Cell clientCell = createBorderedCell();

        // Document info table
        Table infoTable = new Table(UnitValue.createPercentArray(new float[]{50, 50}));
        infoTable.setWidth(UnitValue.createPercentValue(100));

        Cell docCell = createBorderlessCell();
        docCell.add(new Paragraph(documentTitle).setBold());

        Cell dateCell = createBorderlessCell();
        dateCell.setTextAlignment(TextAlignment.RIGHT);
        dateCell.add(new Paragraph("Fecha: " + date).setBold());

        infoTable.addCell(docCell);
        infoTable.addCell(dateCell);
        clientCell.add(infoTable);

        // Client details
        String clientName = client.getName();
        String clientAddress = client.getAddress();
        String clientNif = client.getCif();

        clientCell.add(new Paragraph("Cliente: " + clientName).setMarginTop(10));
        clientCell.add(new Paragraph("Dirección: " + clientAddress));
        clientCell.add(new Paragraph("CIF/NIF: " + clientNif));

        headerTable.addCell(companyCell);
        headerTable.addCell(clientCell);
        document.add(headerTable);
        document.add(new Paragraph(" ").setMarginBottom(20));
    }

    /**
     * Adds items table for delivery notes - unified method
     * @param document PDF document
     * @param deliveryNotes List of delivery notes
     * @param showHeaders Whether to show delivery note headers (for invoices)
     */
    private static void addItemsTable(Document document, List<DeliveryNoteEntity> deliveryNotes, boolean showHeaders) {
        Table itemsTable = createItemsTableStructure();

        Map<Integer, String> allArticleNames = preloadAllArticleNames(deliveryNotes);

        for (DeliveryNoteEntity deliveryNote : deliveryNotes) {
            // delivery note header for invoices
            if (showHeaders) {
                Cell noteHeaderCell = new Cell(1, 7);
                noteHeaderCell.setBackgroundColor(ColorConstants.LIGHT_GRAY);
                noteHeaderCell.add(new Paragraph("Albarán nº " + deliveryNote.getIndex() +
                        " de fecha " + deliveryNote.getDate().format(DATE_FORMATTER)).setBold());
                itemsTable.addCell(noteHeaderCell);
            }

            List<DeliveryNoteItemEntity> items = DeliveryNoteItemDAO.getInstance().getItemsByDeliveryNoteId(deliveryNote.getId());

            for (DeliveryNoteItemEntity item : items) {
                addItemRow(itemsTable, item, allArticleNames);
            }
        }

        document.add(itemsTable);
    }

    /**
     * Adds a single item row to the table
     */
    private static void addItemRow(Table table, DeliveryNoteItemEntity item, Map<Integer, String> articleNames) {
        Integer articleID = item.getArticleID();
        String articleName = articleNames.get(articleID);
        BigDecimal amount = item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));

        table.addCell(new Cell().add(new Paragraph(String.valueOf(articleID))));
        table.addCell(new Cell().add(new Paragraph(articleName)));
        table.addCell(new Cell().add(new Paragraph(safeString(item.getTrace1()))));
        table.addCell(new Cell().add(new Paragraph(safeString(item.getTrace2()))));
        table.addCell(new Cell().add(new Paragraph(String.valueOf(item.getQuantity()))));
        table.addCell(new Cell().add(new Paragraph(formatCurrency(item.getPrice()))));
        table.addCell(new Cell().add(new Paragraph(formatCurrency(amount))));
    }

    /**
     * Preloads all article names for multiple delivery notes to avoid N+1 queries
     */
    private static Map<Integer, String> preloadAllArticleNames(List<DeliveryNoteEntity> deliveryNotes) {
        Map<Integer, String> articleNames = new HashMap<>();

        for (DeliveryNoteEntity deliveryNote : deliveryNotes) {
            List<DeliveryNoteItemEntity> items = DeliveryNoteItemDAO.getInstance().getItemsByDeliveryNoteId(deliveryNote.getId());
            for (DeliveryNoteItemEntity item : items) {
                Integer articleID = item.getArticleID();
                if (articleID != null && !articleNames.containsKey(articleID)) {
                    try {
                        String name = ArticleDAO.getInstance().getNameById(articleID);
                        articleNames.put(articleID, name != null ? name : "");
                    } catch (Exception e) {
                        LOGGER.log(Level.WARNING, "Error retrieving article name: " + articleID, e);
                        articleNames.put(articleID, "");
                    }
                }
            }
        }

        return articleNames;
    }

    private static void addInvoiceTotals(Document document, InvoiceEntity invoice, ClientEntity client) {
        VATCalculator.VATCalculation vatCalc = VAT_CALCULATOR.calculateVAT(invoice, client);

        document.add(new Paragraph(" ").setMarginBottom(20));

        Table totalsTable = new Table(UnitValue.createPercentArray(new float[]{20, 20, 20, 20, 20}));
        totalsTable.setWidth(UnitValue.createPercentValue(100));

        // Headers
        String[] headers = {"Base Imponible", "IVA", "Total Factura", "", "Total a Pagar"};
        for (String header : headers) {
            Cell cell = new Cell();
            cell.add(new Paragraph(header).setBold());
            cell.setBackgroundColor(ColorConstants.LIGHT_GRAY);
            cell.setTextAlignment(TextAlignment.CENTER);
            totalsTable.addCell(cell);
        }

        // Values
        totalsTable.addCell(createCenteredBorderedCell(formatCurrency(vatCalc.getBaseAmount())));
        totalsTable.addCell(createCenteredBorderedCell(formatCurrency(vatCalc.getVatAmount())));
        totalsTable.addCell(createCenteredBorderedCell(formatCurrency(vatCalc.getTotalAmount())));
        totalsTable.addCell(createBorderedCell()); // Empty cell
        totalsTable.addCell(createCenteredBorderedCell(formatCurrency(vatCalc.getTotalAmount())));

        document.add(totalsTable);
    }

    private static void addSimpleTotal(Document document, Double totalAmount) {
        Table totalTable = new Table(UnitValue.createPercentArray(new float[]{70, 30}));
        totalTable.setWidth(UnitValue.createPercentValue(100));
        totalTable.setMarginTop(20);

        totalTable.addCell(createBorderlessCell());

        Cell totalCell = createBorderedCell();
        totalCell.add(new Paragraph("TOTAL: " + formatCurrency(totalAmount)).setBold());
        totalCell.setTextAlignment(TextAlignment.RIGHT);
        totalTable.addCell(totalCell);

        document.add(totalTable);
    }

    private static void addFooter(Document document) {
        document.add(new Paragraph(" ").setMarginBottom(20));
        Paragraph footerText = new Paragraph("Gracias por su confianza");
        footerText.setTextAlignment(TextAlignment.CENTER);
        footerText.setFontSize(10);
        document.add(footerText);
    }

    private static File createTempFile(String prefix) throws IOException {
        return File.createTempFile(prefix + "_", ".pdf");
    }



    private static Table createItemsTableStructure() {
        Table table = new Table(UnitValue.createPercentArray(new float[]{8, 22, 20, 20, 9, 10, 11}));
        table.setWidth(UnitValue.createPercentValue(100));

        String[] headers = {"Código", "Concepto", "Trazabilidad 1", "Trazabilidad 2", "Cantidad", "Precio", "Importe"};
        for (String header : headers) {
            Cell cell = new Cell();
            cell.add(new Paragraph(header).setBold());
            cell.setBackgroundColor(ColorConstants.LIGHT_GRAY);
            table.addHeaderCell(cell);
        }
        return table;
    }

    private static Cell createBorderlessCell() {
        Cell cell = new Cell();
        cell.setBorder(Border.NO_BORDER);
        return cell;
    }

    private static Cell createBorderedCell() {
        Cell cell = new Cell();
        cell.setBorder(new SolidBorder(ColorConstants.BLACK, 1));
        return cell;
    }

    private static Cell createCenteredBorderedCell(String text) {
        Cell cell = createBorderedCell();
        cell.add(new Paragraph(text));
        cell.setTextAlignment(TextAlignment.CENTER);
        return cell;
    }

    private static String safeString(String value) {
        return value != null ? value : "";
    }

    private static String formatCurrency(Object amount) {
        if (amount instanceof BigDecimal) {
            return String.format("%.2f €", ((BigDecimal) amount).doubleValue());
        }
        if (amount instanceof Double) {
            return String.format("%.2f €", (Double) amount);
        }
        return amount.toString() + " €";
    }
}