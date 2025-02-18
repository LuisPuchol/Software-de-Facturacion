package com.luis.facturacion.mvc_mainmenu;

import com.luis.facturacion.AppController;
import com.luis.facturacion.mvc_articulo.ArticulosView;
import com.luis.facturacion.mvc_client.ClienteView;
import com.luis.facturacion.mvc_familiaArticulos.FamiliaArticulosView;
import javafx.event.ActionEvent;
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
        appController.showArticuloView();
    }

    @FXML
    public void handleFamiliaArticulosClick(ActionEvent actionEvent) {
        System.out.println("El menú 'Familia Articulos ' fue seleccionado");
        appController.showFamiliaArticulosView();
    }

    @FXML
    public void handleClientesClick(javafx.event.ActionEvent actionEvent) {
        System.out.println("El menú 'Clientes' fue seleccionado.");
        appController.showClienteView();
    }

    @FXML
    public void handleConfigIVAClick(javafx.event.ActionEvent actionEvent) {
        System.out.println("El menú 'Configuración IVA' fue seleccionado.");
    }


    public void handleFormasPagoClick(ActionEvent actionEvent) {
    }
}
