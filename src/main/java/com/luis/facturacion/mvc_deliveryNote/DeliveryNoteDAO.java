package com.luis.facturacion.mvc_deliveryNote;

import com.luis.facturacion.mvc_article.database.ArticleEntity;
import com.luis.facturacion.utils.GlobalDAO;


public class DeliveryNoteDAO extends GlobalDAO<DeliveryNoteEntity, Integer> {
    private static DeliveryNoteDAO instance;

    private DeliveryNoteDAO() {
        super(DeliveryNoteEntity.class);
    }

    public static DeliveryNoteDAO getInstance() {
        if (instance == null) {
            instance = new DeliveryNoteDAO();
        }
        return instance;
    }
}
