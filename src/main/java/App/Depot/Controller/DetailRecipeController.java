package App.Depot.Controller;

import App.Depot.Model.DetailRecipeModel;
import App.Depot.View.MessageDialog;
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

public class DetailRecipeController implements Initializable {
    @FXML
    private TableView<Ingredient> detailTable;
    @FXML
    private TableColumn<Ingredient, String> idCol;
    @FXML
    private TableColumn<Ingredient, ?> componentCol;
    @FXML
    private TableColumn<Ingredient, String> qtyCol;
    @FXML
    private TableColumn<Ingredient, ?> unitCol;
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
    private Button cancelBtn;
    @FXML
    private ComboBox<String> componentCb;
    private DetailRecipeModel model;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new DetailRecipeModel();

        idCol.setCellValueFactory(new PropertyValueFactory<>("ingredientId"));
        componentCol.setCellValueFactory(new PropertyValueFactory<>("ingredientName"));
        qtyCol.setCellValueFactory(data -> new SimpleStringProperty(
                model.getDetails().get(data.getValue()).toString()
        ));
//        unitCol.setCellValueFactory(new PropertyValueFactory<>("ingredientStorage"));
    }

    public void init(Product selected) {
        model.setSelected(selected);
        detailTable.setItems(FXCollections.observableArrayList(
                model.getDetails().keySet()
        ));

        productLbl.setText(selected.getProductName());

        handleActionOnRow();
        handleActionBtn();
        handleActionForm();
    }

    public void handleActionOnRow() {
        detailTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                qtyProductTxf.setText(String.valueOf(model.getSelected().getRecipe().getProductQty()));
                ObservableList<String> list = FXCollections.observableArrayList(
                        new IngredientManagement().getNameList()
                );
                componentCb.setItems(list);
                componentCb.getSelectionModel().select(newSelection.getIngredientName());
                qtyComponentTxf.setText(model.getDetails().get(newSelection).toString());
            }
        });
    }

    public void handleActionBtn() {
        EventHandler<ActionEvent> buttonAddHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MessageDialog messageDialog = new MessageDialog(
                        "Confirm Addition",
                        "Do you want to add the new component?",
                        "Your changes will be lost if you don’t save them.",
                        MessageDialog.TYPES.get("Confirmation")
                );
                int rs = messageDialog.showMessage();

                if(rs == 1) {
//                    model.handleCreate("Ca phe hat", 12);
                    try{
                        model.handleAdd(
                                componentCb.getValue(),
                                Integer.parseInt(qtyComponentTxf.getText())
                        );
                    } catch(Exception e) {

                    }
                    detailTable.refresh();
                    detailTable.setItems(FXCollections.observableArrayList(model.getDetails().keySet()));
                }
            }
        };

        EventHandler<ActionEvent> buttonUpdateHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                LocalDate deleteDate = null;
                try{
                    Ingredient selectedIg = detailTable.getSelectionModel().getSelectedItem();
                    MessageDialog messageDialog = new MessageDialog(
                            "Confirm Edition",
                            "Do you want to edit the information?",
                            "Your changes will be lost if you don’t save them.",
                            MessageDialog.TYPES.get("Confirmation")
                    );
                    int rs = messageDialog.showMessage();
                    if (rs == 1) {
                        try{
                            model.handleUpdate(
                                    selectedIg,
                                    componentCb.getValue(),
                                    Integer.parseInt(qtyComponentTxf.getText())
                            );
                        } catch(Exception e) {

                        }
//                        model.handleUpdate(selected, index, name, type, limit, Date.valueOf(deleteDate));
//                        ingredientTable.refresh();
                    }
                } catch (Exception e) {
                    System.out.println("Khong bat duoc selected");
                }
                detailTable.refresh();
                detailTable.setItems(FXCollections.observableArrayList(model.getDetails().keySet()));
            }
        };

        EventHandler<ActionEvent> buttonDeleteHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    Ingredient selectedIg = detailTable.getSelectionModel().getSelectedItem();
                    MessageDialog messageDialog = new MessageDialog(
                            "Confirm Delete",
                            "Do you want to delete the information?",
                            "Your changes will be lost if you don’t save them.",
                            MessageDialog.TYPES.get("Confirmation")
                    );
                    int rs = messageDialog.showMessage();
                    if (rs == 1) {
                        model.handleDelete(selectedIg);
                    }
                } catch(Exception e) {
                    System.out.println("Khong bat duoc selected");
                }
                detailTable.setItems(FXCollections.observableArrayList(model.getDetails().keySet()));
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
                if(Integer.parseInt(qtyProductTxf.getText()) ==
                        model.getSelected().getRecipe().getProductQty()) {
                }
            }
        };
        EventHandler<ActionEvent> buttonCancelHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MessageDialog messageDialog = new MessageDialog(
                        "Quit",
                        "Do you want to quit ?",
                        "Your changes will be lost if you don’t save them.",
                        MessageDialog.TYPES.get("Confirmation")
                );
                int rs = messageDialog.showMessage();
                if(rs == 1) {
                    ((Stage) ((Node)event.getSource()).getScene().getWindow()).close();
                }
            }
        };

        saveBtn.setOnAction(buttonSaveHandler);
        cancelBtn.setOnAction(buttonCancelHandler);
    }

}
