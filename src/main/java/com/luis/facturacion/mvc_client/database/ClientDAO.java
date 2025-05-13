package com.luis.facturacion.mvc_client.database;
import com.luis.facturacion.mvc_article.database.ArticleDAO;
import com.luis.facturacion.utils.GlobalDAO;

public class ClientDAO extends GlobalDAO<ClientEntity, Integer> {
    private static ClientDAO instance;

    public ClientDAO() {
        super(ClientEntity.class);
    }

    public static ClientDAO getInstance() {
        if (instance == null) {
            instance = new ClientDAO();
        }
        return instance;
    }

}