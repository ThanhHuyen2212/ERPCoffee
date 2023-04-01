package App.Depot.Controller;

import App.Depot.Model.IngredientManagementModel;
import App.Depot.View.MessageDialog;
import App.ModuleManager.AppControl;
import Entity.Ingredient;
import javafx.beans.property.SimpleStringProperty;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import static Main.MainApp.APP;

public class IngredientManagementController implements Initializable {
    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public static final NumberFormat nf = new DecimalFormat( "###,###,###,###.##" );
    public static final String messageInvalidNumber = "Giá trị số lượng không hợp lệ !";
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
    private TableColumn<Ingredient, String> unitCol;
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
        unitCol.setCellValueFactory(data -> new SimpleStringProperty(
                setupUnit(data.getValue())
        ));

        init();

        APP.getPOSButton("ingredient").setOnAction(e -> {
            ingredientTable.refresh();
        });
    }

    public void init() {
        model = new IngredientManagementModel();
        ingredientTable.setItems(model.getList());
        handleActionOnRow();
        handleActionBtn();
        setup();
    }

    public void handleActionOnRow() {
        ingredientTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                nameTxf.setText(newSelection.getIngredientName());
                typeTxf.setText(newSelection.getIngredientType());
                priceTxf.setText(String.valueOf(newSelection.getPrice()));
                limitTxf.setText(String.valueOf(newSelection.getIngredientLimit()));
                try {
                    createDateLbl.setText(sdf.format(newSelection.getCreateDate()));
                    if (newSelection.getDeleteDate() != null) {
                        deleteDateLbl.setText(sdf.format(newSelection.getDeleteDate()));
                    } else {
                        deleteDateLbl.setText("");
                    }
                } catch (Exception ignored) {
                    System.out.println("error");
                }
                if (newSelection.isDeleted()) {
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
                int rs = MessageDialog.showAlert(
                        "Warning", "Bạn muốn thêm mới thông tin nguyên liệu?" +
                                "\n\nCác thay đổi sẽ mất nếu bạn không lưu.");
                if (rs == 1) {
                    try {
                        model.handleCreate(new Ingredient(
                                nameTxf.getText(),
                                typeTxf.getText(),
                                Integer.parseInt(priceTxf.getText()),
                                Integer.parseInt(limitTxf.getText())
                        ));
                        ingredientTable.setItems(model.getList());
                    } catch (Exception exception) {
                        AppControl.showAlert("error", messageInvalidNumber);
                    }
                }
            }
        };

        EventHandler<ActionEvent> buttonUpdateHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Ingredient selected = ingredientTable.getSelectionModel().getSelectedItem();
                int index = ingredientTable.getSelectionModel().getSelectedIndex();
                try {
                    String name = nameTxf.getText();
                    String type = typeTxf.getText();
                    int limit = Integer.parseInt(limitTxf.getText());
                    int price = Integer.parseInt(priceTxf.getText());
                    int rs = MessageDialog.showAlert(
                            "Warning", "Bạn muốn lưu thay đổi thông tin nguyên liệu?" +
                                    "\n\nCác thay đổi sẽ mất nếu bạn không lưu.");
                    if (rs == 1) {
                        model.handleUpdate(selected, index, name, type, price, limit);
                        ingredientTable.refresh();
                    }
                } catch (Exception e) {
                    AppControl.showAlert("error", "Giá trị số lượng không hợp lệ!");
                }
            }
        };

        EventHandler<ActionEvent> buttonDeleteHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Ingredient selected = ingredientTable.getSelectionModel().getSelectedItem();
                try {
                    int rs = MessageDialog.showAlert(
                            "Warning", "Bạn muốn xóa thông tin nguyên liệu?" +
                                    "\n\nCác thay đổi sẽ mất nếu bạn không lưu.");
                    if (rs == 1) {
                        model.handleDelete(selected);
                        ingredientTable.refresh();
                    }
                } catch (Exception e) {
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

    public void setup() {
        //The pseudo classes 'up' and 'down' that were defined in the css file.
        PseudoClass up = PseudoClass.getPseudoClass("up");
        PseudoClass faded = PseudoClass.getPseudoClass("faded");

        //Set a rowFactory for the table view.
        ingredientTable.setRowFactory(tableView -> {
            TableRow<Ingredient> row = new TableRow<>();
            row.itemProperty().addListener((obs, previous, current) -> {
                if (current != null) {
                    if (current.getIngredientLimit() > current.getIngredientStorage()) {
                        if(current.isDeleted())
                            row.pseudoClassStateChanged(faded, true);
                        else
                            row.pseudoClassStateChanged(up, true);
                    } else {
                        row.pseudoClassStateChanged(up, false);
                    }
                }
            });
            return row;
        });
    }

    public static String setupUnit(Ingredient ingredient) {
        if (ingredient.getIngredientType().equalsIgnoreCase("Nguyên liệu lỏng")) {
            return "ml";
        } else if (ingredient.getIngredientName().equalsIgnoreCase("Trứng")
                || ingredient.getIngredientName().equalsIgnoreCase("Bánh mì")) {
            return "cái";
        }
        return "gram";
    }
}
