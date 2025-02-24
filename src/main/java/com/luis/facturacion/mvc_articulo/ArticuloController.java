package com.luis.facturacion.mvc_articulo;

import com.luis.facturacion.AppController;
import com.luis.facturacion.mvc_articulo.database.ArticuloEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class ArticuloController {
    private ArticuloModel articuloModel;
    private AppController appController;

    @FXML
    private TableView<ArticuloEntity> articulosTable;
    @FXML
    private TableColumn<ArticuloEntity, Integer> columnId;
    @FXML
    private TableColumn<ArticuloEntity, Integer> columnName;

    @FXML
    private TextField idArticuloField, codigoArticuloField, codigoBarrasField, descripcionField, familiaField,
            costeField, margenComercialField, pvpField, proveedorField, stockField;

    @FXML
    private TextArea observacionesField;
    @FXML
    private Button nuevoButton, editarButton, eliminarButton, salirButton;

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
        columnId.setCellValueFactory(new PropertyValueFactory<>("idArticulo"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("descripcionArticulo"));

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
        //limpiarFormulario();
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



    //Cosas que pide el Model
    public Object getFamilyById(Integer id){
        return appController.getFamilyById(id);
    }


}


