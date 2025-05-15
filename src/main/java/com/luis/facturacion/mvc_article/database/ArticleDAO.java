package com.luis.facturacion.mvc_article.database;

import com.luis.facturacion.utils.GlobalDAO;

public class ArticleDAO extends GlobalDAO<ArticleEntity, Integer> {
    private static ArticleDAO instance;

    private ArticleDAO() {
        super(ArticleEntity.class);
    }

    public static ArticleDAO getInstance() {
        if (instance == null) {
            instance = new ArticleDAO();
        }
        return instance;
    }

}