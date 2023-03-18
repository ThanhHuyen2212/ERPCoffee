package App.Depot.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class InventoryManagementController {
    @FXML
    private TableView<?> historyTable;
    @FXML
    private TableColumn<?, ?> dateCol;

    @FXML
    private TableColumn<?, ?> fromCol;

    @FXML
    private TableColumn<?, ?> productCol;

    @FXML
    private TableColumn<?, ?> qtyCol;

    @FXML
    private TableColumn<?, ?> refCol;

    @FXML
    private TableColumn<?, ?> statusCol;

    @FXML
    private TableColumn<?, ?> unitCol;
}
