package App.Depot.Controller;

import App.Depot.Model.IngredientManagementModel;
import App.Depot.View.MessageDialog;
import Entity.Ingredient;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class IngredientManagementController implements Initializable {
    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//    Code convert java.sql.Date to Local Date
//    Covert LocalDate to java.sql.Date
//    java.sql.Date.valueOf(dateToConvert);
    @FXML
    private TableView<Ingredient> ingredientTable;
    @FXML
    private TableColumn<Ingredient, Integer> idCol;
    @FXML
    private TableColumn<Ingredient, String> nameCol;
    @FXML
    private TableColumn<Ingredient, String> typeCol;
    @FXML
    private TableColumn<Ingredient, Integer> storageCol;
    @FXML
    private TableColumn<Ingredient, Integer> limitCol;
    @FXML
    private TableColumn<Ingredient, Integer> priceCol;
    @FXML
    private TextField nameTxf;
    @FXML
    private TextField limitTxf;
    @FXML
    private TextField typeTxf;
    @FXML
    private TextField priceTxf;
    @FXML
    private Label createDateLbl;
    @FXML
    private Label deleteDateLbl;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button addBtn;
    @FXML
    private Button editBtn;

    private IngredientManagementModel model;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idCol.setCellValueFactory(new PropertyValueFactory<>("ingredientId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("ingredientName"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("ingredientType"));
        storageCol.setCellValueFactory(new PropertyValueFactory<>("ingredientStorage"));
        limitCol.setCellValueFactory(new PropertyValueFactory<>("ingredientLimit"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        init();
    }

    public void init() {
        model = new IngredientManagementModel();
        ingredientTable.setItems(model.getList());
        handleActionOnRow();
        handleActionBtn();
    }

    public void handleActionOnRow() {
        ingredientTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                nameTxf.setText(newSelection.getIngredientName());
                typeTxf.setText(newSelection.getIngredientType());
                priceTxf.setText(String.valueOf(newSelection.getPrice()));
                limitTxf.setText(String.valueOf(newSelection.getIngredientLimit()));
                try{
                    createDateLbl.setText(sdf.format(newSelection.getCreateDate()));
                    if(newSelection.getDeleteDate() != null) {
                        deleteDateLbl.setText(sdf.format(newSelection.getDeleteDate()));
                    }
                } catch (Exception ignored) {
                    System.out.println("error");
                }
                if(newSelection.isDeleted()) {
                    handleDeletedIngredient();
                } else {
                    addBtn.setVisible(true);
                    editBtn.setVisible(true);
                    deleteBtn.setVisible(true);
                }
            }
        });
    }

    public void handleActionBtn() {

        EventHandler<ActionEvent> buttonAddHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MessageDialog messageDialog = new MessageDialog(
                        "Confirm Addition",
                        "Do you want to add the new ingredient?",
                        "Your changes will be lost if you don’t save them.",
                        MessageDialog.TYPES.get("Confirmation")
                );
                int rs = messageDialog.showMessage();
                if(rs == 1) {
                    try{
                        model.handleCreate(new Ingredient(
                                nameTxf.getText(),
                                typeTxf.getText(),
                                Integer.parseInt(priceTxf.getText()),
                                Integer.parseInt(limitTxf.getText())
                        ));
                        ingredientTable.setItems(model.getList());
                    } catch (Exception exception){
                        System.out.println("Khong parse duoc so luong nhap vao");
                    }
                }
            }
        };

        EventHandler<ActionEvent> buttonUpdateHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Ingredient selected = ingredientTable.getSelectionModel().getSelectedItem();
                int index = ingredientTable.getSelectionModel().getSelectedIndex();
                try{
                    String name = nameTxf.getText();
                    String type = typeTxf.getText();
                    int limit = Integer.parseInt(limitTxf.getText());
                    int price = Integer.parseInt(priceTxf.getText());
                    MessageDialog messageDialog = new MessageDialog(
                            "Confirm Edition",
                            "Do you want to edit the information?",
                            "Your changes will be lost if you don’t save them.",
                            MessageDialog.TYPES.get("Confirmation")
                    );
                    int rs = messageDialog.showMessage();
                    if (rs == 1) {
                        model.handleUpdate(selected, index, name, type, price, limit);
                        ingredientTable.refresh();
                    }
                } catch (Exception e) {
                    System.out.println("Khong bat duoc selected");
                }
            }
        };

        EventHandler<ActionEvent> buttonDeleteHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Ingredient selected = ingredientTable.getSelectionModel().getSelectedItem();
                try{
                    MessageDialog messageDialog = new MessageDialog(
                            "Confirm Delete",
                            "Do you want to delete the information?",
                            "Your changes will be lost if you don’t save them.",
                            MessageDialog.TYPES.get("Confirmation")
                    );
                    int rs = messageDialog.showMessage();
                    if (rs == 1) {
                        model.handleDelete(selected);
                        ingredientTable.refresh();
                    }
                } catch(Exception e) {
                    System.out.println("Khong bat duoc selected - xoa ingredient");
                }
            }
        };

        addBtn.setOnAction(buttonAddHandler);
        editBtn.setOnAction(buttonUpdateHandler);
        deleteBtn.setOnAction(buttonDeleteHandler);
    }

    public void handleDeletedIngredient() {
        addBtn.setVisible(false);
        editBtn.setVisible(false);
        deleteBtn.setVisible(false);
    }


}
