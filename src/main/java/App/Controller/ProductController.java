package App.Controller;

import App.Model.CategoryTable;
import App.Model.ProductModel;
import Entity.Category;
import Entity.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class ProductController implements Initializable {
    @FXML
    private Button BtnAdd;

    @FXML
    private Button BtnAddSize;

    @FXML
    private Button BtnClear;

    @FXML
    private Button BtnClearSize;

    @FXML
    private Button BtnDel;

    @FXML
    private Button BtnDelSize;

    @FXML
    private Button BtnEdit;

    @FXML
    private Button BtnUpdateSize;

    @FXML
    private Button BtnUpload;

    @FXML
    private ComboBox CBCategory;

    @FXML
    private ComboBox<?> CBSize;

    @FXML
    private TableColumn<Product, String> ColCategory;
    @FXML
    private TableColumn<Product, Date> ColCreateAt;

    @FXML
    private TableColumn<Product, Date> ColDeleteAt;
    @FXML
    private TableColumn<Product, Integer> ColProductId;

    @FXML
    private TableColumn<Product, String> ColProductName;


    @FXML
    private ImageView ImgProduct;

    @FXML
    private Label LBCategory;

    @FXML
    private Label LBProductId;

    @FXML
    private Label LBProductName;

    // WARNING: fx:id="TBProduct" cannot be injected: several objects share the same fx:id;
    @FXML
    private TableView<Product> TBProduct ;
    @FXML
    private TextField TFPrice;

    @FXML
    private TextField TFProductId;

    @FXML
    private TextField TFProductName;

    @FXML
    private TextField TFProductNameSize;
    @FXML
    private TableView<Product> TBProductSize;
    @FXML
    private TableColumn<Product, String> ColCategorySize;
    @FXML
    private TableColumn<Product, Integer> ColProductIdSize;
    @FXML
    private TableColumn<Product, String> ColProductNameSize;
    public static ArrayList<Product> ProductViewList;
    private ObservableList<Product> ProductTable;
    public void fillDataDetail(TableView tableView){
        tableView.setRowFactory( tv -> {
            TableRow<Product> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Product rowData = row.getItem();
                    TFProductId.setText(String.valueOf(rowData.getProductId()));
                    TFProductName.setText(rowData.getProductName());
                    File file = new File("src/main/java/Assets/Images/"+rowData.getImagePath());
                    Image image = new Image(file.toURI().toString());
                    ImgProduct.setImage(image);
                    System.out.println(rowData.getImagePath());
                    CBCategory.getSelectionModel().select(rowData.getCategory());
                }
            });
            return row ;
        });
    }
    public void fillDataSize(TableView tableView){
        tableView.setRowFactory( tv -> {
            TableRow<Product> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Product rowData = row.getItem();
                    TFProductNameSize.setText(rowData.getProductName());

                }
            });
            return row ;
        });
    }
    public void refillData(ProductModel productModel){
        ProductViewList = productModel.getProducts();
        ProductTable = FXCollections.observableArrayList(ProductViewList);
        TBProduct.setItems(ProductTable);
        TBProduct.refresh();
    }
    public void setDataCBCategory(){
        CategoryTable categoryTable = new CategoryTable();
        ArrayList<Category> categoryTables = new ArrayList<>();
        categoryTables = categoryTable.getDataCategory();
        int size = categoryTables.size();
        String[] array = new String[size];
        for (int i = 0 ; i < size ; i++ ){
            array[i] = categoryTables.get(i).getCategoryName();
        }
        CBCategory.getItems().clear();
        CBCategory.getItems().addAll(array);
    }
    public void actionButton(){
        BtnUpload.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    FileChooser fileChooser = new FileChooser();
                    File file = fileChooser.showOpenDialog(new Stage());
                    Image image = new Image(file.toURI().toString());
                    ImgProduct.setImage(image);
                } catch (Exception e){
                    System.out.println("Upload fail!");
                }
            }
        });
        BtnAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                try{
                    String s = String.valueOf(ImgProduct.getImage().getUrl());
                    String[] words=s.split("/");
                    s = words[words.length-1];
                    String name = TFProductName.getText();
                    String category = String.valueOf(CBCategory.getSelectionModel().getSelectedItem());
//                    System.out.println(words[words.length-1]);
                    Product product = new Product(name,s,category);
                    ProductModel productModel = new ProductModel();
                    productModel.addNewProduct(product);
                    ProductViewList = productModel.getProducts();
                    ProductTable = FXCollections.observableArrayList(ProductViewList);
                    TBProduct.setItems(ProductTable);
                    TBProduct.refresh();
                    showAlert("Success","Success!");
                }catch (Exception e){
                    System.out.println("fail");
                    showAlert("error","Fail!");
                }
            }
        });
        BtnEdit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try{
                    String s = String.valueOf(ImgProduct.getImage().getUrl());
                    String[] words=s.split("/");
                    s = words[words.length-1]; //path imange
                    int id = Integer.parseInt(TFProductId.getText());
                    String name = TFProductName.getText();
                    String category = String.valueOf(CBCategory.getSelectionModel().getSelectedItem());
                    Product product = new Product(id,name,s,category);
                    //Update
                    ProductModel productModel = new ProductModel();
                    productModel.updateOldProduct(product);
                    ProductViewList = productModel.getProducts();
                    ProductTable = FXCollections.observableArrayList(ProductViewList);
                    TBProduct.setItems(ProductTable);
                    TBProduct.refresh();
                    showAlert("Success","Success!");
                }catch (Exception e){
                    showAlert("error","Fail!");
                }
            }
        });
        BtnClear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try{
                    File file = new File("src/main/java/Assets/Icons/no-pictures.png");
                    Image image = new Image(file.toURI().toString());
                    ImgProduct.setImage(image);
                    TFProductId.setText(null);
                    TFProductName.setText(null);
                    CBCategory.getSelectionModel().select(0);
                }catch (Exception e){
                    showAlert("error","Fail!");
                }
            }
        });
        ProductModel productModel = new ProductModel();
        ProductViewList = productModel.getProducts();
        ProductTable = FXCollections.observableArrayList(ProductViewList);
        TBProduct.setItems(ProductTable);
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
        ProductModel productModel = new ProductModel();
        ProductViewList = productModel.getProducts();
        ProductTable = FXCollections.observableArrayList(ProductViewList);
        ColProductId.setCellValueFactory(new PropertyValueFactory<Product,Integer>("productId"));
        ColProductIdSize.setCellValueFactory(new PropertyValueFactory<Product,Integer>("productId"));
        ColProductName.setCellValueFactory(new PropertyValueFactory<Product,String>("productName"));
        ColProductNameSize.setCellValueFactory(new PropertyValueFactory<Product,String>("productName"));
        ColCreateAt.setCellValueFactory(new PropertyValueFactory<Product, Date>("createAt"));
        ColDeleteAt.setCellValueFactory(new PropertyValueFactory<Product, Date>("deleteAt"));
        ColCategory.setCellValueFactory(new PropertyValueFactory<Product,String>("category"));
        ColCategorySize.setCellValueFactory(new PropertyValueFactory<Product,String>("category"));
        TBProduct.setItems(ProductTable);
        TBProductSize.setItems(ProductTable);
        File file = new File("src/main/java/Assets/Icons/no-pictures.png");
        Image image = new Image(file.toURI().toString());
        ImgProduct.setImage(image);
        fillDataDetail(TBProduct);
        setDataCBCategory();
        actionButton();
    }
}
