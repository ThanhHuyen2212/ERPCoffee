package App.Controller;

import App.Model.AccountTable;
import App.Model.CategoryTable;
import App.Model.ProductModel;
import DAL.ProductAccess;
import Entity.Category;
import Entity.Product;
import Entity.Size;
import Logic.ProductManagement;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import static java.lang.String.valueOf;

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
    private ComboBox CBSize;

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
    @FXML
    private TextField TFVolume;
    @FXML
    private Tab TabSize;
    @FXML
    private Tab TabProduct;
    @FXML
    private TabPane TPProduct;
    @FXML
    private TextField TFSearchProd;

    @FXML
    private TextField TFSearchSize;
    public static ArrayList<Product> ProductViewList;
    private ObservableList<Product> ProductTable;
    public void fillDataDetail(TableView tableView){
        tableView.setRowFactory( tv -> {
            TableRow<Product> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Product rowData = row.getItem();
                    TFProductId.setText(valueOf(rowData.getProductId()));
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
                    CBSize.getSelectionModel().select(1);
                    changeSize(String.valueOf(CBSize.getSelectionModel().getSelectedItem()),rowData);
                    CBSize.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            try{
                                changeSize(String.valueOf(CBSize.getSelectionModel().getSelectedItem()),rowData);
                            }catch (Exception e){
                                showAlert("error","Fail!");
                            }
                        }
                    });
                }
            });
            return row ;
        });
    }
    public void changeSize(String size,Product product){
        TFVolume.setText(String.valueOf(product.getVolume(size)));
        TFPrice.setText(String.valueOf(product.getPrice(size)));
    }
    public void refillData(){
        ProductModel productModel = new ProductModel();
        ProductViewList = productModel.getProducts();
        ProductTable = FXCollections.observableArrayList(ProductViewList);
        TBProduct.setItems(ProductTable);
        TBProductSize.setItems(ProductTable);
        TBProduct.refresh();
        TBProductSize.refresh();
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
    public void  setDataCBSize(){
        ProductModel productModel = new ProductModel();
        ArrayList<Size> SizeList = productModel.getSize();
        int size = SizeList.size();
        String[] array = new String[size];
        for (int i = 0 ; i < size ; i++ ){
            array[i] = SizeList.get(i).getSign();
        }
        CBSize.getItems().clear();
        CBSize.getItems().addAll(array);
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
                    String s = valueOf(ImgProduct.getImage().getUrl());
                    String[] words=s.split("/");
                    s = words[words.length-1];
                    String name = TFProductName.getText();
                    String category = valueOf(CBCategory.getSelectionModel().getSelectedItem());
//                    System.out.println(words[words.length-1]);
                    Product product = new Product(name,s,category);
                    ProductModel productModel = new ProductModel();
                    productModel.addNewProduct(product);
                    ProductViewList = productModel.getProducts();
                    ProductTable = FXCollections.observableArrayList(ProductViewList);
                    TBProduct.setItems(ProductTable);
                    TBProductSize.setItems(ProductTable);
                    TBProduct.refresh();
                    TBProductSize.refresh();
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
                    String s = valueOf(ImgProduct.getImage().getUrl());
                    String[] words=s.split("/");
                    s = words[words.length-1]; //path imange
                    int id = Integer.parseInt(TFProductId.getText());
                    String name = TFProductName.getText();
                    String category = valueOf(CBCategory.getSelectionModel().getSelectedItem());
                    Product product = new Product(id,name,s,category);
                    //Update
                    ProductModel productModel = new ProductModel();
                    productModel.updateOldProduct(product);
                    ProductViewList = productModel.getProducts();
                    ProductTable = FXCollections.observableArrayList(ProductViewList);
                    TBProduct.setItems(ProductTable);
                    TBProductSize.setItems(ProductTable);
                    TBProductSize.refresh();
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
        BtnAddSize.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                try{
//                    ProductModel productModel = new ProductModel();
                    ProductAccess productAccess = new ProductAccess();
//                    ProductManagement productManagement = new ProductManagement();
                    String name = TFProductNameSize.getText();
                    String size = String.valueOf(CBSize.getSelectionModel().getSelectedItem());
                    int vle = Integer.parseInt(TFVolume.getText());
                    int price = Integer.parseInt(TFPrice.getText());
                    productAccess.InsertProductSize(name,size,vle,price);
//                    productModel.addProdSize(name,size,vle,price);
//                    productManagement.insertProdSize(name,size,vle,price);
                    refillData();
                    showAlert("success","Success!");
                }catch (Exception e){
                    showAlert("error","Fail!");
                }
            }
        });
        BtnUpdateSize.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try{
                    String name = TFProductNameSize.getText();
                    String size = String.valueOf(CBSize.getSelectionModel().getSelectedItem());
                    int vle = Integer.parseInt(TFVolume.getText());
                    int price = Integer.parseInt(TFPrice.getText());
                    System.out.println(name+" "+size+" "+vle+" "+price);
                    ProductAccess productAccess = new ProductAccess();
                    productAccess.UpdateProductSize(name,size,vle,price);
                    ProductModel productModel = new ProductModel();
                    ProductViewList = productModel.getProducts();
                    ProductTable = FXCollections.observableArrayList(ProductViewList);
                    TBProductSize.setItems(ProductTable);
                    TBProductSize.refresh();
                    showAlert("success","Success!");
                }catch (Exception e){
                    showAlert("error","Fail!");
                }
            }
        });
        BtnClearSize.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    TFProductNameSize.setText(null);
                    CBSize.getSelectionModel().select(2);
                    TFPrice.setText(null);
                    TFVolume.setText(null);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }
    public void changeTab(){
        TabSize.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event t) {
                if (TabSize.isSelected()) {
                    refillData();
                }
            }
        });
    }
    public void searchProduct(TableView<Product> tableView, TextField textField  ){
        ObservableList data =  tableView.getItems();
        textField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (oldValue != null && (newValue.length() < oldValue.length())) {
                tableView.setItems(data);
            }
            String value = newValue.toLowerCase();
            ObservableList<Product> subentries = FXCollections.observableArrayList();

            long count = tableView.getColumns().stream().count();
            for (int i = 0; i < tableView.getItems().size(); i++) {
                for (int j = 0; j < count; j++) {
                    String entry = "" + tableView.getColumns().get(j).getCellData(i);
                    if (entry.toLowerCase().contains(value)) {
                        subentries.add(tableView.getItems().get(i));
                        break;
                    }
                }
            }
            tableView.setItems(subentries);
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
        fillDataSize(TBProductSize);
        setDataCBCategory();
        setDataCBSize();
        actionButton();
        searchProduct(TBProduct,TFSearchProd);
        searchProduct(TBProductSize,TFSearchSize);
    }
}
