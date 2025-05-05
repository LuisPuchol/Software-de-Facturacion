package com.luis.facturacion.mvc_article;

import com.luis.facturacion.mvc_article.database.ArticleDAO;
import com.luis.facturacion.mvc_article.database.ArticleEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class ArticleModel {
    private static ArticleModel instance;
    private ArticleController articleController;
    private final ArticleDAO articleDAO;
    private final ObservableList<ArticleEntity> articulosList;

    ArticleModel() {
        System.out.println("Articulo Model created");
        this.articleDAO = new ArticleDAO();
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

    public void addArticle(String ind, String name) {
        try {
            // Validations
            if (ind == null || ind.trim().isEmpty()) {
                throw new IllegalArgumentException("Lack Index.");
            }
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Lack Name");
            }

            // Conversion
            int indice = castingToInt(ind, "Index");

            // Create Entity
            ArticleEntity articleEntity = new ArticleEntity();

            articleEntity.setIndice(indice);
            articleEntity.setNombre(name);

            // Save on DDBB
            articleDAO.save(articleEntity);

            System.out.println("Article Saved.");

        } catch (IllegalArgumentException e) {
            System.err.println("Error adding article: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected Error adding article: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private int castingToInt(String value, String field) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Field '" + field + "' must be a valid number.");
        }
    }

    private double castingToDouble(String value, String field) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Field '" + field + "' must be a valid double.");
        }
    }

    public List<ArticleEntity> getAllArticles() {
        return articleDAO.getAll();
    }

    public String getProductByID(Integer id) {
        return articleDAO.getProductNameById(id);
    }
}
