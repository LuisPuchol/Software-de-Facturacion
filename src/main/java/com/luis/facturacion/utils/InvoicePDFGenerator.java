package com.luis.facturacion.utils;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
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

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Clase simple para generar facturas en PDF usando iText
 */
public class InvoicePDFGenerator {

    /**
     * Genera un PDF para la factura con los datos proporcionados
     *
     * @param numeroFactura Número de factura
     * @param fechaFactura Fecha de la factura
     * @param clienteFactura Nombre del cliente
     * @param lineasFactura Lista de datos de líneas (cada fila es un Object[] con [codigo, nombre, cantidad, precio, importe])
     * @param ivaFactura Porcentaje de IVA (ejemplo: 21.0)
     * @param observaciones Observaciones (opcional)
     * @param outputPath Ruta donde guardar el PDF
     */
    public static void generateFacturaPDF(
            int numeroFactura,
            LocalDate fechaFactura,
            String clienteFactura,
            List<Object[]> lineasFactura,
            double ivaFactura,
            String observaciones,
            String outputPath) {

        try {
            // Crear el archivo de salida
            File file = new File(outputPath);
            file.getParentFile().mkdirs();

            // Inicializar el PDF
            PdfWriter writer = new PdfWriter(outputPath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf, PageSize.A4);
            document.setMargins(36, 36, 36, 36);

            // Fuentes
            PdfFont fontBold = PdfFontFactory.createFont("Helvetica-Bold");
            PdfFont fontRegular = PdfFontFactory.createFont("Helvetica");

            // Cabecera de la factura
            Paragraph header = new Paragraph("FACTURA")
                    .setFont(fontBold)
                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(header);

            // Datos de la factura
            Table facturaInfo = new Table(UnitValue.createPercentArray(new float[]{30, 70}))
                    .setWidth(UnitValue.createPercentValue(100));

            addInfoRow(facturaInfo, "Nº Factura:", String.valueOf(numeroFactura), fontBold, fontRegular);
            addInfoRow(facturaInfo, "Fecha:", fechaFactura.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), fontBold, fontRegular);
            addInfoRow(facturaInfo, "Cliente:", clienteFactura, fontBold, fontRegular);

            document.add(facturaInfo);
            document.add(new Paragraph("\n"));

            // Títulos de las columnas de la tabla de líneas
            Table lineTable = new Table(UnitValue.createPercentArray(new float[]{10, 40, 15, 15, 20}))
                    .setWidth(UnitValue.createPercentValue(100));

            // Estilo para la cabecera de la tabla
            DeviceRgb headerBgColor = new DeviceRgb(210, 210, 210);

            addTableHeader(lineTable, "Código", fontBold, headerBgColor);
            addTableHeader(lineTable, "Producto", fontBold, headerBgColor);
            addTableHeader(lineTable, "Cantidad", fontBold, headerBgColor);
            addTableHeader(lineTable, "Precio", fontBold, headerBgColor);
            addTableHeader(lineTable, "Importe", fontBold, headerBgColor);

            // Agregar las líneas de la factura
            double totalSinIVA = 0;
            for (Object[] linea : lineasFactura) {
                int codigo = (int) linea[0];
                String nombre = (String) linea[1];
                int cantidad = (int) linea[2];
                double precio = (double) linea[3];
                double importe = (double) linea[4];

                totalSinIVA += importe;

                lineTable.addCell(new Cell().add(new Paragraph(String.valueOf(codigo)).setFont(fontRegular)).setBorder(Border.NO_BORDER));
                lineTable.addCell(new Cell().add(new Paragraph(nombre).setFont(fontRegular)).setBorder(Border.NO_BORDER));
                lineTable.addCell(new Cell().add(new Paragraph(String.valueOf(cantidad)).setFont(fontRegular).setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER));
                lineTable.addCell(new Cell().add(new Paragraph(String.format("%.2f €", precio)).setFont(fontRegular).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));
                lineTable.addCell(new Cell().add(new Paragraph(String.format("%.2f €", importe)).setFont(fontRegular).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));
            }

            document.add(lineTable);
            document.add(new Paragraph("\n"));

            // Totales
            Table totals = new Table(UnitValue.createPercentArray(new float[]{70, 30}))
                    .setWidth(UnitValue.createPercentValue(100));

            totals.addCell(new Cell().add(new Paragraph("Subtotal:").setFont(fontBold).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));
            totals.addCell(new Cell().add(new Paragraph(String.format("%.2f €", totalSinIVA)).setFont(fontRegular).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));

            double ivaAmount = totalSinIVA * (ivaFactura / 100);
            totals.addCell(new Cell().add(new Paragraph("IVA (" + ivaFactura + "%):").setFont(fontBold).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));
            totals.addCell(new Cell().add(new Paragraph(String.format("%.2f €", ivaAmount)).setFont(fontRegular).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));

            double totalConIVA = totalSinIVA + ivaAmount;
            Cell totalLabel = new Cell().add(new Paragraph("TOTAL:").setFont(fontBold).setFontSize(12).setTextAlignment(TextAlignment.RIGHT));
            totalLabel.setBorder(Border.NO_BORDER);
            totals.addCell(totalLabel);

            Cell totalValue = new Cell().add(new Paragraph(String.format("%.2f €", totalConIVA)).setFont(fontBold).setFontSize(12).setTextAlignment(TextAlignment.RIGHT));
            totalValue.setBorder(Border.NO_BORDER);
            totals.addCell(totalValue);

            document.add(totals);

            // Observaciones
            if (observaciones != null && !observaciones.trim().isEmpty()) {
                document.add(new Paragraph("\n"));
                document.add(new Paragraph("Observaciones:").setFont(fontBold));
                document.add(new Paragraph(observaciones).setFont(fontRegular));
            }

            document.close();

            System.out.println("PDF Factura generado correctamente en: " + outputPath);

        } catch (IOException e) {
            System.err.println("Error al generar el PDF: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void addInfoRow(Table table, String label, String value, PdfFont labelFont, PdfFont valueFont) {
        table.addCell(new Cell().add(new Paragraph(label).setFont(labelFont)).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph(value).setFont(valueFont)).setBorder(Border.NO_BORDER));
    }

    private static void addTableHeader(Table table, String text, PdfFont font, DeviceRgb bgColor) {
        Cell cell = new Cell();
        cell.setBackgroundColor(bgColor);
        cell.setBorderBottom(new SolidBorder(ColorConstants.BLACK, 1));
        cell.add(new Paragraph(text).setFont(font));
        table.addCell(cell);
    }
}