package App.Depot.Controller;

import Entity.Ingredient;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class IngredientManagementController implements Initializable {

    @FXML
    private TableView<Ingredient> ingredientTable;
    @FXML
    private TableColumn<Ingredient, ?> idCol;
    @FXML
    private TableColumn<Ingredient, ?> nameCol;
    @FXML
    private TableColumn<Ingredient, ?> typeCol;
    @FXML
    private TableColumn<Ingredient, ?> storageCol;
    @FXML
    private TableColumn<Ingredient, ?> limitCol;
    @FXML
    private TableColumn<Ingredient, ?> priceCol;
    @FXML
    private TextField nameTxf;
    @FXML
    private TextField limitTxf;
    @FXML
    private TextField typeTxf;
    @FXML
    private Label createdDateLbl;
    @FXML
    private Label deletedDateLbl;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button addBtn;
    @FXML
    private Button editBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idCol.setCellValueFactory(new PropertyValueFactory<>("ingredientId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("ingredientName"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("ingredientType"));
        storageCol.setCellValueFactory(new PropertyValueFactory<>("ingredientStorage"));
        limitCol.setCellValueFactory(new PropertyValueFactory<>("ingredientLimit"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

}
