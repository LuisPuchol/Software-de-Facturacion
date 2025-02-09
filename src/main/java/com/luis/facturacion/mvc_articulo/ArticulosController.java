package com.luis.facturacion.mvc_articulo;

//package com.luis.facturacion.controller;

import com.luis.facturacion.mvc_articulo.database.entitiesfx.ArticuloFX;
import com.luis.facturacion.mvc_mainmenu.MainMenuController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ArticulosController {
    private final ArticuloModel articuloModel = ArticuloModel.getInstance(); // Singleton del modelo
    private MainMenuController mainMenuController;
    @FXML
    private TableView<ArticuloFX> articulosTable;
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
     */

    @FXML
    public void initialize() {
        articulosTable.setItems(articuloModel.getListaArticulos());
        articuloModel.cargarArticulos();

        /*
                // Inicializar columnas de la tabla
        idArticuloColumn.setCellValueFactory(cellData -> cellData.getValue().idArticuloProperty().asObject());
        codigoArticuloColumn.setCellValueFactory(cellData -> cellData.getValue().codigoArticuloProperty());

        // Listener para mostrar los datos al seleccionar un artículo
        articulosTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                cargarDatosArticulo(newSelection);
            }
        });
         */
    }

    @FXML
    private void handleNuevoButton() {
        //añadir a la BBDD y limpiar los campos
        articuloModel.agregarArticuloDesdeFormulario(
                codigoArticuloField.getText(),
                codigoBarrasField.getText(),
                descripcionField.getText(),
                familiaField.getText(),
                costeField.getText(),
                margenComercialField.getText(),
                pvpField.getText(),
                proveedorField.getText(),
                stockField.getText(),
                observacionesField.getText()
        );
        limpiarFormulario();
    }

    @FXML
    private void handleEditarButton() {
        //
    }

    @FXML
    private void handleEliminarButton() {
        articuloModel.eliminarArticuloSeleccionado(articulosTable.getSelectionModel().getSelectedItem());
    }


    @FXML
    private void handleSalirButton() {
        // Cerrar la ventana actual
        salirButton.getScene().getWindow().hide();
    }

    private void handleSalir() {
        // Cerrar la ventana actual
        Stage stage = (Stage) salirButton.getScene().getWindow();
        stage.close();
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


