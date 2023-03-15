package App.Controller;

import Entity.Category;
import Entity.Product;
import Entity.Size;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class ShopController implements Initializable {
    @FXML
    private Button BtnSearch;

    @FXML
    private ComboBox<String> cbSizePrice;

    @FXML
    private GridPane grid;

    @FXML
    private HBox hboxCartegory;

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

    private MyListener myListener;

    private List<Category> categoryList = new ArrayList<>();
    public static ArrayList<Product> productList  = new ArrayList<>();
    private List<Size> sizeList = new ArrayList<>();

    /**
     * Fake dữ liệu

     */
    private List<Category> getDataCategories() throws IOException {
        List<Category> categories = new ArrayList<>();
        Category category;
        category = new Category();
        category.setCategoryId(1);
        category.setCategoryName("Coffee");
        categories.add(category);
        category = new Category();
        category.setCategoryId(2);
        category.setCategoryName("milkTea");
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
    public void render(ArrayList<Product> products){
        products= (ArrayList<Product>) getDataProducts();
        int column=0;
        int row=1;
        if(products.size()>0){
            setDetails(products.get(0));
            myListener = new MyListener() {
                @Override
                public void onClickListener(Product product) {
                    setDetails(product);
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
    public void renderCategories() throws IOException {
        categoryList.addAll(getDataCategories());
        hboxCartegory.setStyle("-fx-effect:"+"dropShadow(three-pass-box, rgba(0,0,0,0.1),10.0,0.0,0.0,10.0);");
        hboxCartegory.getChildren().add(renderCategory(categoryList.get(0)));
        hboxCartegory.getChildren().add(renderCategory(categoryList.get(1)));

    }
    public void setDetails(Product product){
        ObservableList<String> observableArrayList = FXCollections.observableArrayList(product.getSizeList());
    txtProductNamedetails.setText(product.getProductName());
    cbSizePrice.setItems(observableArrayList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            renderCategories();
            render(productList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
