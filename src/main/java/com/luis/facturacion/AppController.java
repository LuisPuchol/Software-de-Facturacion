package com.luis.facturacion;

import com.luis.facturacion.mvc_factura.FacturasView;
import com.luis.facturacion.mvc_formaPago.FormaDePagoController;
import com.luis.facturacion.mvc_articulo.ArticuloController;
import com.luis.facturacion.mvc_articulo.ArticulosView;
import com.luis.facturacion.mvc_client.ClienteController;
import com.luis.facturacion.mvc_client.ClientesView;
import com.luis.facturacion.mvc_factura.FacturaController;
import com.luis.facturacion.mvc_familiaArticulos.FamiliaArticulosController;
import com.luis.facturacion.mvc_familiaArticulos.FamiliaArticulosView;
import com.luis.facturacion.mvc_formaPago.FormasDePagoView;
import com.luis.facturacion.mvc_login.LoginView;
import com.luis.facturacion.mvc_mainmenu.MainMenuView;
import com.luis.facturacion.mvc_rectificativa.RectificativaController;
import com.luis.facturacion.mvc_rectificativa.RectificativasView;
import com.luis.facturacion.mvc_tipoIva.TipoDeIvaController;
import com.luis.facturacion.mvc_tipoIva.TiposDeIvaView;
import javafx.stage.Stage;

public class AppController {
    private Stage primaryStage;
    // Agregar todas las instancias de los controladores y modelos
    private final ArticuloController articuloController;
    private final FamiliaArticulosController familiaArticulosController;
    private final TipoDeIvaController tiposDeIvaController;
    private final FormaDePagoController formasPagoController;
    private final ClienteController clienteController;
    private final FacturaController facturaController;
    private final RectificativaController rectificativaController;

    public AppController(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // Inicializar todos los controladores desde el inicio
        this.articuloController = new ArticuloController();
        this.familiaArticulosController = new FamiliaArticulosController();
        this.tiposDeIvaController = new TipoDeIvaController();
        this.formasPagoController = new FormaDePagoController();
        this.clienteController = new ClienteController();
        this.facturaController = new FacturaController();
        this.rectificativaController = new RectificativaController();

        // Conectar los controladores con AppController
        this.articuloController.setAppController(this);
        this.familiaArticulosController.setAppController(this);
        this.tiposDeIvaController.setAppController(this);
        this.formasPagoController.setAppController(this);
        this.clienteController.setAppController(this);
        this.facturaController.setAppController(this);
        this.rectificativaController.setAppController(this);
    }

    public void showLoginView() {
        LoginView loginView = new LoginView(this); // Crear la vista del login
        loginView.show(primaryStage); // Mostrarla en el escenario principal
    }

    public void showMainMenuView() {
        MainMenuView mainMenuView = new MainMenuView(this); // Crear la vista del menú principal
        mainMenuView.show(primaryStage); // Mostrarla en el escenario principal
    }

    public void showArticuloView(){
        Stage stage = new Stage();
        ArticulosView articulosView = new ArticulosView(this);
        articulosView.show(stage);
    }

    public void showClienteView() {
        Stage stage = new Stage();
        ClientesView clientesView = new ClientesView(this);
        clientesView.show(stage);
    }

    public void showFamiliaArticulosView(){
        Stage stage = new Stage();
        FamiliaArticulosView familiaArticulosView = new FamiliaArticulosView(this);
        familiaArticulosView.show(stage);
    }

    public void showTipoDeIvaView() {
        Stage stage = new Stage();
        TiposDeIvaView tipoDeIvaView = new TiposDeIvaView(this);
        tipoDeIvaView.show(stage);
    }

    public void showFormaDePagoView() {
        Stage stage = new Stage();
        FormasDePagoView formaDePagoView = new FormasDePagoView(this);
        formaDePagoView.show(stage);
    }

    public void showFacturaView() {
        Stage stage = new Stage();
        FacturasView facturaView = new FacturasView(this);
        facturaView.show(stage);
    }

    public void showRectificativaView() {
        Stage stage = new Stage();
        RectificativasView rectificativaView = new RectificativasView(this);
        rectificativaView.show(stage);
    }


    public Stage getStage () {
        return this.primaryStage;
    }

    public Object getFamilyById(Integer id){
        return familiaArticulosController.getFamilyById(id);
    }
}
