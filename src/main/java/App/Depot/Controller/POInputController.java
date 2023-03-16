package App.Depot.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.awt.*;

public class POInputController {
    @FXML
    private Button addBtn;

    @FXML
    private Button confirmBtn;

    @FXML
    private TextField dateLbl;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button editBtn;

    @FXML
    private TableColumn<?, ?> idCol;

    @FXML
    private TextField idLbl;

    @FXML
    private ComboBox<?> ingredientCb;

    @FXML
    private TableColumn<?, ?> ingredientNameCol;

    @FXML
    private TableView<?> ingredientTable;

    @FXML
    private TableColumn<?, ?> priceCol;

    @FXML
    private TableColumn<?, ?> qtyCol;

    @FXML
    private TextField qtyTxf;

    @FXML
    private TextField staffConfirmTxf;

    @FXML
    private TextField staffCreateTxf;

    @FXML
    private Label totalLbl;

    @FXML
    private TableColumn<?, ?> typeCol;

    @FXML
    private TextField vendorTxf;
}
