package App.Depot.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RecipeManagementController {
    @FXML
    private Button addBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private TableColumn<?, ?> idCol;

    @FXML
    private TableView<?> recipeTable;

    @FXML
    private TableColumn<?, ?> productNameCol;

    @FXML
    private TableColumn<?, ?> qtyCol;

    @FXML
    private Label searchBtn;

    @FXML
    private TextField searchTxf;

    @FXML
    private TableColumn<?, ?> unitCol;

}
