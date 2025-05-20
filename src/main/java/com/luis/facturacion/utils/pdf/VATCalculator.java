package com.luis.facturacion.utils.pdf;

import com.luis.facturacion.mvc_client.database.ClientDAO;
import com.luis.facturacion.mvc_client.database.ClientEntity;
import com.luis.facturacion.mvc_invoice.database.InvoiceEntity;
import com.luis.facturacion.mvc_vatConfig.database.VATConfigDAO;
import com.luis.facturacion.mvc_vatConfig.database.VATConfigEntity;

import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Separate class to handle VAT calculations for invoices
 */
public class VATCalculator {
    private static final Logger LOGGER = Logger.getLogger(VATCalculator.class.getName());
    private static final double DEFAULT_VAT_RATE = 21.0;

    public static class VATCalculation {
        private final BigDecimal baseAmount;
        private final BigDecimal vatAmount;
        private final BigDecimal totalAmount;

        public VATCalculation(BigDecimal baseAmount, BigDecimal vatAmount, BigDecimal totalAmount) {
            this.baseAmount = baseAmount;
            this.vatAmount = vatAmount;
            this.totalAmount = totalAmount;
        }

        public BigDecimal getBaseAmount() { return baseAmount; }
        public BigDecimal getVatAmount() { return vatAmount; }
        public BigDecimal getTotalAmount() { return totalAmount; }
    }

    public VATCalculation calculateVAT(InvoiceEntity invoice, ClientEntity client) {
        try {
            double vatRate = getVATRate();
            boolean applyVAT = shouldApplyVAT(client);

            BigDecimal invoiceTotal = BigDecimal.valueOf(invoice.getTotalAmount());
            BigDecimal baseAmount;
            BigDecimal vatAmount;

            if (!applyVAT) {
                // No VAT applies
                baseAmount = invoiceTotal;
                vatAmount = BigDecimal.ZERO;
            } else if (isVATIncludedInTotal(invoice)) {
                // VAT is included in the total, calculate backwards
                BigDecimal vatMultiplier = BigDecimal.valueOf(1 + (vatRate / 100));
                baseAmount = invoiceTotal.divide(vatMultiplier, 2, BigDecimal.ROUND_HALF_UP);
                vatAmount = invoiceTotal.subtract(baseAmount);
            } else {
                // VAT not included, calculate forward
                baseAmount = invoiceTotal;
                vatAmount = baseAmount.multiply(BigDecimal.valueOf(vatRate / 100))
                        .setScale(2, BigDecimal.ROUND_HALF_UP);
            }

            BigDecimal totalWithVAT = baseAmount.add(vatAmount);

            return new VATCalculation(baseAmount, vatAmount, totalWithVAT);

        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error calculating VAT, using defaults", e);
            // Return safe defaults
            BigDecimal total = BigDecimal.valueOf(invoice.getTotalAmount());
            return new VATCalculation(total, BigDecimal.ZERO, total);
        }
    }

    private double getVATRate() {
        try {
            VATConfigEntity vatConfig = VATConfigDAO.getInstance().getCurrentConfig();
            return (vatConfig != null) ? vatConfig.getVatRate() : DEFAULT_VAT_RATE;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error getting VAT rate, using default", e);
            return DEFAULT_VAT_RATE;
        }
    }

    private boolean shouldApplyVAT(ClientEntity client) {
        return client != null && client.getClientType() != null && client.getClientType() == 1;
    }

    private boolean isVATIncludedInTotal(InvoiceEntity invoice) {
        return invoice.getType() != null && invoice.getType() == 1;
    }
}