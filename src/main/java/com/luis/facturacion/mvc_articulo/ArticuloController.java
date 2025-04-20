package com.luis.facturacion.mvc_articulo;

import com.luis.facturacion.AppController;
import com.luis.facturacion.mvc_articulo.database.ArticuloEntity;
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

        // Obtener el GridPane del centro del BorderPane (donde están los campos)
        GridPane formGrid = (GridPane) rootPane.getCenter();

        // Obtener todos los TextFields del formulario
        List<TextField> textFields = getAllTextFields(formGrid);

        // Configurar el comportamiento de la tecla Enter para cada TextField
        for (TextField textField : textFields) {
            configureEnterKeyBehavior(textField);
        }

        // También puedes agregar un comportamiento especial para el último TextField
        // para que al presionar Enter en el último campo, se enfoque en el TextArea
        if (!textFields.isEmpty()) {
            TextField lastTextField = textFields.get(textFields.size() - 1);
            lastTextField.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    event.consume();
                }
            });
        }

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


    /***
     * Estos 2 metodos se encargan de hacer que la tecla enter funcione como tabulador.
     * Posiblemente se externalice de algun modo en una clase para reutilizarse
     */
    private List<TextField> getAllTextFields(GridPane gridPane) {
        List<TextField> textFields = new ArrayList<>();

        // Recorrer todos los nodos hijos del GridPane
        for (Node node : gridPane.getChildren()) {
            if (node instanceof TextField) {
                // No incluimos el campo ID que es no editable
                if (!node.equals(indArticle)) {
                    textFields.add((TextField) node);
                }
            }
        }

        return textFields;
    }

    private void configureEnterKeyBehavior(TextField textField) {
        textField.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                // Consume el evento para evitar que se procese de la forma predeterminada
                event.consume();

                // Simula la pulsación de la tecla Tab
                textField.fireEvent(new KeyEvent(KeyEvent.KEY_PRESSED,
                        "", "", KeyCode.TAB,
                        false, false, false, false));
            }
        });
    }

}


