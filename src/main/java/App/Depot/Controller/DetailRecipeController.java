package App.Depot.Controller;

import App.Depot.Model.DetailRecipeModel;
import App.Depot.View.MessageDialog;
import App.ModuleManager.AppControl;
import Entity.Ingredient;
import Entity.Product;
import Logic.Depot.IngredientManagement;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static App.Depot.Controller.IngredientManagementController.messageInvalidNumber;

public class DetailRecipeController implements Initializable {
    @FXML
    private TableView<Ingredient> detailTable;
    @FXML
    private TableColumn<Ingredient, String> idCol;
    @FXML
    private TableColumn<Ingredient, String> componentCol;
    @FXML
    private TableColumn<Ingredient, String> qtyCol;
    @FXML
    private TableColumn<Ingredient, String> unitCol;
    @FXML
    private Label productLbl;
    @FXML
    private TextField qtyComponentTxf;
    @FXML
    private TextField qtyProductTxf;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button editBtn;
    @FXML
    private Button addBtn;
    @FXML
    private Button saveBtn;
    @FXML
    private ComboBox<String> componentCb;
    private DetailRecipeModel model;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new DetailRecipeModel();

        idCol.setCellValueFactory(new PropertyValueFactory<>("ingredientId"));
        componentCol.setCellValueFactory(new PropertyValueFactory<>("ingredientName"));
        qtyCol.setCellValueFactory(data -> new SimpleStringProperty(
                model.getSelected().getRecipe().getIngredientCosts().get(data.getValue()).toString()
        ));
        unitCol.setCellValueFactory(data -> new SimpleStringProperty(
                IngredientManagementController.setupUnit(data.getValue())
        ));

        qtyProductTxf.setFocusTraversable(false);
    }

    public void init(Product selected) {
        model.setSelected(selected);
        detailTable.setItems(FXCollections.observableArrayList(
                model.getSelected().getRecipe().getIngredientCosts().keySet()
        ));

        productLbl.setText(selected.getProductName());
        qtyProductTxf.setText(String.valueOf(model.getSelected().getRecipe().getProductQty()));

        ObservableList<String> list = FXCollections.observableArrayList(
                new IngredientManagement().getNameList()
        );
        componentCb.setItems(list);

        handleActionOnRow();
        handleActionBtn();
        handleActionForm();
    }

    public void handleActionOnRow() {
        detailTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                componentCb.getSelectionModel().select(newSelection.getIngredientName());
                qtyComponentTxf.setText(model.getSelected().getRecipe().getIngredientCosts().get(newSelection).toString());
            }
        });
    }

    public void handleActionBtn() {
        EventHandler<ActionEvent> buttonAddHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int rs = MessageDialog.showAlert(
                        "Warning", "Bạn muốn thêm thành phần mới?" +
                                "\n\nCác thay đổi sẽ mất nếu bạn không lưu.");
                if(rs == 1) {
                    try{
                        model.handleAdd(
                                componentCb.getValue(),
                                Integer.parseInt(qtyComponentTxf.getText())
                        );
                    } catch(Exception e) {
                        throw new RuntimeException(e);
                    }
                    detailTable.refresh();
                    detailTable.setItems(FXCollections.observableArrayList(model.getSelected().getRecipe().getIngredientCosts().keySet()));
                }
            }
        };

        EventHandler<ActionEvent> buttonUpdateHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    Ingredient selectedIg = detailTable.getSelectionModel().getSelectedItem();
                    int rs = MessageDialog.showAlert(
                            "Warning", "Bạn muốn thay đổi thông tin thành phần?" +
                                    "\n\nCác thay đổi sẽ mất nếu bạn không lưu.");
                    if (rs == 1) {
                        try{
                            model.handleUpdate(
                                    selectedIg,
                                    componentCb.getValue(),
                                    Integer.parseInt(qtyComponentTxf.getText())
                            );
                        } catch(Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("DetailRecipeController - Khong bat duoc selected");
                }
                detailTable.refresh();
                detailTable.setItems(FXCollections.observableArrayList(
                        model.getSelected().getRecipe().getIngredientCosts().keySet()));
            }
        };

        EventHandler<ActionEvent> buttonDeleteHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    Ingredient selectedIg = detailTable.getSelectionModel().getSelectedItem();
                    int rs = MessageDialog.showAlert(
                            "Warning", "Bạn xóa thành phần?" +
                                    "\n\nCác thay đổi sẽ mất nếu bạn không lưu.");
                    if (rs == 1) {
                        model.handleDelete(selectedIg);
                    }
                } catch(Exception e) {
                    System.out.println("Khong bat duoc selected");
                }
                detailTable.setItems(FXCollections.observableArrayList(
                        model.getSelected().getRecipe().getIngredientCosts().keySet()));
            }
        };

        addBtn.setOnAction(buttonAddHandler);
        editBtn.setOnAction(buttonUpdateHandler);
        deleteBtn.setOnAction(buttonDeleteHandler);
    }

    public void handleActionForm() {
        EventHandler<ActionEvent> buttonSaveHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    int newQty = Integer.parseInt(qtyProductTxf.getText());
                    if(model.getSelected().getRecipe().getProductQty() <= 0)
                        throw new Exception();
                    if( newQty != model.getSelected().getRecipe().getProductQty() &&
                            model.getSelected().getRecipe().getProductQty() > 0) {
                        model.handleUpdatePrdQty(newQty);
                        ((Stage) ((Node)event.getSource()).getScene().getWindow()).close();
                    }
                } catch(Exception e) {
                    AppControl.showAlert("error", messageInvalidNumber);
                }
            }
        };

        saveBtn.setOnAction(buttonSaveHandler);
    }

}
