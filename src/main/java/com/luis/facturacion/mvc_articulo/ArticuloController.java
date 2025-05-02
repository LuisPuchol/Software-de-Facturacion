package com.luis.facturacion.mvc_articulo;

import com.luis.facturacion.AppController;
import com.luis.facturacion.mvc_articulo.database.ArticuloEntity;
import com.luis.facturacion.utils.TabFunction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class ArticuloController {
    private ArticuloModel articuloModel;
    private AppController appController;
    private TabFunction tabFunction;

    @FXML
    private TableView<ArticuloEntity> articulosTable;
    @FXML
    private TableColumn<ArticuloEntity, Integer> columnInd;
    @FXML
    private TableColumn<ArticuloEntity, Integer> columnName;

    @FXML
    private TextField indArticle, nameArticle;

    @FXML
    private Button nuevoButton, editarButton, eliminarButton, salirButton;

    @FXML
    private BorderPane rootPane;

    public ArticuloController() {
        System.out.println("Articulos Controller created");
        this.articuloModel = ArticuloModel.getInstance();
        tabFunction = new TabFunction();
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
        articuloModel.setController(this);
    }


    // Cosas que pide la View
    @FXML
    public void initialize() {
        columnInd.setCellValueFactory(new PropertyValueFactory<>("indice"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        //Gestionar la tecla enter como tabulador

        tabFunction.configureTabFunction((GridPane) rootPane.getCenter());


        // También puedes agregar un comportamiento especial para el último TextField
        // para que al presionar Enter en el último campo, se enfoque en el TextArea
        //if (!textFields.isEmpty()) {
        //    TextField lastTextField = textFields.get(textFields.size() - 1);
        //    lastTextField.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
        //        if (event.getCode() == KeyCode.ENTER) {
        //            event.consume();
        //        }
        //    });
        //}

        cargarDatos();
    }

    private void cargarDatos() {
        List<ArticuloEntity> articulosBD = articuloModel.cargarArticulos();
        ObservableList<ArticuloEntity> articuloEntityObservableList = FXCollections.observableArrayList(articulosBD);
        articulosTable.setItems(articuloEntityObservableList);
    }

    @FXML
    private void handleNuevoButton() {
        //añadir a la BBDD y limpiar los campos
        articuloModel.agregarArticuloDesdeFormulario(indArticle.getText(), nameArticle.getText());

        limpiarFormulario();
        cargarDatos();
    }

    @FXML
    private void handleEditarButton() {
        //
    }

    @FXML
    private void handleEliminarButton() {
        //No elimina, "desactiva"
    }


    @FXML
    private void handleSalirButton() {
        // Cerrar la ventana actual
        salirButton.getScene().getWindow().hide();
    }


    private void limpiarFormulario() {
        indArticle.clear();
        nameArticle.clear();
    }

    //Cosas que pide el Model
    public String getProductByID(Integer id) {
        return articuloModel.getProductByID(id);
    }


}


