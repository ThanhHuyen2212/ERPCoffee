package App.Depot.Controller;

import App.Controller.ShopController;
import Entity.Product;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RecipeManagementController implements Initializable {
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, ?> unitCol;

    @FXML
    private TableColumn<Product, ?> idCol;
    @FXML
    private TableColumn<Product, ?> productNameCol;

    @FXML
    private TableColumn<Product, Integer> qtyCol;
    @FXML
    private TextField searchTxf;
    @FXML
    private Label searchBtn;
    @FXML
    private Button addBtn;
    @FXML
    private Button deleteBtn;

    private ShopController model;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idCol.setCellValueFactory(new PropertyValueFactory<>("productId"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
//        qtyCol.setCellValueFactory(
//                data -> new SimpleStringProperty(String.valueOf(data.getValue().getRecipe().getProductQty()))
//        );
        unitCol.setCellValueFactory(new PropertyValueFactory<>(""));

        init();
    }

    public void init() {
        model = new ShopController();
//        productTable.setItems(FXCollections.observableArrayList(model.getDataProducts()));

        handleActionOnRow();
    }

    public void handleActionOnRow() {
        productTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                String filepath = "src/main/java/App/Depot/View/DetailRecipeView.fxml";
                FXMLLoader fxmlLoader = null;
                Stage childStage = new Stage();
                Parent root = null;
                try {
                    fxmlLoader = new FXMLLoader(new File(filepath).toURI().toURL());
                    root = fxmlLoader.load();
                    DetailRecipeController controller = fxmlLoader.getController();
                    controller.init(newSelection);
                    childStage.setScene(new Scene(root));
                    childStage.show();
                } catch (IOException e) {
                    System.out.println("Khong load duoc child stage");
                    throw new RuntimeException(e);
                }

            }
        });
    }

    public void handleActionBtn() {

        EventHandler<ActionEvent> buttonAddHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        };

        EventHandler<ActionEvent> buttonDeleteHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                Ingredient selected = ingredientTable.getSelectionModel().getSelectedItem();
//                try{
//                    MessageDialog messageDialog = new MessageDialog(
//                            "Confirm Delete",
//                            "Do you want to delete the information?",
//                            "Your changes will be lost if you don’t save them.",
//                            MessageDialog.TYPES.get("Confirmation")
//                    );
//                    int rs = messageDialog.showMessage();
//                    if (rs == 1) {
//                        model.handleDelete(selected);
//                        ingredientTable.setItems(model.getList());
//                    }
//                } catch(Exception e) {
//                    System.out.println("Khong bat duoc selected - xoa ingredient");
//                }
            }
        };

        addBtn.setOnAction(buttonAddHandler);
        deleteBtn.setOnAction(buttonDeleteHandler);
    }



}
