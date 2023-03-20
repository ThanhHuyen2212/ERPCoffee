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
    private Label createdDateLbl;
    @FXML
    private DatePicker deleteDatePk;
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
                limitTxf.setText(String.valueOf(newSelection.getIngredientLimit()));
                try{
                    createdDateLbl.setText(sdf.format(newSelection.getCreateDate()));
//                    Code convert java.sql.Date to Local Date
                    LocalDate ld = (new Date(newSelection.getDeleteDate().getTime())).toLocalDate();
                    deleteDatePk.setValue(ld);

//                    Covert LocalDate to java.sql.Date
//                    java.sql.Date.valueOf(dateToConvert);
                } catch (Exception ignored) {
                    System.out.println("error");
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
                    model.handleCreate(new Ingredient(
                            1,
                            "Bot matcha",
                            "Nguyen lieu kho",
                            22,
                            7,
                            90000));
                    ingredientTable.setItems(model.getList());
                }
            }
        };

        EventHandler<ActionEvent> buttonUpdateHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Ingredient selected = ingredientTable.getSelectionModel().getSelectedItem();
                int index = ingredientTable.getSelectionModel().getSelectedIndex();
                LocalDate deleteDate = null;
                try{
//                    String name = "Bot cacao dua";
//                    String type = "Nguyen lieu duoc che bien";
//                    int limit = 12;
                    String name = nameTxf.getText();
                    String type = typeTxf.getText();
                    int limit = Integer.parseInt(limitTxf.getText());
                    deleteDate = deleteDatePk.getValue();
                    MessageDialog messageDialog = new MessageDialog(
                            "Confirm Edition",
                            "Do you want to edit the information?",
                            "Your changes will be lost if you don’t save them.",
                            MessageDialog.TYPES.get("Confirmation")
                    );
                    int rs = messageDialog.showMessage();
                    if (rs == 1) {
                        model.handleUpdate(selected, index, name, type, limit, Date.valueOf(deleteDate));
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
                        ingredientTable.setItems(model.getList());
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


}
