package App.Depot.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class POManagementController {
    @FXML
    private Button addBtn;

    @FXML
    private TableColumn<?, ?> buyerCol;

    @FXML
    private TableColumn<?, ?> idCol;

    @FXML
    private TableView<?> ingredientTable;

    @FXML
    private TableColumn<?, ?> orderDateCol;

    @FXML
    private Label searchBtn;

    @FXML
    private TextField searchTxf;

    @FXML
    private TableColumn<?, ?> statusCol;

    @FXML
    private TableColumn<?, ?> totalCol;

    @FXML
    private TableColumn<?, ?> vendorCol;

}
