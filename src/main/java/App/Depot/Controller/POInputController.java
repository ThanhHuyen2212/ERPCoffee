package App.Depot.Controller;

import App.Depot.Model.POModel;
import Entity.Ingredient;
import Logic.Depot.IngredientManagement;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class POInputController implements Initializable {
    @FXML
    private TableView<Ingredient> ingredientTable;
    @FXML
    private TableColumn<Ingredient, String> ingredientCol;
    @FXML
    private TableColumn<Ingredient, String> priceCol;
    @FXML
    private TableColumn<Ingredient, String> qtyCol;
    @FXML
    private TableColumn<Ingredient, Integer> idCol;
    @FXML
    private TableColumn<Ingredient, ?> typeCol;
    @FXML
    private ComboBox<String> ingredientCb;
    @FXML
    private TextField qtyTxf;
    @FXML
    private TextField staffCreateTxf;
    @FXML
    private Label totalLbl;
    @FXML
    private TextField vendorTxf;
    @FXML
    private Label dateLbl;
    @FXML
    private Label idLbl;
    @FXML
    private Button addBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button confirmBtn;
    @FXML
    private Button cancelBtn;


    private POModel model;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new POModel();

        idCol.setCellValueFactory(new PropertyValueFactory<>("ingredientId"));
        ingredientCol.setCellValueFactory(new PropertyValueFactory<>("ingredientName"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("ingredientType"));
        qtyCol.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(
                model.getCurrent().getDetails().get(data.getValue().getIngredientId()).getOrderQty()
        )));
        priceCol.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(
                (float) (model.getCurrent().getDetails().get(data.getValue().getIngredientId()).getOrderQty()
                        * data.getValue().getPrice())
        )));

        dateLbl.setText(IngredientManagementController.sdf.format(new Date(new java.util.Date().getTime())));
        ingredientCb.setItems(FXCollections.observableArrayList(new IngredientManagement().getNameList()));
    }

    public void init(POModel modelOfSib) {
        this.model = modelOfSib;

        idLbl.setText(String.valueOf(model.getCurrent().getPurchaseOrderId()));
    }

    public void handleActionOnRow() {
        ingredientTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
            }
        });
    }

    public void handleActionBtn() {
        EventHandler<ActionEvent> buttonAddHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        };

        addBtn.setOnAction(buttonAddHandler);
    }
}
