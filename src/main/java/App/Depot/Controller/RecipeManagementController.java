package App.Depot.Controller;

import App.Depot.View.MessageDialog;
import Entity.Product;
import Logic.ProductManagement;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
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

        handleTableUI();
        handleActionOnRow();
    }

    public void handleTableUI() {
        ObservableMap<Product, Boolean> removed = FXCollections.observableHashMap();
        for(Product p : model.getProducts()) {
            if(p.getRecipe() == null) {
                removed.put(p, true);
            }
        }
        PseudoClass removedPseudoClass = PseudoClass.getPseudoClass("removed");
        productTable.setRowFactory(tv -> {
            TableRow<Product> result = new TableRow<>();
            ObjectBinding<Boolean> binding = Bindings.valueAt(removed, result.itemProperty());
            binding.addListener((observable, oldValue, newValue) -> result.pseudoClassStateChanged(
                    removedPseudoClass,
                    newValue != null && newValue
            ));
            return result;
        });
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



}
