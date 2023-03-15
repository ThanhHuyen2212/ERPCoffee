package App.Depot.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class DetailPOController {
    @FXML
    private Button cancelBtn;

    @FXML
    private Button confirmBtn;

    @FXML
    private TableColumn<?, ?> idCol;

    @FXML
    private TableColumn<?, ?> ingredientNameCol;

    @FXML
    private TableView<?> detailTable;

    @FXML
    private TableColumn<?, ?> priceCol;

    @FXML
    private Button printBtn;

    @FXML
    private TableColumn<?, ?> qtyCol;

    @FXML
    private TableColumn<?, ?> receiveCol;
}
