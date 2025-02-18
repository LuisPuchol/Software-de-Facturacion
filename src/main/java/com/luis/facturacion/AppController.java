package com.luis.facturacion;

import com.luis.facturacion.mvc_articulo.ArticulosController;
import com.luis.facturacion.mvc_articulo.ArticulosView;
import com.luis.facturacion.mvc_client.ClienteView;
import com.luis.facturacion.mvc_familiaArticulos.FamiliaArticulosController;
import com.luis.facturacion.mvc_familiaArticulos.FamiliaArticulosView;
import com.luis.facturacion.mvc_login.LoginView;
import com.luis.facturacion.mvc_mainmenu.MainMenuView;
import javafx.stage.Stage;

public class AppController {
    private Stage primaryStage;
    // 🔥 Agregar todas las instancias de los controladores y modelos
    private final ArticulosController articulosController;
    private final FamiliaArticulosController familiaArticulosController;
    //private final ProveedoresController proveedoresController;
    //private final FacturasController facturasController;
    //private final AlbaranesController albaranesController;
    //private final ConfigIvaController configIvaController;
    //private final FormasPagoController formasPagoController;
    //private final FacturasRectificativasController facturasRectificativasController;

    public AppController(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // Inicializar todos los controladores desde el inicio
        this.articulosController = new ArticulosController();
        this.familiaArticulosController = new FamiliaArticulosController();
        //this.proveedoresController = new ProveedoresController();
        //this.facturasController = new FacturasController();
        //this.albaranesController = new AlbaranesController();
        //this.configIvaController = new ConfigIvaController();
        //this.formasPagoController = new FormasPagoController();
        //this.facturasRectificativasController = new FacturasRectificativasController();

        // Conectar los controladores con AppController
        this.articulosController.setAppController(this);
        this.familiaArticulosController.setAppController(this);
        //this.proveedoresController.setAppController(this);
        //this.facturasController.setAppController(this);
        //this.albaranesController.setAppController(this);
        //this.configIvaController.setAppController(this);
        //this.formasPagoController.setAppController(this);
        //this.facturasRectificativasController.setAppController(this);
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
        ClienteView clienteView = new ClienteView();
        clienteView.show(stage);
    }

    public void showFamiliaArticulosView(){
        Stage stage = new Stage();
        FamiliaArticulosView familiaArticulosView = new FamiliaArticulosView(this);
        familiaArticulosView.show(stage);
    }

    public Stage getStage () {
        return this.primaryStage;
    }

    public Object getFamilyById(Integer id){
        return familiaArticulosController.getFamilyById(id);
    }
}
