import com.luis.facturacion.AppController;
import com.luis.facturacion.mvc_client.database.ClientDAO;
import com.luis.facturacion.mvc_deliveryNote.DeliveryNoteEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Controller for the Delivery Note List view.
 * Handles displaying a list of delivery notes based on date filters.
 */
public class DeliveryNoteListController {

    private AppController appController;
    private DeliveryNoteListModel model;
    private ObservableList<DeliveryNoteEntity> deliveryNoteItems = FXCollections.observableArrayList();

    @FXML
    private DatePicker fromDateField, toDateField;

    @FXML
    private CheckBox includeInvoicesCheck;

    @FXML
    private Button searchButton, exitButton;

    @FXML
    private TableView<DeliveryNoteEntity> deliveryNotesTable;

    @FXML
    private TableColumn<DeliveryNoteEntity, String> dateColumn, deliveryNoteNumberColumn,
            codeColumn, clientNameColumn, amountColumn, invoiceNumberColumn;

    /**
     * Constructor for the controller.
     */
    public DeliveryNoteListController() {
        System.out.println("Delivery-Note List Controller created");
        this.model = DeliveryNoteListModel.getInstance();
    }

    /**
     * Sets the application controller and registers this controller with the model.
     */
    public void setAppController(AppController appController) {
        this.appController = appController;
        model.setController(this);
    }

    /**
     * Initializes UI components and configures the table.
     */
    @FXML
    public void initialize() {
        setupTableColumns();
        setupInitialDates();
    }

    /**
     * Sets up the table columns with property value factories.
     */
    private void setupTableColumns() {
        // Configure standard columns using PropertyValueFactory
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("formattedDate"));
        deliveryNoteNumberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        invoiceNumberColumn.setCellValueFactory(new PropertyValueFactory<>("invoiceNumber"));

        // Custom cell factory for client name (since it's not directly in DeliveryNoteEntity)
        clientNameColumn.setCellValueFactory(cellData -> {
            int clientId = cellData.getValue().getClientId();
            String clientName = ClientDAO.getInstance().getNameById(clientId);
            return javafx.beans.binding.Bindings.createStringBinding(() -> clientName);
        });

        // Center align column headers
        List<TableColumn<DeliveryNoteEntity, ?>> columns = List.of(
                dateColumn, deliveryNoteNumberColumn, codeColumn,
                clientNameColumn, amountColumn, invoiceNumberColumn
        );

        columns.forEach(column ->
                column.setStyle("-fx-alignment: CENTER;")
        );

        deliveryNotesTable.setItems(deliveryNoteItems);
    }

    /**
     * Sets up initial date values for the date pickers.
     */
    private void setupInitialDates() {
        // Set default dates: From = first day of current month, To = current date
        LocalDate now = LocalDate.now();
        LocalDate firstDayOfMonth = LocalDate.of(now.getYear(), now.getMonth(), 1);

        fromDateField.setValue(firstDayOfMonth);
        toDateField.setValue(now);
    }

    /**
     * Handles the search button action.
     * Retrieves delivery notes based on the date range and filter options.
     */
    @FXML
    public void handleSearch(ActionEvent actionEvent) {
        LocalDate fromDate = fromDateField.getValue();
        LocalDate toDate = toDateField.getValue();
        boolean includeInvoices = includeInvoicesCheck.isSelected();

        if (fromDate == null || toDate == null) {
            showAlert("Error", "Por favor, seleccione un rango de fechas válido.");
            return;
        }

        if (fromDate.isAfter(toDate)) {
            showAlert("Error", "La fecha 'Desde' no puede ser posterior a la fecha 'Hasta'.");
            return;
        }

        // Use the model to get delivery notes and update the table
        loadDeliveryNotes(fromDate, toDate, includeInvoices);
    }

    /**
     * Loads delivery notes from the model based on specified criteria.
     */
    private void loadDeliveryNotes(LocalDate fromDate, LocalDate toDate, boolean includeInvoices) {
        // Clear previous items
        deliveryNoteItems.clear();

        // Get delivery notes from the model
        List<DeliveryNoteEntity> notes = model.getDeliveryNotesByDateRange(fromDate, toDate, includeInvoices);

        // Add to observable list
        deliveryNoteItems.addAll(notes);

        // Show message if no items found
        if (deliveryNoteItems.isEmpty()) {
            showAlert("Información", "No se encontraron albaranes para el período seleccionado.");
        }
    }

    /**
     * Displays an alert dialog with the specified title and message.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Closes the list window.
     */
    @FXML
    public void handleExit(ActionEvent actionEvent) {
        exitButton.getScene().getWindow().hide();
    }
}