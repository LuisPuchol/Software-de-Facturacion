package com.luis.facturacion.mvc_mainmenu;

import com.luis.facturacion.AppController;
import com.luis.facturacion.mvc_articulo.ArticulosView;
import com.luis.facturacion.mvc_client.ClienteView;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class MainMenuController {
    private AppController appController;
    private MainMenuController mainMenuController;
    private Stage primaryStage;

    public MainMenuController() {
        System.out.println("MainMenu Controller created");
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    @FXML
    public void handleArticulosClick(javafx.event.ActionEvent actionEvent) {
        System.out.println("El menú 'Articulos' fue seleccionado.");
        this.showArticuloView();
    }

    public void showArticuloView() {
        Stage stage = new Stage();
        ArticulosView articulosView = new ArticulosView(this); // Crear la vista del login
        articulosView.show(stage); // Mostrarla en el escenario principal
    }

    @FXML
    public void handleClientesClick(javafx.event.ActionEvent actionEvent) {
        System.out.println("El menú 'Clientes' fue seleccionado.");
        this.showClienteView();
    }

    public void showClienteView() {
        Stage stage = new Stage();
        ClienteView clienteView = new ClienteView(); // Crear la vista del login
        clienteView.show(stage); // Mostrarla en el escenario principal
    }

    @FXML
    public void handleConfigIVAClick(javafx.event.ActionEvent actionEvent) {
        System.out.println("El menú 'Configuración IVA' fue seleccionado.");
    }

}
