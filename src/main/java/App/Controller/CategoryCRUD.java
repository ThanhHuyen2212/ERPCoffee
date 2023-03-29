package App.Controller;

import App.Model.AccountTable;
import App.Model.CategoryTable;
import App.Model.EmployeeTable;
import Entity.Category;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CategoryCRUD implements Initializable {

    @FXML
    private TableColumn<CategoryTable, Integer> ColCategoryId;

    @FXML
    private TableColumn<CategoryTable, String> ColCategoryName;

    @FXML
    private TableView<Category> TBCategory;

    @FXML
    private TextField TFCatagoryId;

    @FXML
    private TextField TFCategoryName;

    @FXML
    private Button btnAddCate;

    @FXML
    private Button btnCancelCate;

    @FXML
    private Button btnDeleteCate;

    @FXML
    private Button btnEditCate;
    public static ArrayList<Category> CategoryTableViewList;
    private ObservableList<Category> categoryTable;
    public void fillDataDetail(TableView TBCategory){
        TBCategory.setRowFactory( tv -> {
            TableRow<Category> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Category rowData = row.getItem();
                    TFCatagoryId.setText(String.valueOf(rowData.getCategoryId()));
                    TFCategoryName.setText(rowData.getCategoryName());
                }
            });
            return row ;
        });

    }
    public void actionButton(){
        btnAddCate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                try{
                    CategoryTable category = new CategoryTable(Integer.parseInt(TFCatagoryId.getText()),TFCategoryName.getText());
                    //Add
                    category.addCategory();
//                    CategoryTable tb = new CategoryTable();
//                    CategoryTableViewList = tb.getDataCategory();
//                    categoryTable = FXCollections.observableArrayList(CategoryTableViewList);
//                    TBCategory.setItems(categoryTable);
                    showAlert("Success","Success!");
                }catch (Exception e){
                    System.out.println("fail");
                    showAlert("error","Fail!");
                }
            }
        });
        btnEditCate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try{
                    CategoryTable category = new CategoryTable(Integer.parseInt(TFCatagoryId.getText()),TFCategoryName.getText());
                    //Add
                    category.editCategory();
                    CategoryTable tb = new CategoryTable();
                    CategoryTableViewList = tb.getDataCategory();
                    categoryTable = FXCollections.observableArrayList(CategoryTableViewList);
                    TBCategory.setItems(categoryTable);
                    showAlert("Success","Success!");
                }catch (Exception e){
                    System.out.println("fail");
                    showAlert("error","Fail!");
                }
            }
        });
        btnCancelCate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                TFCatagoryId.setText(null);
                TFCategoryName.setText(null);
            }
        });
        btnDeleteCate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });
    }
    public static void showAlert(String type,String message){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    new File("src/main/java/App/View/Alert.fxml").toURI().toURL()
            );
            Pane alert = fxmlLoader.load();
            Dialog<ButtonType> btnType = new Dialog<>();
            btnType.setDialogPane((DialogPane) alert);
            AlertController alert1 = fxmlLoader.getController();
            try {
                alert1.RenderAlert(type,message);
            } catch (MalformedURLException exc) {
                throw new RuntimeException(exc);
            }
            btnType.showAndWait();
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CategoryTable cateTb = new CategoryTable();
        CategoryTableViewList = cateTb.getDataCategory();
        categoryTable = FXCollections.observableArrayList(CategoryTableViewList);
        ColCategoryId.setCellValueFactory(new PropertyValueFactory<CategoryTable,Integer>("categoryId"));
        ColCategoryName.setCellValueFactory(new PropertyValueFactory<CategoryTable,String>("categoryName"));
        TBCategory.setItems(categoryTable);
        fillDataDetail(TBCategory);
        actionButton();
    }
}
