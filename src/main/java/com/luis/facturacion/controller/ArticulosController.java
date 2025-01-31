package com.luis.facturacion.controller;

//package com.luis.facturacion.controller;

import com.luis.facturacion.view.Articulo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ArticulosController {
    private AppController appController;
    private MainMenuController mainMenuController;

    @FXML
    private TableColumn<Articulo, String> codigoColumn;

    @FXML
    private TableColumn<Articulo, String> nombreColumn;

    @FXML
    private TextField codigoField;

    @FXML
    private TextField nombreField;

    @FXML
    private Button abrirButton;



    private final ObservableList<Articulo> articulosList = FXCollections.observableArrayList();

    @FXML
    private TableView<Articulo> articulosTable;
    @FXML
    private TableColumn<Articulo, Integer> idArticuloColumn;
    @FXML
    private TableColumn<Articulo, String> codigoArticuloColumn;
    @FXML
    private TextField idArticuloField;
    @FXML
    private TextField codigoArticuloField;
    @FXML
    private TextField codigoBarrasField;
    @FXML
    private TextField descripcionField;
    @FXML
    private TextField familiaField;
    @FXML
    private TextField costeField;
    @FXML
    private TextField margenComercialField;
    @FXML
    private TextField pvpField;
    @FXML
    private TextField proveedorField;
    @FXML
    private TextField stockField;
    @FXML
    private TextArea observacionesField;
    @FXML
    private Button nuevoButton;
    @FXML
    private Button editarButton;
    @FXML
    private Button eliminarButton;
    @FXML
    private Button salirButton;

    public ArticulosController() {
        System.out.println("Articulos Controller created");
    }

    public void setMainMenuController(MainMenuController mainMenuController) {
        this.mainMenuController = mainMenuController;
    }

    /*
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
     */
    @FXML
    public void initialize() {
        // Inicializar columnas de la tabla
        idArticuloColumn.setCellValueFactory(cellData -> cellData.getValue().idArticuloProperty().asObject());
        codigoArticuloColumn.setCellValueFactory(cellData -> cellData.getValue().codigoArticuloProperty());

        // Listener para mostrar los datos al seleccionar un artículo
        articulosTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                cargarDatosArticulo(newSelection);
            }
        });
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


    private void cargarDatosArticulo(Articulo articulo) {
        idArticuloField.setText(String.valueOf(articulo.getIdArticulo()));
        codigoArticuloField.setText(articulo.getCodigoArticulo());
        codigoBarrasField.setText(articulo.getCodigoBarrasArticulo());
        descripcionField.setText(articulo.getDescripcionArticulo());
        familiaField.setText(String.valueOf(articulo.getFamiliaArticulo()));
        costeField.setText(String.valueOf(articulo.getCosteArticulo()));
        margenComercialField.setText(String.valueOf(articulo.getMargenComercialArticulo()));
        pvpField.setText(String.valueOf(articulo.getPvpArticulo()));
        proveedorField.setText(String.valueOf(articulo.getProveedorArticulo()));
        stockField.setText(String.valueOf(articulo.getStockArticulo()));
        observacionesField.setText(articulo.getObservacionesArticulo());
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

    @FXML
    private void handleNuevoButton() {
        limpiarFormulario();
    }

    @FXML
    private void handleEditarButton() {
        // Lógica para editar un artículo
    }

    @FXML
    private void handleEliminarButton() {
        // Lógica para eliminar un artículo
    }

    @FXML
    private void handleSalirButton() {
        // Cerrar la ventana actual
        salirButton.getScene().getWindow().hide();
    }

    private void limpiarFormulario() {
        idArticuloField.clear();
        codigoArticuloField.clear();
        codigoBarrasField.clear();
        descripcionField.clear();
        familiaField.clear();
        costeField.clear();
        margenComercialField.clear();
        pvpField.clear();
        proveedorField.clear();
        stockField.clear();
        observacionesField.clear();
    }
}


