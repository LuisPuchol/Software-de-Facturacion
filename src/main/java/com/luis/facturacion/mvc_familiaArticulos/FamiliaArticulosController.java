package com.luis.facturacion.mvc_familiaArticulos;

import com.luis.facturacion.mvc_familiaArticulos.database.FamiliaArticulosEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class FamiliaArticulosController {
    private final FamiliaArticulosModel familiaModel = FamiliaArticulosModel.getInstance();
    @FXML
    private TableView<FamiliaArticulosEntity> familiaTable;
    @FXML
    private TableColumn<FamiliaArticulosEntity, Integer> columnId;
    @FXML
    private TableColumn<FamiliaArticulosEntity, String> columnName;
    @FXML
    private TableColumn<FamiliaArticulosEntity, String> columnFamily;
    @FXML
    private TextField idArticuloField, codigoFamiliaField, denominacionFamiliaField;
    @FXML
    private Button nuevoButton, editarButton, eliminarButton, salirButton;

    public FamiliaArticulosController() {
        System.out.println("Familia Articulos created");
    }

    @FXML
    public void initialize() {
        columnId.setCellValueFactory(new PropertyValueFactory<>("idFamiliaArticulos"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("codigoFamiliaArticulos"));
        columnFamily.setCellValueFactory(new PropertyValueFactory<>("denominacionFamilias"));

        loadData();
    }

    private void loadData() {
        List<FamiliaArticulosEntity> familiaDB = familiaModel.loadFamiliaArticulos();
        ObservableList<FamiliaArticulosEntity> familiaArticulosEntityObservableList = FXCollections.observableArrayList(familiaDB);
        familiaTable.setItems(familiaArticulosEntityObservableList);
    }

    @FXML
    private void handleNuevoButton(MouseEvent mouseEvent) {
        familiaModel.addFamiliaArticulo(
                codigoFamiliaField.getText(),
                denominacionFamiliaField.getText()
        );

        cleanFormulary();
        loadData();
    }

    @FXML
    public void handleEditarButton(MouseEvent mouseEvent) {
    }

    @FXML
    public void handleEliminarButton(MouseEvent mouseEvent) {

    }

    @FXML
    public void handleSalirButton(MouseEvent mouseEvent) {
        salirButton.getScene().getWindow().hide();
    }


    private void cleanFormulary(){
        idArticuloField.clear();
        codigoFamiliaField.clear();
        denominacionFamiliaField.clear();
    }
}
