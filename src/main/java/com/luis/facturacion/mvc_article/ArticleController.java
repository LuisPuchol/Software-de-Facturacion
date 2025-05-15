package com.luis.facturacion.mvc_article;

import com.luis.facturacion.AppController;
import com.luis.facturacion.mvc_article.database.ArticleEntity;
import com.luis.facturacion.utils.TabFunction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.util.List;

public class ArticleController {
    private ArticleModel articleModel;
    private AppController appController;
    private TabFunction tabFunction;

    @FXML
    private TableView<ArticleEntity> articulosTable;
    @FXML
    private TableColumn<ArticleEntity, Integer> columnInd;
    @FXML
    private TableColumn<ArticleEntity, Integer> columnName;

    @FXML
    private TextField indArticle, nameArticle;

    @FXML
    private Button newButton, editButton, deleteButton, exitButton;

    @FXML
    private BorderPane rootPane;

    public ArticleController() {
        System.out.println("Article Controller created");
        this.articleModel = ArticleModel.getInstance();
        tabFunction = new TabFunction();
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
        articleModel.setController(this);
    }


    /**
     * Stuff view needs
     */
    @FXML
    public void initialize() {
        columnInd.setCellValueFactory(new PropertyValueFactory<>("index"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));

        tabFunction.configureTabFunction((GridPane) rootPane.getCenter());

        loadTableData();
    }

    private void loadTableData() {
        List<ArticleEntity> articulosBD = articleModel.getAllArticles();
        ObservableList<ArticleEntity> articleEntityObservableList = FXCollections.observableArrayList(articulosBD);
        articulosTable.setItems(articleEntityObservableList);
    }

    /**
     * Add to DDBB, clean fields and reload data
     */
    @FXML
    private void handleNewButton() {

        articleModel.addArticle(indArticle.getText(), nameArticle.getText());

        cleanFields();
        loadTableData();
    }


    @FXML
    private void handleDeleteButton() {
        //Dont delete, "deactivate"
    }

    /**
     * Close actual window
     */
    @FXML
    private void handleExitButton() {
        exitButton.getScene().getWindow().hide();
    }


    private void cleanFields() {
        indArticle.clear();
        nameArticle.clear();
    }

    /**
     * Stuff model Asks
     */
    public String getProductByID(Integer id) {
        return articleModel.getProductByID(id);
    }


}


