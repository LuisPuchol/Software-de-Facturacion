package com.luis.facturacion.mvc_deliveryNote;

import com.luis.facturacion.mvc_article.database.ArticleEntity;
import com.luis.facturacion.utils.GlobalDAO;

import java.util.List;

public class DeliveryNoteItemDAO extends GlobalDAO<DeliveryNoteItemEntity, Integer> {
    private static DeliveryNoteItemDAO instance;

    private DeliveryNoteItemDAO() {
        super(DeliveryNoteItemEntity.class);
    }

    public static DeliveryNoteItemDAO getInstance() {
        if (instance == null) {
            instance = new DeliveryNoteItemDAO();
        }
        return instance;
    }
}
