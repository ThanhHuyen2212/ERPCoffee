package App.Depot.Controller;

import App.Depot.Model.ProductPreparationModel;
import App.Depot.View.MessageDialog;
import App.ModuleManager.AppControl;
import Entity.Product;
import Logic.Depot.ProductPreparationManagement;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import static App.Depot.Controller.IngredientManagementController.messageInvalidNumber;
import static Main.MainApp.APP;

public class ProductPreparationController implements Initializable {
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, Integer> idCol;
    @FXML
    private TableColumn<Product, String> productCol;
    @FXML
    private TableColumn<Product, Integer> qtyCol;
    @FXML
    private TableColumn<Product, String> availableCol;
    @FXML
    private TableColumn<Product, String> productQtyCol;
    @FXML
    private Button clearBtn;
    @FXML
    private Button confirmBtn;
    @FXML
    private Button autofillBtn;

    private ProductPreparationModel model;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new ProductPreparationModel();

        idCol.setCellValueFactory(new PropertyValueFactory<>("productId"));
        productCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        qtyCol.setCellValueFactory(data -> new SimpleIntegerProperty(
                model.getLogic().getPreparations().get(data.getValue()) == null
                        ? 0
                        : model.getLogic().getPreparations().get(data.getValue())
        ).asObject());
        productQtyCol.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getRecipe().getProductQty() + " units / batch"
        ));
        availableCol.setCellValueFactory(data -> new SimpleStringProperty(
                ProductPreparationManagement.preparedList.get(data.getValue().getProductId()) == null
                        ? String.valueOf(0)
                        : ProductPreparationManagement.preparedList.get(data.getValue().getProductId()) + " units"
        ));

        init();

        APP.getPOSButton("manufacturing").setOnAction(e -> {
            productTable.refresh();
        });
    }

    public void init() {
        model.init();
        productTable.setItems(model.getProducts());

        editableCols();
        handleActionBtn();
    }

    public void editableCols() {
        qtyCol.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>() {
            @Override
            public String toString(Integer integer) {
                return String.valueOf(integer);
            }
            @Override
            public Integer fromString(String s) {
                int value = 0;
//                int value = model.getLogic().getPreparations().get(productTable.getSelectionModel().getSelectedItem());
                try {
                    value = Integer.parseInt(s);
                    if(value < 0) {
                        throw new Exception();
                    }
                } catch (Exception e) {
                    AppControl.showAlert("error", messageInvalidNumber);
                }
                return value;
            }
        }));

        qtyCol.setOnEditCommit(e -> {
            model.getLogic().addPreparation(
                    e.getTableView().getItems().get(e.getTablePosition().getRow()),
                    e.getNewValue()
            );
        });

        productTable.setEditable(true);
    }

    public void handleActionBtn() {

        EventHandler<ActionEvent> buttonAutoFillHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                model.handleFillQty();
                productTable.refresh();
            }
        };

        EventHandler<ActionEvent> buttonConfirmHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                model.handlePrepare();
                productTable.refresh();
            }
        };

        EventHandler<ActionEvent> buttonClearHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int rs = MessageDialog.showAlert(
                        "Warning", "Bạn xóa tất cả giá trị hiện tại?" +
                                "\n\nCác thay đổi sẽ mất nếu bạn không lưu.");
                if (rs == 1) {
                    model.getLogic().setPreparations(new HashMap<>());
                    productTable.refresh();
                }
            }
        };

        autofillBtn.setOnAction(buttonAutoFillHandler);
        confirmBtn.setOnAction(buttonConfirmHandler);
        clearBtn.setOnAction(buttonClearHandler);
    }
}
