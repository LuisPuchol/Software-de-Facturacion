package com.luis.facturacion.mvc_article;

import com.luis.facturacion.mvc_article.database.ArticleDAO;
import com.luis.facturacion.mvc_article.database.ArticleEntity;
import com.luis.facturacion.utils.ShowAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class ArticleModel {
    private static ArticleModel instance;
    private ArticleController articleController;
    private final ObservableList<ArticleEntity> articulosList;

    ArticleModel() {
        System.out.println("Article Model created");
        this.articulosList = FXCollections.observableArrayList();
    }

    public static ArticleModel getInstance() {
        if (instance == null) {
            instance = new ArticleModel();
        }
        return instance;
    }

    public void setController(ArticleController articleController) {
        if (this.articleController == null) {
            this.articleController = articleController;
        }
    }

    public boolean addArticle(String ind, String name) {

        if (!validateFields(ind, name)) {
            return false;
        }

        try {
            // Conversion
            int indice = castingToInt(ind, "Índice");

            // Create Entity
            ArticleEntity articleEntity = new ArticleEntity();
            articleEntity.setIndex(indice);
            articleEntity.setName(name);

            // Save to DDBB
            ArticleDAO.getInstance().save(articleEntity);
            System.out.println("Article Saved.");
            return true;

        } catch (IllegalArgumentException e) {
            ShowAlert.showError("Error de Validación", e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected Error adding article: " + e.getMessage());
            ShowAlert.showError("Error", "Error inesperado al guardar el artículo: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Validates required fields and shows alerts for any errors.
     *
     * @param ind  Article index
     * @param name Article name
     * @return true if all validations pass, false otherwise
     */
    private boolean validateFields(String ind, String name) {
        if (ind == null || ind.trim().isEmpty()) {
            ShowAlert.showError("Campo Requerido", "El campo 'Índice' es obligatorio.");
            return false;
        }

        if (name == null || name.trim().isEmpty()) {
            ShowAlert.showError("Campo Requerido", "El campo 'Nombre' es obligatorio.");
            return false;
        }

        return true;
    }

    private int castingToInt(String value, String field) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El campo '" + field + "' debe ser un número válido.");
        }
    }

    private double castingToDouble(String value, String field) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El campo '" + field + "' debe ser un número decimal válido.");
        }
    }

    public List<ArticleEntity> getAllArticles() {
        return ArticleDAO.getInstance().getAll();
    }

}