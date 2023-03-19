package App.Controller;

import Entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class ShopController implements Initializable {
    @FXML
    private Button BtnSearch;
    @FXML
    private TextField Quality;

    @FXML
    private ComboBox<String> cbSizePrice;

    @FXML
    private GridPane grid;

    @FXML
    private HBox hboxCartegory;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnCancel;
    @FXML
    private Button minusBtn;

    @FXML
    private Button plusBtn;
    @FXML
    private ScrollPane scroll;

    @FXML
    private TextField textSearch;

    @FXML
    private Label txtPriceDetails;

    @FXML
    private Label txtProductNamedetails;

    @FXML
    private AnchorPane viewControl;
    @FXML
    private  Button btnOrder;

    private MyListener myListener;

    private List<Category> categoryList = new ArrayList<>();
    public static ArrayList<Product> productList  = new ArrayList<>();
    public ArrayList<OrderDetail> orderList = new ArrayList<>();
    private List<Size> sizeList = new ArrayList<>();

    /**
     * Fake dữ liệu

     */
    private List<Category> getDataCategories() throws IOException {
        List<Category> categories = new ArrayList<>();
        Category category;
        category = new Category();
        category.setCategoryId(0);
        category.setCategoryName("All");
        categories.add(category);
        category = new Category();
        category.setCategoryId(1);
        category.setCategoryName("Coffee");

        category = new Category();
        category.setCategoryId(2);
        category.setCategoryName("milkTea");
        categories.add(category);
        category = new Category();
        category.setCategoryId(3);
        category.setCategoryName("milkTea1");
        categories.add(category);
        return categories;
    }
    private ArrayList<Product> getDataProducts(){
        Size sizeS = new Size("S","Size sieu nho");
        Size sizeM = new Size("M","Size sieu vua");
        Size sizeL = new Size("L","Size sieu lon");
        HashMap<Size,Integer> priceList = new HashMap<>();
        priceList.put(sizeS,40000);
        priceList.put(sizeM,45000);
        priceList.put(sizeL,50000);
        ArrayList<Product> products = new ArrayList<>();
        Product product;
        product = new Product();
        product.setProductId(1);
        product.setProductName("White Coffee");
        product.setImagePath("src/main/java/Assets/Images/WhiteCoffee.png");
        product.setPriceList(priceList);
        products.add(product);
        product = new Product();
        product.setProductId(2);
        product.setProductName("White Coffee1");
        product.setImagePath("src/main/java/Assets/Images/WhiteCoffee.png");
        product.setPriceList(priceList);
        products.add(product);
        product = new Product();
        product.setProductId(3);
        product.setProductName("White Coffee3");
        product.setImagePath("src/main/java/Assets/Images/WhiteCoffee.png");
        product.setPriceList(priceList);
        products.add(product);
        product = new Product();
        product.setProductId(4);
        product.setProductName("White Coffee4");
        product.setImagePath("src/main/java/Assets/Images/WhiteCoffee.png");
        product.setPriceList(priceList);
        products.add(product);
        return products;
    }


    //plusBtn
    public void Plus(ActionEvent e) {
        if (Quality.getText().equalsIgnoreCase("")) {
            Quality.setText("1");
        }
        int inputValue = Integer.parseInt(Quality.getText());
        inputValue += 1;
        Quality.setText(String.valueOf(inputValue));

    }
    //minusBtn
    public void Minus(ActionEvent e) {
        int inputValue = Integer.parseInt(Quality.getText());
        if (inputValue > 1) {
            inputValue -= 1;
            Quality.setText(String.valueOf(inputValue));
        }
    }
    public void printOrder(){
        FXMLLoader loader = new FXMLLoader();
        String orderFIle = "src/main/java/App/View/Order.fxml";
        try {
            loader.setLocation(new File(orderFIle).toURI().toURL());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        Pane order = null;
        try {
            order = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Dialog<ButtonType> dialog = new Dialog<>();
        OrderController orderController = loader.getController();
        dialog.setDialogPane((DialogPane) order);
        btnOrder.setOnAction(actionEvent -> {
            orderController.RenderOrder(orderList,"","");
            Optional<ButtonType> clickedButton = dialog.showAndWait();
            if (clickedButton.get() == ButtonType.NO) {
                dialog.close();
            }else if (clickedButton.get()==ButtonType.YES){

            }
        });

    }

    public void render(ArrayList<Product> products){
        orderList.clear();
        products= (ArrayList<Product>) getDataProducts();
        int column=0;
        int row=1;
        if(products.size()>0){
            setDetails(products.get(0));
            myListener = new MyListener() {
                @Override
                public void onClickListener(Product product) {
                    setDetails(product);
                    btnAdd.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {

                        }
                    });
                }

            };
        }
        try {
            if (products.size() == 0) {
                System.out.println("Khong co san pham nao!");
            }else{
                for (int i=0; i<products.size(); i++) {
                    System.out.println(products.get(i).getProductName());
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(new File("src/main/java/App/View/item.fxml").toURI().toURL());
                    AnchorPane anchorPane = fxmlLoader.load();
                    itemController itemController = fxmlLoader.getController();
                    itemController.setData(products.get(i), myListener);
                    if(column==3){
                        column = 0;
                        row++;
                    }
                    grid.add(anchorPane,column++,row);
                    grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                    grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                    grid.setMaxWidth(Region.USE_PREF_SIZE);

                    grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                    grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                    grid.setMaxHeight(Region.USE_PREF_SIZE);
                    GridPane.setMargin(anchorPane, new Insets(15));
                    GridPane.setVgrow(anchorPane, Priority.ALWAYS);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public AnchorPane renderCategory(Category category){
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefHeight(200);
        HBox vbox = new HBox();
        vbox.setStyle(" -fx-alignment:"+" center; \n"+
                "-fx-border-insets:"+"5px; \n"+
                "-fx-background-insets:"+"5px; \n"+
                "-fx-border-radius:"+"30px; \n" +
                "-fx-background-radius:"+"30px; \n" +
                "-fx-background-color:"+"#feca57; \n"+
                "-fx-border-color:"+"#feca57; \n"+
                "-fx-alignment:"+"center; \n"+
                "fx-pref-height:"+ "150px; \n"+
                "-fx-pref-width:" +"200px; \n"
        );
        Label label = new Label(category.getCategoryName());
        label.setStyle("-fx-font-size:"+"20px; \n"+
                "-fx-padding:"+"0 0 0 10px; \n");
        vbox.getChildren().add(label);
        anchorPane.getChildren().add(vbox);
        anchorPane.setOnMouseClicked(e->{
            grid.getChildren().clear();
        });
        return anchorPane;
    }


    /**
     * Hiển thị các thể loại sản phẩm
     * @throws IOException
     */
    public void renderCategories() throws IOException {
        categoryList.addAll(getDataCategories());
        hboxCartegory.setStyle("-fx-effect:"+"dropShadow(three-pass-box, rgba(0,0,0,0.1),10.0,0.0,0.0,10.0);");
        hboxCartegory.getChildren().add(renderCategory(categoryList.get(0)));
        for(int i=1;i< categoryList.size();i++){
            hboxCartegory.getChildren().add(renderCategory(categoryList.get(i)));
        }
    }

    /**
     * Hiển thị giao diện chi tiết sản phẩm
     * @param product
     */
    public void setDetails(Product product){
        ObservableList<String> observableArrayList = FXCollections.observableArrayList(product.getSizeList());
    txtProductNamedetails.setText(product.getProductName());
    cbSizePrice.setItems(observableArrayList);
    cbSizePrice.getSelectionModel().selectFirst();
    if(!cbSizePrice.getValue().isEmpty()){
        txtPriceDetails.setText(String.valueOf(product.getPrice(cbSizePrice.getValue())));
    }
    }
    public void searchProduct(){
        productList=getDataProducts();
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(new File("src/main/java/App/View/Alert.fxml").toURI().toURL());
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        }
        Pane alert = null;
        try {
            alert = loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        Dialog<ButtonType> dialog = new Dialog<>();
        AlertController alertController = loader.getController();
        dialog.setDialogPane((DialogPane) alert);
        ArrayList<Product> products = productList;
        ArrayList<Product> ProductsForSearch = new ArrayList<> ();
        BtnSearch.setOnAction(e->{
            ProductsForSearch.clear();
            if(!textSearch.getText().equals("")){
                String pattern = ".*" + textSearch.getText() + ".*";
                for(Product product:products) {
                    if (product.getProductName().toLowerCase().matches(pattern.toLowerCase())) {
                        ProductsForSearch.add(product);
                    }
                }
                grid.getChildren().clear();
                if (ProductsForSearch.size() > 0){
                    render(ProductsForSearch);
                }else {
                    try {
                        alertController.RenderAlert("Warning", "Không tìm thấy sản phẩm!");
                    } catch (MalformedURLException ex) {
                        throw new RuntimeException(ex);
                    }
                    Optional<ButtonType> clickedButton = dialog.showAndWait();
                    if (clickedButton.get() == ButtonType.OK) {
                        dialog.close();
                        textSearch.setText("");
                        grid.getChildren().clear();
                        render(products);
                    }
                }
            }else{
                try {
                    alertController.RenderAlert("Warning","Vui lòng nhập nội dung cần tìm kiếm!");
                } catch (MalformedURLException ex) {
                    throw new RuntimeException(ex);
                }
                Optional<ButtonType> clickedButton = dialog.showAndWait();
                if(clickedButton.get()== ButtonType.OK){
                    dialog.close();
                }
            }

        });
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            searchProduct();
            renderCategories();
            printOrder();
            render(productList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
