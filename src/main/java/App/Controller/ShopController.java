package App.Controller;
import App.Model.OrderTable;
import Entity.*;
import Logic.CategoryManagement;
import Logic.OrderManagement;
import Logic.ProductManagement;
import Logic.SizeManagement;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.StageStyle;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.*;
import java.util.List;

public class ShopController implements Initializable {
    @FXML
    private Button BtnSearch;
    @FXML
    private ImageView imgProduct;

    @FXML
    private TableColumn<OrderTable, String> NoColumn;

    @FXML
    private TableColumn<OrderTable, String> ProductColumn;

    @FXML
    private TextField Quality;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnCacncel;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnOrder;

    @FXML
    private ComboBox<String> cbSizePrice;

    @FXML
    private GridPane grid;

    @FXML
    private HBox hboxCartegory;

    @FXML
    private Button minusBtn;
    @FXML
    private HBox shopHBox;

    @FXML
    private TableView<OrderTable> orderDetailsTables;

    @FXML
    private Button customerBtn;

    @FXML
    private TableColumn<OrderTable, Integer> quantityColumn;

    @FXML
    private ScrollPane scroll;
    @FXML
    private Label labelCustomer;

    @FXML
    private TextField textSearch;

    @FXML
    private TableColumn<OrderTable, String> totalColumn;

    @FXML
    private Label txtPriceDetails;

    @FXML
    private Label txtProductNamedetails;

    @FXML
    private AnchorPane viewControl;
    @FXML
    private Label totalmoneyLabel;

    private MyListener myListener;
    private CategoryManagement categoryManagement = new CategoryManagement();
    private SizeManagement sizeManagement = new SizeManagement();
    private ProductManagement productManagement = new ProductManagement();
    private OrderManagement orderManagement = new OrderManagement();
    private List<Category> categoryList = new ArrayList<>();
    public static ArrayList<Product> productList = new ArrayList<>();
    public ArrayList<OrderDetail> orderList = new ArrayList<>();
    private List<Size> sizeList = new ArrayList<>();
    ObservableList<OrderTable> orderTableObservableList;
    public void getData(){
        categoryList = categoryManagement.getCategoriesList();
        sizeList=sizeManagement.getSizes();
        productList=productManagement.getProducts();
    }

    public ArrayList<OrderTable> getDataOrderTable(ArrayList<OrderDetail> orderList) {
        ArrayList<OrderTable> orderTables = new ArrayList<>();
        for (OrderDetail o : orderList) {
            OrderTable newOrderTable = new OrderTable(o);
            orderTables.add(newOrderTable);
        }
        if (orderTables.size() > 0) {
            System.out.println("co");
        } else {
            System.out.println("Khong");
        }
        return orderTables;

    }

    public void initTable(ArrayList<OrderTable> orderTables) {
        editOrderDetails();
        orderTableObservableList = FXCollections.observableArrayList(orderTables);
        NoColumn.setCellValueFactory(data->new SimpleStringProperty(String.valueOf(orderTables.indexOf(data.getValue())+1)));
        ProductColumn.setCellValueFactory(new PropertyValueFactory<OrderTable, String>("product"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<OrderTable, Integer>("quantity"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<OrderTable, String>("total"));
        orderDetailsTables.setItems(orderTableObservableList);
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

    public boolean checkProductChoose(OrderDetail orderDetail) {
        for (OrderDetail o : orderList) {
            if (orderDetail.getProduct() == o.getProduct() && o.getSize().getSign().equalsIgnoreCase(cbSizePrice.getValue())) {
                o.setQty(o.getQty() + Integer.parseInt(Quality.getText()));
                System.out.println(o.getProduct().getProductName() + "-" + o.getQty() + "-" + o.getSize().getSign());
                return true;
            }
        }
        orderList.add(orderDetail);
        return false;
    }

    public OrderDetail orderDetailsChoose(Product product, String sizeChoose) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProduct(product);
        int quantity = Integer.parseInt(Quality.getText());
        Size size = null;
        for (Size s : sizeList) {
            if (s.getSign().equalsIgnoreCase(sizeChoose)) {
                size = s;
            }
        }
        orderDetail.setQty(quantity);
        orderDetail.setSize(size);
        return orderDetail;
    }

    public void AddOrderDetails(OrderDetail product) {
        btnAdd.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {
                if (orderList.size() > 0) {
                    btnEdit.setDisable(true);
                    if (checkProductChoose(product)) {
                        System.out.println("San pham cu");
                    } else {
                        System.out.println("San pham moi");
                    }
                } else {
                    System.out.println("San pham moi 1");
                    orderList.add(product);
                }
                initTable(getDataOrderTable(orderList));
                orderDetailsTables.refresh();
                Integer sumTotal = 0;
                for (OrderDetail o : orderList) {
                    sumTotal += o.getProduct().getPrice(o.getSize().getSign()) * o.getQty();
                }

                totalmoneyLabel.setText(NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(sumTotal));
            }


        });

    }

    public void editOrderDetails() {
        orderDetailsTables.setRowFactory(tv -> {
            TableRow<OrderTable> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                btnAdd.setDisable(true);
                btnEdit.setDisable(false);
                if (e.getClickCount() == 1 && (!row.isEmpty())) {
                    OrderTable o = row.getItem();
                    setDetails(o.getOrderDetail());
                }
            });
            return row;
        });
    }

    public void printOrder(Order newOrder) {
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
            orderController.RenderOrder(newOrder);
            Optional<ButtonType> clickedButton = dialog.showAndWait();
            if (clickedButton.get() == ButtonType.NO) {
                dialog.close();
            } else if (clickedButton.get() == ButtonType.YES) {
                //code
            }

    }

    public void Event(Product product) {
        setDetails(product);
        changeSizePrice(product);

    }

    public void changeSizePrice(Product product) {
        cbSizePrice.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                int Price = product.getPrice(t1);
                txtPriceDetails.setText(NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(Price));
            }
        });
    }

    public void clearOrderList(ActionEvent e) {
        orderList.clear();
        orderTableObservableList.clear();
        orderDetailsTables.refresh();
    }

    public void addOrderChangeSize(Product product) {
        cbSizePrice.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                OrderDetail o = orderDetailsChoose(product, t1);
                AddOrderDetails(o);
                editOrderDetails();
            }


        });
    }

    public void render(ArrayList<Product> products) {
        //orderList.clear();
        Quality.setDisable(true);
        btnEdit.setDisable(true);
        btnCacncel.setDisable(true);
        int column = 0;
        int row = 1;
        if (products.size() > 0) {
            Event(products.get(0));
            OrderDetail o = orderDetailsChoose(products.get(0), cbSizePrice.getValue());
            AddOrderDetails(o);
            addOrderChangeSize(products.get(0));
            editOrderDetails();

            myListener = new MyListener() {
                @Override
                public void onClickListener(Product product) {
                    btnAdd.setDisable(false);
                    btnEdit.setDisable(true);
                    Quality.setText("1");
                    Event(product);
                    OrderDetail o = orderDetailsChoose(product, cbSizePrice.getValue());
                    AddOrderDetails(o);
                    addOrderChangeSize(product);
                    editOrderDetails();
                }
            };
        }
        try {
            if (products.size() == 0) {
                System.out.println("Khong co san pham nao!");
            } else {
                for (int i = 0; i < products.size(); i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(new File("src/main/java/App/View/item.fxml").toURI().toURL());
                    AnchorPane anchorPane = fxmlLoader.load();
                    itemController itemController = fxmlLoader.getController();
                    itemController.setData(products.get(i), myListener);
                    if (column == 3) {
                        column = 0;
                        row++;
                    }
                    grid.add(anchorPane, column++, row);
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

    public AnchorPane renderCategory(Category category, ArrayList<Product> products) {
        ArrayList<Product> productFilter;
        if(category.getCategoryName().equalsIgnoreCase("All")){
            productFilter=products;
        }else{
            productFilter = new ArrayList<>();
            for(Product p : products){
                if(p.getCategory().equalsIgnoreCase(category.getCategoryName())){
                    productFilter.add(p);
                }
            }
        }
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefHeight(200);
        HBox vbox = new HBox();
        vbox.setStyle(" -fx-alignment:" + " center; \n" +
                "-fx-border-insets:" + "5px; \n" +
                "-fx-background-insets:" + "5px; \n" +
                "-fx-border-radius:" + "30px; \n" +
                "-fx-background-radius:" + "30px; \n" +
                "-fx-background-color:" + "#feca57; \n" +
                "-fx-border-color:" + "#feca57; \n" +
                "-fx-alignment:" + "center; \n" +
                "fx-pref-height:" + "150px; \n" +
                "-fx-pref-width:" + "200px; \n"
        );
        Label label = new Label(category.getCategoryName());
        label.setStyle("-fx-font-size:" + "20px; \n" +
                "-fx-padding:" + "0 0 0 10px; \n");
        vbox.getChildren().add(label);
        anchorPane.getChildren().add(vbox);
        anchorPane.setOnMouseClicked(e -> {
            Quality.setText("1");
            grid.getChildren().clear();
            render(productFilter);
        });
        return anchorPane;
    }


    /**
     * Hiển thị các thể loại sản phẩm
     *
     * @throws IOException
     */
    public void renderCategories() throws IOException {
        hboxCartegory.setStyle("-fx-effect:" + "dropShadow(three-pass-box, rgba(0,0,0,0.1),10.0,0.0,0.0,10.0);");
        hboxCartegory.getChildren().add(renderCategory(new Category(0, "All"),productList));
        for (int i = 0; i < categoryList.size(); i++) {
            hboxCartegory.getChildren().add(renderCategory(categoryList.get(i),productList));
        }
    }

    /**
     * Hiển thị giao diện chi tiết sản phẩm
     *
     * @param product
     */
    public void setDetails(Product product) {
       ObservableList<String> observableArrayList = FXCollections.observableArrayList(product.getSizeList());
        txtProductNamedetails.setText(product.getProductName());
        javafx.scene.image.Image newImage;
        if(product != null){
            try {
                newImage = new Image(String.valueOf((new File("src/main/java/Assets/Images/"+product.getImagePath()).toURI()).toURL()));
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }else{
            try {
                newImage = new Image(String.valueOf((new File("src/main/java/Assets/Images/WhiteCoffee.png").toURI()).toURL()));
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
        imgProduct.setImage(newImage);
        cbSizePrice.setItems(observableArrayList);

        cbSizePrice.getSelectionModel().selectFirst();
        if (!cbSizePrice.getValue().isEmpty()) {
            txtPriceDetails.setText(NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(product.getPrice(cbSizePrice.getValue())));
        }
    }

    public void setDetails(OrderDetail orderDetail) {
        ObservableList<String> observableArrayList = FXCollections.observableArrayList(orderDetail.getProduct().getSizeList());
        txtProductNamedetails.setText(orderDetail.getProduct().getProductName());
        javafx.scene.image.Image newImage;
        if(orderDetail != null){
            try {
                newImage = new Image(String.valueOf((new File("src/main/java/Assets/Images/"+orderDetail.getProduct().getImagePath()).toURI()).toURL()));
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }else{
            try {
                newImage = new Image(String.valueOf((new File("src/main/java/Assets/Images/WhiteCoffee.png").toURI()).toURL()));
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
        imgProduct.setImage(newImage);
        cbSizePrice.setItems(observableArrayList);
        cbSizePrice.getSelectionModel().select(orderDetail.getSize().getSign());
        if (!cbSizePrice.getValue().isEmpty()) {
            txtPriceDetails.setText(NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(orderDetail.getProduct().getPrice(cbSizePrice.getValue())));
        }
        Quality.setText(String.valueOf(orderDetail.getQty()));
        btnEditEvent(orderDetail);

    }

    public void btnEditEvent(OrderDetail orderDetail) {
        btnEdit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (orderDetail != null) {
                    int quantity = Integer.parseInt(Quality.getText());
                    changeSizePrice(orderDetail.getProduct());
                    String sizeString = cbSizePrice.getValue();
                    Size size = null;
                    for (Size s : sizeList) {
                        if (s.getSign().equalsIgnoreCase(sizeString)) {
                            size = s;
                        }
                    }
                    if (orderDetail.getSize() != size || orderDetail.getQty() != quantity) {
                        for (OrderDetail od : orderList) {
                            if (od.getProduct() == orderDetail.getProduct() && od.getSize() == size) {
                                od.setQty(od.getQty() + quantity);
                                od.setSize(size);
                                orderList.remove(orderDetail);
                            } else if (od.getProduct() == orderDetail.getProduct() && od.getSize() != size) {
                                orderDetail.setSize(size);
                                orderDetail.setQty(quantity);
                            }
                        }
                        initTable(getDataOrderTable(orderList));
                        orderDetailsTables.refresh();
                        Integer sumTotal = 0;
                        for (OrderDetail o : orderList) {
                            sumTotal += o.getProduct().getPrice(o.getSize().getSign()) * o.getQty();
                        }

                        totalmoneyLabel.setText(NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(sumTotal));
                    }

                }
            }
        });
    }
    private void disLayCustomer(){
        customerBtn.setOnAction(actionEvent -> {
        FXMLLoader loader = new FXMLLoader();
        String orderFIle = "src/main/java/App/View/CustomerGUI.fxml";
        try {
            loader.setLocation(new File(orderFIle).toURI().toURL());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        Pane customerPane = null;
        try {
            customerPane = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Dialog<ButtonType> dialog = new Dialog<>();
        customerController customerController = loader.getController();
        dialog.setDialogPane((DialogPane) customerPane);
        dialog.initStyle(StageStyle.TRANSPARENT);

            shopHBox.setStyle("-fx-opacity:" + "0.5; \n");
            Optional<ButtonType> btn=dialog.showAndWait();
            if(btn.get()==ButtonType.YES){
                shopHBox.setStyle("-fx-opacity:" + "1; \n");
                Member member = customerController.getMember();
                labelCustomer.setText(member.getFullName());
            }else if(btn.get()==ButtonType.NO){
                shopHBox.setStyle("-fx-opacity:" + "1; \n");
                dialog.close();
            }


        });
    }

    public void searchProduct() {
        BtnSearch.setOnAction(e -> {
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
        ArrayList<Product> ProductsForSearch = new ArrayList<>();

            ProductsForSearch.clear();
            if (!textSearch.getText().equals("")) {
                String pattern = ".*" + textSearch.getText() + ".*";
                for (Product product : products) {
                    if (product.getProductName().toLowerCase().matches(pattern.toLowerCase())) {
                        ProductsForSearch.add(product);
                    }
                }
                grid.getChildren().clear();
                if (ProductsForSearch.size() > 0) {
                    render(ProductsForSearch);
                } else {
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
            } else {
                try {
                    alertController.RenderAlert("Warning", "Vui lòng nhập nội dung cần tìm kiếm!");
                } catch (MalformedURLException ex) {
                    throw new RuntimeException(ex);
                }
                Optional<ButtonType> clickedButton = dialog.showAndWait();
                if (clickedButton.get() == ButtonType.OK) {
                    dialog.close();
                }
            }

        });
    }
    private Order order(ArrayList<OrderDetail> orderList){
        Order newOder = new Order();
        customerController customerController = new customerController();
        String memberName= labelCustomer.getText();
        if(!memberName.isEmpty() || memberName.equalsIgnoreCase(" ")){
            Member member =customerController.findByName(memberName);
           newOder= orderManagement.insertOrder(orderList,member);
        }else{
            newOder= orderManagement.insertOrder(orderList);
        }
        return newOder;
    }
    private void orderDialog(){
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(new File("src/main/java/App/View/Alert2.fxml").toURI().toURL());
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
        Alert2Controller controller = loader.getController();
        dialog.setDialogPane((DialogPane) alert);
        try {
            controller.RenderAlert("Success", "Bạn chắc chắn muốn thanh toán!!!");
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        }
        Optional<ButtonType> clickedButton = dialog.showAndWait();
        if (clickedButton.get() == ButtonType.OK) {
            OrderGUIController orderGUIController =new OrderGUIController();
            Order order = order(orderList);
            orderGUIController.reload(order);
            printOrder( order);
            orderList.clear();
        }else if(clickedButton.get()==ButtonType.CANCEL){
            dialog.close();
        }
    }
    private void orderEvent(){
        btnOrder.setOnAction(e->{
            if(orderList.size()>0){
                orderDialog();
            }else {
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
                try {
                    alertController.RenderAlert("Warning", "Chưa chọn sản phẩm cần thanh toán!!!");
                } catch (MalformedURLException ex) {
                    throw new RuntimeException(ex);
                }
                Optional<ButtonType> clickedButton = dialog.showAndWait();
                if (clickedButton.get() == ButtonType.OK) {
                    dialog.close();
                }
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getData();
            render(productList);
            searchProduct();
            renderCategories();
            disLayCustomer();
            orderEvent();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
