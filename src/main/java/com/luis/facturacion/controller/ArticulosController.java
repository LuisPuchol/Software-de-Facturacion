package com.luis.facturacion.controller;

//package com.luis.facturacion.controller;

import com.luis.facturacion.view.Articulo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ArticulosController {

    @FXML
    private TableView<Articulo> articulosTable;

    @FXML
    private TableColumn<Articulo, String> codigoColumn;

    @FXML
    private TableColumn<Articulo, String> nombreColumn;

    @FXML
    private TextField codigoField;

    @FXML
    private TextField nombreField;

    @FXML
    private Button nuevoButton;

    @FXML
    private Button abrirButton;

    @FXML
    private Button eliminarButton;

    @FXML
    private Button salirButton;

    private final ObservableList<Articulo> articulosList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Configurar las columnas de la tabla
        codigoColumn.setCellValueFactory(cellData -> cellData.getValue().codigoProperty());
        nombreColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());

        // Conectar la lista con la tabla
        articulosTable.setItems(articulosList);

        // Manejar selección de fila
        articulosTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                codigoField.setText(newSelection.getCodigo());
                nombreField.setText(newSelection.getNombre());
            }
        });

        // Configurar botones
        nuevoButton.setOnAction(event -> handleNuevo());
        abrirButton.setOnAction(event -> handleAbrir());
        eliminarButton.setOnAction(event -> handleEliminar());
        salirButton.setOnAction(event -> handleSalir());
    }

    private void handleNuevo() {
        // Agregar un nuevo artículo a la lista
        String codigo = codigoField.getText().trim();
        String nombre = nombreField.getText().trim();

        if (!codigo.isEmpty() && !nombre.isEmpty()) {
            articulosList.add(new Articulo(codigo, nombre));
            codigoField.clear();
            nombreField.clear();
        } else {
            showAlert("Error", "Debe completar los campos Código y Nombre.");
        }
    }

    private void handleAbrir() {
        // De momento, no hace nada
        System.out.println("Abrir presionado");
    }

    private void handleEliminar() {
        // Eliminar el artículo seleccionado
        Articulo selectedArticulo = articulosTable.getSelectionModel().getSelectedItem();
        if (selectedArticulo != null) {
            articulosList.remove(selectedArticulo);
        } else {
            showAlert("Error", "Debe seleccionar un artículo para eliminar.");
        }
    }

    private void handleSalir() {
        // Cerrar la ventana actual
        Stage stage = (Stage) salirButton.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


