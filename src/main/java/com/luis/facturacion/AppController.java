package com.luis.facturacion;

import com.luis.facturacion.mvc_deliveryNote.DeliveryNoteController;
import com.luis.facturacion.mvc_article.ArticleController;
import com.luis.facturacion.mvc_client.ClientController;
import com.luis.facturacion.mvc_invoice.InvoiceController;
import com.luis.facturacion.mvc_login.LoginController;
import com.luis.facturacion.mvc_mainmenu.MainMenuController;
import com.luis.facturacion.mvc_correctiveInvoice.CorrectiveInvoiceController;
import com.luis.facturacion.mvc_vatConfig.VATConfigController;
import javafx.stage.Stage;

public class AppController {
    private Stage primaryStage;
    private ViewLoader viewLoader;

    private final LoginController loginController;
    private final MainMenuController mainMenuController;
    private final ArticleController articleController;
    private final VATConfigController tiposDeIvaController;
    private final ClientController clientController;
    private final DeliveryNoteController deliveryNoteController;
    private final InvoiceController invoiceController;
    private final CorrectiveInvoiceController correctiveInvoiceController;

    public AppController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.viewLoader = new ViewLoader();

        // Start Controllers
        this.loginController = new LoginController();
        this.mainMenuController = new MainMenuController();
        this.articleController = new ArticleController();
        this.tiposDeIvaController = new VATConfigController();
        this.clientController = new ClientController();
        this.deliveryNoteController = new DeliveryNoteController();
        this.invoiceController = new InvoiceController();
        this.correctiveInvoiceController = new CorrectiveInvoiceController();

        // Conect Controllers to AppController
        this.loginController.setAppController(this);
        this.mainMenuController.setAppController(this);
        this.articleController.setAppController(this);
        this.tiposDeIvaController.setAppController(this);
        this.clientController.setAppController(this);
        this.deliveryNoteController.setAppController(this);
        this.invoiceController.setAppController(this);
        this.correctiveInvoiceController.setAppController(this);
    }

    public void showLoginView() {
        viewLoader.loadView("/com/luis/facturacion/loginMenu.fxml", LoginController.class, loginController, primaryStage, "Login");
    }

    public void showMainMenuView() {
        viewLoader.loadView("/com/luis/facturacion/mainMenu.fxml", MainMenuController.class, mainMenuController, primaryStage, "MainMenu");
    }

    public void showArticuloView(){
        viewLoader.loadViewInNewStage("/com/luis/facturacion/articles.fxml", ArticleController.class, articleController, "Listado Artículos");
    }

    public void showTipoDeIvaView() {
        viewLoader.loadViewInNewStage("/com/luis/facturacion/vatConfig.fxml", VATConfigController.class, tiposDeIvaController, "Configuración de IVA");
    }

    public void showClienteView() {
        viewLoader.loadViewInNewStage("/com/luis/facturacion/clients.fxml", ClientController.class, clientController, "Listado Clientes");
    }

    public void showAlbaranView() {
        viewLoader.loadViewInNewStage("/com/luis/facturacion/deliveryNote.fxml", DeliveryNoteController.class, deliveryNoteController, "Albaran");
    }

    public void showFacturaView() {
        viewLoader.loadViewInNewStage("/com/luis/facturacion/InvoiceList.fxml", InvoiceController.class, invoiceController, "Factura");
    }

    public void showRectificativaView() {

    }

    public void showListadoFacturasView() {

    }



    public Stage getStage () {
        return this.primaryStage;
    }

    public String getClienteByID(Integer id) {
        return clientController.getClienteById(id);
    }

    public String getProductByID(Integer id) {
        return articleController.getProductByID(id);
    }

}
