package com.luis.facturacion;

import com.luis.facturacion.mvc_deliveryNote.DeliveryNoteController;
import com.luis.facturacion.mvc_article.ArticleController;
import com.luis.facturacion.mvc_client.ClientController;
import com.luis.facturacion.mvc_deliveryNoteList.DeliveryNoteListController;
import com.luis.facturacion.mvc_invoice.InvoiceController;
import com.luis.facturacion.mvc_invoiceList.InvoiceListController;
import com.luis.facturacion.mvc_login.LoginController;
import com.luis.facturacion.mvc_mainmenu.MainMenuController;
import com.luis.facturacion.mvc_correctiveInvoice.CorrectiveInvoiceController;
import com.luis.facturacion.mvc_vatConfig.VATConfigController;
import com.luis.facturacion.utils.ViewLoader;
import javafx.stage.Stage;

public class AppController {
    private Stage primaryStage;
    private ViewLoader viewLoader;
    private final LoginController loginController;
    private final MainMenuController mainMenuController;
    private final ArticleController articleController;
    private final VATConfigController vatConfigController;
    private final ClientController clientController;
    private final DeliveryNoteController deliveryNoteController;
    private final DeliveryNoteListController deliveryNoteListController;
    private final InvoiceController invoiceController;
    private final InvoiceListController invoiceListController;
    private final CorrectiveInvoiceController correctiveInvoiceController;

    public AppController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.viewLoader = new ViewLoader();

        // Start Controllers
        this.loginController = new LoginController();
        this.mainMenuController = new MainMenuController();
        this.articleController = new ArticleController();
        this.vatConfigController = new VATConfigController();
        this.clientController = new ClientController();
        this.deliveryNoteController = new DeliveryNoteController();
        this.deliveryNoteListController = new DeliveryNoteListController();
        this.invoiceController = new InvoiceController();
        this.invoiceListController = new InvoiceListController();
        this.correctiveInvoiceController = new CorrectiveInvoiceController();

        // Conect Controllers to AppController
        this.loginController.setAppController(this);
        this.mainMenuController.setAppController(this);
        this.articleController.setAppController(this);
        this.vatConfigController.setAppController(this);
        this.clientController.setAppController(this);
        this.deliveryNoteController.setAppController(this);
        this.deliveryNoteListController.setAppController(this);
        this.invoiceController.setAppController(this);
        this.invoiceListController.setAppController(this);
        this.correctiveInvoiceController.setAppController(this);
    }

    public void showLoginView() {
        viewLoader.loadView("/com/luis/facturacion/loginMenu.fxml", LoginController.class, loginController, primaryStage, "Login");
    }

    public void showMainMenuView() {
        viewLoader.loadView("/com/luis/facturacion/mainMenu.fxml", MainMenuController.class, mainMenuController, primaryStage, "Menú Principal");
    }

    public void showArticleView(){
        viewLoader.loadViewInNewStage("/com/luis/facturacion/articles.fxml", ArticleController.class, articleController, "Listado Artículos");
    }

    public void showClientView() {
        viewLoader.loadViewInNewStage("/com/luis/facturacion/clients.fxml", ClientController.class, clientController, "Listado Clientes");
    }

    public void showDeliveryNoteView() {
        viewLoader.loadViewInNewStage("/com/luis/facturacion/deliveryNote.fxml", DeliveryNoteController.class, deliveryNoteController, "Albaran");
    }

    public void showDeliveryNoteListView() {
        viewLoader.loadViewInNewStage("/com/luis/facturacion/deliveryNoteList.fxml", DeliveryNoteListController.class, deliveryNoteListController, "Listado Albaranes");
    }

    public void showInvoiceView() {
        viewLoader.loadViewInNewStage("/com/luis/facturacion/invoice.fxml", InvoiceController.class, invoiceController, "Listado a Facturar");
    }

    public void showInvoiceListView() {
        viewLoader.loadViewInNewStage("/com/luis/facturacion/invoiceList.fxml", InvoiceListController.class, invoiceListController, "Listado de facturas");
    }

    public void showVatConfigView() {
        viewLoader.loadViewInNewStage("/com/luis/facturacion/vatConfig.fxml", VATConfigController.class, vatConfigController, "Configuración de IVA");
    }

    public void showCorrectiveInvoiceView() {

    }

    public Stage getStage () {
        return this.primaryStage;
    }


    /**
     * Creates an invoice for a specific client using the InvoiceController
     * This delegates the invoice creation to the proper controller
     *
     * @param clientId The ID of the client to create invoice for
     * @return The ID of the created invoice, or null if creation failed
     */
    public Integer createInvoiceForClient(Integer clientId) {
        try {
            return invoiceController.createInvoiceForClient(clientId);
        } catch (Exception e) {
            System.err.println("Error creating invoice from AppController: " + e.getMessage());
            return null;
        }
    }
}
