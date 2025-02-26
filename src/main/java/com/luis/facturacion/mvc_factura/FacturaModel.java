package com.luis.facturacion.mvc_factura;

import com.luis.facturacion.mvc_factura.database.FacturaDAO;
import com.luis.facturacion.mvc_factura.database.FacturaEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FacturaModel {
    private static FacturaModel instance;
    private FacturaController facturaController;
    private final FacturaDAO facturaDAO;
    private final ObservableList<FacturaEntity> facturasList;

    // Datos de la factura actual
    private int numeroFactura;
    private LocalDate fechaFactura;
    private String clienteFactura;
    private double ivaFactura;
    private String observaciones;

    // Lista para almacenar las líneas de la factura actual
    private final List<Object[]> lineasFactura;

    public FacturaModel() {
        System.out.println("Factura Model Created");
        this.facturaDAO = new FacturaDAO();
        this.facturasList = FXCollections.observableArrayList();
        this.lineasFactura = new ArrayList<>();
    }

    public static FacturaModel getInstance() {
        if (instance == null) {
            instance = new FacturaModel();
        }
        return instance;
    }

    public void setController(FacturaController facturaController){
        if (this.facturaController == null){
            this.facturaController = facturaController;
        }
    }

    public void startFactura(int numeroFactura, LocalDate fechaFactura, String clienteFactura, double ivaFactura, String observaciones) {
        // asigna: Número, Fecha, Cliente, IVA, Observaciones
        this.numeroFactura = numeroFactura;
        this.fechaFactura = fechaFactura;
        this.clienteFactura = clienteFactura;
        this.ivaFactura = ivaFactura;
        this.observaciones = observaciones;

        // Limpiar líneas de factura anteriores
        this.lineasFactura.clear();
    }

    public void addLine(Integer id, Integer quantity, Double price) {
        // Obtener nombre del producto (ejemplo, deberás implementar esto según tu sistema)
        String nombreProducto = obtenerNombreProducto(id);

        // Calcular importe
        double importe = quantity * price;

        // Crear línea y añadir a la lista
        Object[] linea = new Object[] {id, nombreProducto, quantity, price, importe};
        lineasFactura.add(linea);
    }

    // Método auxiliar - deberás implementarlo según tu sistema
    private String obtenerNombreProducto(Integer id) {
        // Aquí deberías obtener el nombre del producto de tu base de datos
        // Por ahora, retorno un valor de ejemplo
        return "Producto " + id;
    }

    public void finishFactura() {
        // Calcular total sin IVA
        double totalSinIVA = 0;
        for (Object[] linea : lineasFactura) {
            totalSinIVA += (double) linea[4]; // El importe está en la posición 4
        }

        // Calcular IVA
        double importeIVA = totalSinIVA * (ivaFactura / 100);

        // Calcular total con IVA
        double totalConIVA = totalSinIVA + importeIVA;

        // Crear entidad de factura para guardar en BD
        //FacturaEntity facturaEntity = new FacturaEntity();
        //facturaEntity.setNumeroFactura(numeroFactura);
        //facturaEntity.setFechaFactura(fechaFactura);
        //facturaEntity.setClienteFactura(clienteFactura);
        //facturaEntity.setIvaFactura(ivaFactura);
        //facturaEntity.setObservaciones(observaciones);
        //facturaEntity.setTotalSinIVA(totalSinIVA);
        //facturaEntity.setTotalIVA(importeIVA);
        //facturaEntity.setTotalConIVA(totalConIVA);

        // Guardar en base de datos
        //facturaDAO.guardarFactura(facturaEntity);
    }

    /**
     * Genera un PDF para la factura actual
     * @return La ruta del archivo PDF generado
     */
    public String generatePDF() {
        // Crear directorio de facturas si no existe
        String dirPath = "facturas";
        File directory = new File(dirPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Generar nombre del archivo
        String fileName = String.format("factura_%d.pdf", numeroFactura);
        String filePath = dirPath + File.separator + fileName;

        // Generar PDF con los datos
        FacturaPDFGenerator.generateFacturaPDF(
                numeroFactura,
                fechaFactura,
                clienteFactura,
                lineasFactura,
                ivaFactura,
                observaciones,
                filePath
        );

        return filePath;
    }

    // Getters para acceder a los datos de la factura actual
    public int getNumeroFactura() {
        return numeroFactura;
    }

    public LocalDate getFechaFactura() {
        return fechaFactura;
    }

    public String getClienteFactura() {
        return clienteFactura;
    }

    public double getIvaFactura() {
        return ivaFactura;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public List<Object[]> getLineasFactura() {
        return lineasFactura;
    }
}