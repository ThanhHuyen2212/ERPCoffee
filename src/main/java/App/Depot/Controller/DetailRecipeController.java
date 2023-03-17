package App.Depot.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import java.awt.*;

public class DetailRecipeController {
    @FXML
    private Button addBtn;

    @FXML
    private TextField componentTxf;

    @FXML
    private TableColumn<?, ?> componentCol;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button editBtn;

    @FXML
    private TableColumn<?, ?> idCol;

    @FXML
    private TableView<?> detailTable;

    @FXML
    private Label productLbl;

    @FXML
    private TableColumn<?, ?> qtyCol;

    @FXML
    private TextField qtyComponentTxf;

    @FXML
    private TextField qtyProductTxf;

    @FXML
    private Button saveBtn;

    @FXML
    private TableColumn<?, ?> unitCol;
}
