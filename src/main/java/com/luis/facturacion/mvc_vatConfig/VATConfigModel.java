package com.luis.facturacion.mvc_vatConfig;

import com.luis.facturacion.mvc_vatConfig.database.VATConfigDAO;
import com.luis.facturacion.mvc_vatConfig.database.VATConfigEntity;

/**
 * Model for the VAT Configuration view.
 * Handles business logic and data access for VAT settings.
 */
public class VATConfigModel {
    private static VATConfigModel instance;
    private VATConfigController controller;

    private VATConfigDAO vatConfigDAO;

    private VATConfigModel() {
        this.vatConfigDAO = VATConfigDAO.getInstance();
    }

    /**
     * Gets the singleton instance of VATConfigModel.
     *
     * @return The singleton instance
     */
    public static VATConfigModel getInstance() {
        if (instance == null) {
            instance = new VATConfigModel();
        }
        return instance;
    }

    /**
     * Sets the controller reference.
     *
     * @param controller The controller to set
     */
    public void setController(VATConfigController controller) {
        if (this.controller == null) {
            this.controller = controller;
        }
    }

    /**
     * Gets the current VAT configuration from the database.
     *
     * @return The current VAT configuration or null if none exists
     */
    public VATConfigEntity getCurrentVATConfig() {
        return vatConfigDAO.getCurrentConfig();
    }

    /**
     * Saves a new VAT configuration to the database.
     * If a configuration already exists, it will update the existing one.
     *
     * @param vatRate The VAT rate as a percentage
     * @param surchargeRate The surcharge rate as a percentage
     */
    public void saveVATConfiguration(double vatRate, double surchargeRate) {
        VATConfigEntity currentConfig = getCurrentVATConfig();

        if (currentConfig != null) {
            currentConfig.setVatRate(vatRate);
            currentConfig.setSurchargeRate(surchargeRate);
            vatConfigDAO.update(currentConfig);
        } else {
            VATConfigEntity entity = new VATConfigEntity(vatRate, surchargeRate);
            vatConfigDAO.save(entity);
        }
    }
}
