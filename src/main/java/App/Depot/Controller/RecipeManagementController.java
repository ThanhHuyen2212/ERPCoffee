package App.Depot.Controller;

import App.Depot.View.MessageDialog;
import Entity.Product;
import Logic.ProductManagement;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.css.PseudoClass;
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
    private TableColumn<Product, String> qtyCol;
    @FXML
    private TextField searchTxf;
    @FXML
    private Label searchBtn;
    @FXML
    private Button addBtn;
    @FXML
    private Button deleteBtn;

    private ProductManagement model;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idCol.setCellValueFactory(new PropertyValueFactory<>("productId"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        qtyCol.setCellValueFactory(data -> new SimpleStringProperty(
                String.valueOf(data.getValue().getRecipe().getProductQty())
        ));
        unitCol.setCellValueFactory(new PropertyValueFactory<>(""));

        addBtn.setVisible(false);
        deleteBtn.setVisible(false);

        init();
    }

    public void init() {
        model = new ProductManagement();
        productTable.setItems(FXCollections.observableArrayList(model.getProducts()));

        handleActionOnRow();
        setup();
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
                    childStage.showAndWait();
                    productTable.refresh();
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
                Product selected = productTable.getSelectionModel().getSelectedItem();
                try{
                    MessageDialog messageDialog = new MessageDialog(
                            "Confirm Delete",
                            "Do you want to delete the information?",
                            "Your changes will be lost if you don’t save them.",
                            MessageDialog.TYPES.get("Confirmation")
                    );
                    int rs = messageDialog.showMessage();
                    if (rs == 1) {
//                        model.handleDelete(selected);
//                        ingredientTable.setItems(model.getList());
                    }
                } catch(Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };

        addBtn.setOnAction(buttonAddHandler);
        deleteBtn.setOnAction(buttonDeleteHandler);
    }

    public void setup() {
        //The pseudo classes 'up' and 'down' that were defined in the css file.
        PseudoClass up = PseudoClass.getPseudoClass("up");

        //Set a rowFactory for the table view.
        productTable.setRowFactory(tableView -> {
            TableRow<Product> row = new TableRow<>();

            row.itemProperty().addListener((obs, previous, current) -> {
                if (current != null) {
                    if(current.getRecipe() == null) {
                        row.pseudoClassStateChanged(up, true);
                    } else {
                        row.pseudoClassStateChanged(up, false);
                    }
                }
            });
            return row;
        });
    }



}
