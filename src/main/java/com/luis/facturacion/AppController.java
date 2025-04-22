package com.luis.facturacion;

import com.luis.facturacion.mvc_factura.FacturasView;
import com.luis.facturacion.mvc_articulo.ArticuloController;
import com.luis.facturacion.mvc_articulo.ArticulosView;
import com.luis.facturacion.mvc_client.ClienteController;
import com.luis.facturacion.mvc_client.ClientesView;
import com.luis.facturacion.mvc_factura.FacturaController;
import com.luis.facturacion.mvc_listadoFacturas.ListadoFacturasView;
import com.luis.facturacion.mvc_login.LoginController;
import com.luis.facturacion.mvc_login.LoginView;
import com.luis.facturacion.mvc_mainmenu.MainMenuController;
import com.luis.facturacion.mvc_mainmenu.MainMenuView;
import com.luis.facturacion.mvc_rectificativa.RectificativaController;
import com.luis.facturacion.mvc_rectificativa.RectificativasView;
import com.luis.facturacion.mvc_tipoIva.TipoDeIvaController;
import com.luis.facturacion.mvc_tipoIva.TiposDeIvaView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppController {
    private Stage primaryStage;
    // Agregar todas las instancias de los controladores y modelos
    private final LoginController loginController;
    private final MainMenuController mainMenuController;
    private final ArticuloController articuloController;
    private final TipoDeIvaController tiposDeIvaController;
    private final ClienteController clienteController;
    //private final FacturaController facturaController;
    //private final RectificativaController rectificativaController;

    public AppController(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // Inicializar todos los controladores desde el inicio
        this.loginController = new LoginController();
        this.mainMenuController = new MainMenuController();
        this.articuloController = new ArticuloController();
        this.tiposDeIvaController = new TipoDeIvaController();
        this.clienteController = new ClienteController();
        //this.facturaController = new FacturaController();
        //this.rectificativaController = new RectificativaController();

        // Conectar los controladores con AppController
        this.loginController.setAppController(this);
        this.mainMenuController.setAppController(this);
        this.articuloController.setAppController(this);
        this.tiposDeIvaController.setAppController(this);
        //this.formasPagoController.setAppController(this);
        //this.clienteController.setAppController(this);
        //this.facturaController.setAppController(this);
        //this.rectificativaController.setAppController(this);
    }

    public void showLoginView() {
        loadFXMLView("/com/luis/facturacion/loginMenu.fxml", LoginController.class, loginController, primaryStage, "Login");
    }

    public void showMainMenuView() {
        loadFXMLView("/com/luis/facturacion/mainMenu.fxml", MainMenuController.class, mainMenuController, primaryStage, "MainMenu");
    }

    public void showArticuloView(){
        Stage stage = new Stage();
        loadFXMLView("/com/luis/facturacion/articulos.fxml", ArticuloController.class, articuloController, stage, "Listado Artículos");
    }

    public void showTipoDeIvaView() {
        Stage stage = new Stage();
        loadFXMLView("/com/luis/facturacion/tipoIva.fxml", TipoDeIvaController.class, tiposDeIvaController, stage, "Configuración de IVA");
    }

    public void showClienteView() {
        Stage stage = new Stage();
        loadFXMLView("/com/luis/facturacion/clientes.fxml", ClienteController.class, clienteController, stage, "Listado Clientes");
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

    public void showListadoFacturasView() {
        Stage stage = new Stage();
        ListadoFacturasView listadoFacturasView = new ListadoFacturasView(this);
        listadoFacturasView.show(stage);
    }

    private <T> void loadFXMLView(String fxmlPath, Class<T> controllerClass, T controller, Stage stage, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));

            loader.setControllerFactory(clazz -> {
                if (clazz == controllerClass) {
                    return controller;
                }
                try {
                    return clazz.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Stage getStage () {
        return this.primaryStage;
    }

    //public String getClienteByID(Integer id) {
    //    return clienteController.getClienteById(id);
    //}

    public String getProductByID(Integer id) {
        return articuloController.getProductByID(id);
    }

}
