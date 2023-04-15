package App.Controller;

import App.Model.OrderGUI;
import App.Model.OrderTable;
import App.ModuleManager.AppControl;
import Entity.*;
import Logic.*;
import Logic.Depot.ProductPreparationManagement;
import Main.MainApp;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.StageStyle;
import javafx.util.Callback;

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
    private Button btnApply;
    @FXML
    private ImageView imgProduct;
    @FXML
    private HBox hbCustomer;

    @FXML
    private TableColumn<OrderTable, String> NoColumn;

    @FXML
    private TableColumn<OrderTable, String> ProductColumn;
    @FXML
    private TableColumn<OrderTable, Void> DeleteColum;
    @FXML
    private TextField txtCustomerpay;
    @FXML
    private Label returnMoneyLabel;
    @FXML
    private TextField Quality;

    @FXML
    private Button btnAdd;
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
    private HBox shopHBox;
    @FXML
    private HBox Hbdiscount;

    @FXML
    private TableView<OrderTable> orderDetailsTables;

    @FXML
    private Button customerBtn;

    @FXML
    private TableColumn<OrderTable, Integer> quantityColumn;

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
    private Label totalmoneyLabel;

    ComboBox<String> cbPoint = new ComboBox<>();
    Label labelInfo = new Label();
    Label price = new Label();
    Button btnClose = new Button("x");

    private MyListener myListener;
    private CategoryManagement categoryManagement = new CategoryManagement();
    private SizeManagement sizeManagement = new SizeManagement();
    private ProductManagement productManagement = new ProductManagement();
    private OrderManagement orderManagement = new OrderManagement();
    private List<Category> categoryList = new ArrayList<>();
    public ArrayList<Product> productList = new ArrayList<>();
    public ArrayList<OrderDetail> orderList = new ArrayList<>();
    private List<Size> sizeList = new ArrayList<>();
    public HashMap<Integer, Integer> preListTemp = new HashMap<>();
    ObservableList<OrderTable> orderTableObservableList;

    public void getData() {
        categoryList = categoryManagement.getCategoriesList();
        sizeList = sizeManagement.getSizes();
        productList = productManagement.getProducts();
    }

    public ArrayList<OrderTable> getDataOrderTable(ArrayList<OrderDetail> orderList) {
        ArrayList<OrderTable> orderTables = new ArrayList<>();
        for (OrderDetail o : orderList) {
            OrderTable newOrderTable = new OrderTable(o);
            orderTables.add(newOrderTable);
        }
        return orderTables;

    }

    public void initTable(ArrayList<OrderTable> orderTables) {
        editOrderDetails();
        orderTableObservableList = FXCollections.observableArrayList(orderTables);
        NoColumn.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(orderTables.indexOf(data.getValue()) + 1)));
        ProductColumn.setCellValueFactory(new PropertyValueFactory<OrderTable, String>("product"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<OrderTable, Integer>("quantity"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<OrderTable, String>("total"));
        Callback<TableColumn<OrderTable, Void>, TableCell<OrderTable, Void>> cellFactory = new Callback<TableColumn<OrderTable, Void>, TableCell<OrderTable, Void>>() {
            @Override
            public TableCell<OrderTable, Void> call(final TableColumn<OrderTable, Void> param) {
                final TableCell<OrderTable, Void> cell = new TableCell<OrderTable, Void>() {

                    private final Button btn = new Button("Delete");

                    {

                        btn.setOnAction((ActionEvent event) -> {
                            OrderTable data = getTableView().getItems().get(getIndex());
                            orderList.remove(data.getOrderDetail());
                            orderTables.remove(data);
                            orderTableObservableList.remove(data);
                            orderDetailsTables.refresh();
                            Integer sumTotal = 0;
                            for (OrderDetail o : orderList) {
                                sumTotal += o.getProduct().getPrice(o.getSize().getSign()) * o.getQty();
                            }
                            preListTemp.put(data.getOrderDetail().getProduct().getProductId(), 0);
                            MemberManagement memberManagement = new MemberManagement();

                            if(memberManagement.findByName(labelCustomer.getText()).getPoint()>10){
                                if(!price.getText().equalsIgnoreCase("")){
                                    if(sumTotal-Integer.parseInt(price.getText())>0){
                                        totalmoneyLabel.setText(String.valueOf(sumTotal-Integer.parseInt(price.getText())));
                                    }else{
                                        totalmoneyLabel.setText("0");
                                    }
                                }
                            }else {
                                totalmoneyLabel.setText(String.valueOf(sumTotal));
                            }

                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        DeleteColum.setCellFactory(cellFactory);
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

    public void showDialogAlert(Product product) {
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
            alertController.RenderAlert("Warning", product.getProductName() + " không đủ số lượng để bán");
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        }
        Optional<ButtonType> clickedButton = dialog.showAndWait();
        if (clickedButton.get() == ButtonType.OK) {
            dialog.close();
        }

    }

    //minusBtn
    public void Minus(ActionEvent e) {
        int inputValue = Integer.parseInt(Quality.getText());
        if (inputValue > 1) {
            inputValue -= 1;
            Quality.setText(String.valueOf(inputValue));
        }
    }

    public boolean checkPreList(OrderDetail orderDetail) {
        Integer currentTarget = orderDetail.getProduct().getProductId();
//        if(ProductPreparationManagement.preparedList.containsKey(currentTarget)
//                && preListTemp.containsKey(currentTarget)){
//            System.out.println("prepare:");
//                    System.out.println(ProductPreparationManagement.preparedList.get(currentTarget));
//                    System.out.println("choice:");
//            System.out.println(orderDetail.getQty());
//            System.out.println(orderDetail.getProduct().getVle(orderDetail.getSize()));
//                    System.out.println(orderDetail.getProduct().getVle(orderDetail.getSize()) * Integer.parseInt(Quality.getText())
//                            + preListTemp.get(currentTarget));
//        }


        return (ProductPreparationManagement.preparedList.containsKey(currentTarget)
                && !preListTemp.containsKey(currentTarget))
                || (ProductPreparationManagement.preparedList.containsKey(currentTarget)
                && ProductPreparationManagement.preparedList.get(currentTarget)
                >= orderDetail.getProduct().getVle(orderDetail.getSize()) * Integer.parseInt(Quality.getText())
                + preListTemp.get(currentTarget));
    }

    public boolean checkPreList(Product product, String size, Integer quantity) {
        Integer newVle = product.getVle(size) * quantity;
        OrderDetail oldSelected = orderDetailsTables.getSelectionModel().getSelectedItem().getOrderDetail();
        Integer oldVle = oldSelected.getProduct().getVle(oldSelected.getSize()) * oldSelected.getQty();
        if (ProductPreparationManagement.preparedList.get(oldSelected.getProduct().getProductId())
                >= preListTemp.get(oldSelected.getProduct().getProductId()) - oldVle + newVle) {
            preListTemp.replace(oldSelected.getProduct().getProductId(), preListTemp.get(oldSelected.getProduct().getProductId()) - oldVle + newVle);
            System.out.println("true");
            System.out.println("prepare");
            System.out.println(ProductPreparationManagement.preparedList.get(oldSelected.getProduct().getProductId()));
            System.out.println("Choice:");
            System.out.println(oldVle);
            System.out.println(newVle);
            System.out.println(preListTemp.get(oldSelected.getProduct().getProductId()) - oldVle + newVle);
            return true;
        }
        System.out.println("false");
        System.out.println("prepare");
        System.out.println(ProductPreparationManagement.preparedList.get(oldSelected.getProduct().getProductId()));
        System.out.println("Choice:");
        System.out.println(oldVle);
        System.out.println(newVle);
        System.out.println(preListTemp.get(oldSelected.getProduct().getProductId()) - oldVle + newVle);
        return false;
//
//        if (preListTemp.containsKey(product.getProductId())) {
//            newVle = product.getVle(size);
//            oldVle = preListTemp.get(product.getProductId());
//            preListTemp.put(product.getProductId(), newVle * quantity);
//            if (ProductPreparationManagement.preparedList.containsKey(product.getProductId())
//                    && preListTemp.get(product.getProductId()) <= ProductPreparationManagement.preparedList.get(product.getProductId())) {
//                return true;
//            } else {
//                preListTemp.put(product.getProductId(), oldVle);
//            }
//        }
//        showDialogAlert(product);
//        return false;
    }

    public boolean checkProductChoose(OrderDetail orderDetail) {
        boolean existed = false;
        Integer newVl = 0;
        if (checkPreList(orderDetail)) {
            for (OrderDetail o : orderList) {
                if (orderDetail.getProduct() == o.getProduct()) {
                    if (o.getSize().getSign().equalsIgnoreCase(cbSizePrice.getValue())) {
                        System.out.println(o);
                        System.out.println(orderDetail);
                        o.setQty(o.getQty() + Integer.parseInt(Quality.getText()));
//                        preListTemp.replace(o.getProduct().getProductId(),
//                                (orderDetail.getQty()*orderDetail.getProduct().getVle(orderDetail.getSize()))
//                                        +o.getQty() * o.getProduct().getVle(o.getSize()));
                        existed = true;
                    }
                }
            }
            if (!existed) {
                if (ProductPreparationManagement.preparedList.containsKey(orderDetail.getProduct().getProductId())
                        && orderDetail.getQty() * orderDetail.getProduct().getVle(orderDetail.getSize()) <= ProductPreparationManagement.preparedList.get(orderDetail.getProduct().getProductId())) {
                    orderDetail.setQty(Integer.parseInt(Quality.getText()));
                    orderList.add(orderDetail);
                }

            }
            if (preListTemp.containsKey(orderDetail.getProduct().getProductId())) {
                preListTemp.replace(orderDetail.getProduct().getProductId(),
                        preListTemp.get(orderDetail.getProduct().getProductId())
                                + Integer.parseInt(Quality.getText()) * orderDetail.getProduct().getVle(orderDetail.getSize()));
            } else {
                preListTemp.put(orderDetail.getProduct().getProductId(), orderDetail.getQty() * orderDetail.getProduct().getVle(orderDetail.getSize()));

            }
        } else {
            showDialogAlert(orderDetail.getProduct());
        }
        return existed;
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
                btnEdit.setDisable(true);
                //product.setQty(Integer.parseInt(Quality.getText()));
                checkProductChoose(product);
                initTable(getDataOrderTable(orderList));
                orderDetailsTables.refresh();
                Integer sumTotal = 0;
                for (OrderDetail o : orderList) {
                    sumTotal += o.getProduct().getPrice(o.getSize().getSign()) * o.getQty();
                }
                totalmoneyLabel.setText(String.valueOf(sumTotal));
                MemberManagement memberManagement = new MemberManagement();
                if(labelCustomer!=null && memberManagement.findByName(labelCustomer.getText()).getPoint()>10){
                    if(!price.getText().equalsIgnoreCase("")){
                        if(sumTotal-Integer.parseInt(price.getText())>0){
                            totalmoneyLabel.setText(String.valueOf(sumTotal-Integer.parseInt(price.getText())));
                        }else{
                            totalmoneyLabel.setText("0");
                        }
                    }
                }else {
                    totalmoneyLabel.setText(String.valueOf(sumTotal));
                }

            }
        });
    }

//    public void AddOrderDetails(ArrayList<Product> products) {
//        btnAdd.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                btnEdit.setDisable(true);
////                product.setQty(Integer.parseInt(Quality.getText()));
//                OrderDetail tmp = orderDetailsChoose(products.get(0), cbSizePrice.getValue());
//                tmp.setQty(Integer.parseInt(Quality.getText()));
//                checkProductChoose(tmp);
//                initTable(getDataOrderTable(orderList));
//                orderDetailsTables.refresh();
//                Integer sumTotal = 0;
//                for (OrderDetail o : orderList) {
//                    sumTotal += o.getProduct().getPrice(o.getSize().getSign()) * o.getQty();
//                }
//                totalmoneyLabel.setText(NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(sumTotal));
//            }
//        });
//    }

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

    public void printOrder(Order newOrder, String payCustomer, String returnMoney, String casier) {
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
        orderController.RenderOrder(newOrder, payCustomer, returnMoney,casier);
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
        btnClose();

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
        preListTemp.clear();
        orderList.clear();
        orderTableObservableList.clear();
        orderDetailsTables.refresh();
        totalmoneyLabel.setText("0");
        txtCustomerpay.setText("0");
        returnMoneyLabel.setText("0");
        labelCustomer.setText("");
        Hbdiscount.getChildren().removeAll();
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
//                    AddOrderDetails(products);
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
                    GridPane.setMargin(anchorPane, new Insets(10));
                    GridPane.setVgrow(anchorPane, Priority.ALWAYS);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public AnchorPane renderCategory(Category category, ArrayList<Product> products) {
        ArrayList<Product> productFilter;
        if (category.getCategoryName().equalsIgnoreCase("All")) {
            productFilter = products;
        } else {
            productFilter = new ArrayList<>();
            for (Product p : products) {
                if (p.getCategory().equalsIgnoreCase(category.getCategoryName())) {
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
        hboxCartegory.getChildren().add(renderCategory(new Category(0, "All"), productList));
        for (int i = 0; i < categoryList.size(); i++) {
            hboxCartegory.getChildren().add(renderCategory(categoryList.get(i), productList));
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
        try {
            newImage = new Image(String.valueOf((new File("src/main/java/Assets/Images/" + product.getImagePath()).toURI()).toURL()));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
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
        if (orderDetail != null) {
            try {
                newImage = new Image(String.valueOf((new File("src/main/java/Assets/Images/" + orderDetail.getProduct().getImagePath()).toURI()).toURL()));
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        } else {
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
                    if (orderDetail.getSize() != size) {
                        for (OrderDetail o : orderList) {
                            if (o.getProduct() == orderDetail.getProduct()) {
                                if (o.getSize() == size) {
                                    if (checkPreList(o.getProduct(), size.getSign(), quantity)) {
                                        o.setQty(o.getQty() + quantity);
                                        orderList.remove(orderDetail);
                                    } else {
                                        showDialogAlert(orderDetail.getProduct());
                                    }
                                } else {
                                    if (checkPreList(orderDetail.getProduct(), size.getSign(), quantity)) {
                                        orderDetail.setSize(size);
                                        orderDetail.setQty(quantity);
                                    } else {
                                        showDialogAlert(orderDetail.getProduct());
                                    }
                                }
                                break;
                            }
                        }
                    } else if (orderDetail.getSize() == size) {
                        for (OrderDetail o : orderList) {
                            if (orderDetail.getProduct() == o.getProduct()) {
                                if (orderDetail.getQty() != quantity) {
                                    if (checkPreList(orderDetail.getProduct(), orderDetail.getSize().getSign(), quantity)) {
                                        orderDetail.setQty(quantity);
                                    } else {
                                        showDialogAlert(orderDetail.getProduct());
                                    }
                                }
                            }
                        }
                    }
                    initTable(getDataOrderTable(orderList));
                    orderDetailsTables.refresh();
                    Integer sumTotal = 0;
                    for (OrderDetail o : orderList) {
                        sumTotal += o.getProduct().getPrice(o.getSize().getSign()) * o.getQty();
                    }
                    MemberManagement memberManagement = new MemberManagement();
                    if(memberManagement.findByName(labelCustomer.getText()).getPoint()>10){
                        if(!price.getText().equalsIgnoreCase("")){
                            if(sumTotal-Integer.parseInt(price.getText())>0){
                                totalmoneyLabel.setText(String.valueOf(sumTotal-Integer.parseInt(price.getText())));
                            }else{
                                totalmoneyLabel.setText("0");
                            }
                        }
                    }else {
                        totalmoneyLabel.setText(String.valueOf(sumTotal));
                    }


                }

            }
            //}
        });
    }

    private void pointOfMember(ArrayList<OrderDetail> orderList) {
        cbPoint.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (t1 != null) {
                    price.setText(String.valueOf(Integer.parseInt(t1) * 5000));
                    Integer sumTotal = 0;
                    for (OrderDetail o : orderList) {
                        sumTotal += o.getProduct().getPrice(o.getSize().getSign()) * o.getQty();
                    }
                    if (sumTotal - Integer.parseInt(t1) * 5000 > 0) {
                        totalmoneyLabel.setText(String.valueOf(sumTotal - Integer.parseInt(t1) * 5000));
                    } else {
                        totalmoneyLabel.setText("0");
                    }


                }
            }
        });
    }

    Pane newPane1 = new Pane();
    Pane newPane = new Pane();
    Pane newPane2 = new Pane();

    private void setDiscount(Member member) {
        MemberManagement memberManagement = new MemberManagement();
        if (memberManagement.PointOfMember(member).size() < 1) {
            labelInfo.setText("Bạn chưa đủ điều kiện");
            /**
             * -fx-text-fill: #fff;
             *     -fx-font-size: 16;
             *     -fx-font-weight: 700;
             */
            labelInfo.setStyle("-fx-text-fill:" + "#eb2f06; \n" +
                    "-fx-font-size:" + "18; \n" +
                    "-fx-font-weight:" + "700; \n");
            Hbdiscount.getChildren().add(labelInfo);
        } else {
            ObservableList<String> observableArrayList = FXCollections.observableArrayList(memberManagement.PointOfMember(member));
            cbPoint.setItems(observableArrayList);
            Hbdiscount.setStyle("-fx-pref-width:"+"450px;\n"+
                    "fx-pref-height:"+"150px;\n"+
                    "-fx-alignment:" + "center; \n"+
                    "-fx-border-radius:" + "50px; \n" +
                    "-fx-background-radius:" + "50px; \n" +
                    "-fx-background-color:" + "#dff9fb; \n" +
                    "-fx-border-color:" + "#dff9fb; \n"
            );
            newPane1.setStyle("-fx-pref-width:" + "20px; \n");
            Hbdiscount.getChildren().add(newPane1);
            cbPoint.getSelectionModel().selectFirst();
            cbPoint.setStyle("-fx-pref-width:" + "100px; \n" +
                    "-fx-pref-height:" + "50px; \n"+
                    "-fx-alignment:" + "center; \n"+
                    "-fx-border-radius:" + "10px; \n" +
                    "-fx-background-radius:" + "10px; \n" +
                    "-fx-background-color:" + "#f9ca24; \n"+
                    "-fx-text-fill:" + "#fff; \n"
                 );
            Hbdiscount.getChildren().add(cbPoint);
            price.setStyle("-fx-text-fill:" + "#c0392b; \n" +
                    "-fx-pref-width:" + "100px; \n" +
                    "-fx-font-size:" + "20; \n" +
                    "-fx-font-weight:" + "700; \n");
            price.setText(String.valueOf(Integer.parseInt(cbPoint.getValue()) * 5000));
            Integer sumTotal = 0;
            for (OrderDetail o : orderList) {
                sumTotal += o.getProduct().getPrice(o.getSize().getSign()) * o.getQty();
            }
            if (sumTotal - Integer.parseInt(cbPoint.getValue()) * 5000 > 0) {
                totalmoneyLabel.setText(String.valueOf(sumTotal - Integer.parseInt(cbPoint.getValue()) * 5000));
            } else {
                totalmoneyLabel.setText("0");
            }
            newPane2.setStyle("-fx-pref-width:" + "80px; \n");
            Hbdiscount.getChildren().add(newPane2);
            Hbdiscount.getChildren().add(price);
            newPane.setStyle("-fx-pref-width:" + "100px; \n");
            Hbdiscount.getChildren().add(newPane);
            btnClose.setStyle("-fx-border-insets:" + "5px; \n" +
                    "-fx-background-insets:" + "5px; \n" +
                    "-fx-border-radius:" + "50%; \n" +
                    "-fx-background-radius:" + "50%; \n" +
                    "-fx-background-color:" + "#eb2f06; \n" +
                    "-fx-border-color:" + "#eb2f06; \n" +
                    "-fx-alignment:" + "center; \n" +
                    "fx-pref-height:" + "20px; \n" +
                    "-fx-pref-width:" + "20px; \n" +
                    "-fx-font-size:" + "15; \n" +
                    "-fx-text-fill:" + "#fff; \n"+
                    "-fx-font-weight:" + "700;\n");
            Hbdiscount.getChildren().add(btnClose);

        }
    }

    public void clearHbDiscount() {
        price.setText("");
        Hbdiscount.getChildren().remove(labelInfo);
        Hbdiscount.getChildren().remove(cbPoint);
        Hbdiscount.getChildren().remove(price);
        Hbdiscount.getChildren().remove(newPane);
        Hbdiscount.getChildren().remove(newPane1);
        Hbdiscount.getChildren().remove(newPane2);
        Hbdiscount.getChildren().remove(btnClose);
        Hbdiscount.setStyle("");
    }

    private void btnClose() {
        MemberManagement memberManagement = new MemberManagement();
        btnClose.setOnAction(e -> {
            Integer sumTotal = 0;
            for (OrderDetail o : orderList) {
                sumTotal += o.getProduct().getPrice(o.getSize().getSign()) * o.getQty();
            }
            if(labelCustomer!=null && memberManagement.findByName(labelCustomer.getText()).getPoint()>10){
                if(!price.getText().equalsIgnoreCase("")){
                    if(sumTotal-Integer.parseInt(price.getText())>0){
                        totalmoneyLabel.setText(String.valueOf(sumTotal-Integer.parseInt(price.getText())));
                    }else{
                        totalmoneyLabel.setText("0");
                    }
                }
            }else {
                totalmoneyLabel.setText(String.valueOf(sumTotal));
            }
            clearHbDiscount();
        });
    }

    private void setBtnApply() {
        btnApply.setOnAction(e -> {
            Integer payCustomer = null;
            if (txtCustomerpay != null || !txtCustomerpay.getText().equalsIgnoreCase(null)) {
                payCustomer = Integer.parseInt(txtCustomerpay.getText());
            }
            if (payCustomer >= Integer.parseInt(totalmoneyLabel.getText()))
                returnMoneyLabel.setText(String.valueOf(payCustomer - Integer.parseInt(totalmoneyLabel.getText())));

        });
    }

    Button btnDeleteCustomer = new Button("x");

    private void disLayCustomer() {
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
            MemberManagement memberManagement = new MemberManagement();
            shopHBox.setStyle("-fx-opacity:" + "0.5; \n");
            Optional<ButtonType> btn = dialog.showAndWait();
            if (btn.get() == ButtonType.YES) {
                hbCustomer.getChildren().remove(btnDeleteCustomer);
                shopHBox.setStyle("-fx-opacity:" + "1; \n");
                Member member = customerController.getMember();

                if (member == null) {
                    labelCustomer.setText("Alias");
                } else {
                    if(orderList.size()>0){
                        Integer sumTotal = 0;
                        for (OrderDetail o : orderList) {
                            sumTotal += o.getProduct().getPrice(o.getSize().getSign()) * o.getQty();
                        }
                        if (memberManagement.findByName(labelCustomer.getText()).getPoint() > 10) {
                            if (sumTotal - Integer.parseInt(price.getText()) > 0) {
                                totalmoneyLabel.setText(String.valueOf(sumTotal - Integer.parseInt(price.getText()) * 5000));
                            } else {
                                totalmoneyLabel.setText("0");
                            }
                        } else {
                            totalmoneyLabel.setText(String.valueOf(sumTotal));
                        }
                    }

                    clearHbDiscount();
                    labelCustomer.setText(member.getFullName());
                    btnDeleteCustomer.setStyle("-fx-border-insets:" + "5px; \n" +
                            "-fx-background-insets:" + "5px; \n" +
                            "-fx-border-radius:" + "50%; \n" +
                            "-fx-background-radius:" + "50%; \n" +
                            "-fx-background-color:" + "#eb2f06; \n" +
                            "-fx-border-color:" + "#eb2f06; \n" +
                            "-fx-alignment:" + "center; \n" +
                            "fx-pref-height:" + "20px; \n" +
                            "-fx-pref-width:" + "20px; \n" +
                            "-fx-font-size:" + "15; \n" +
                            "-fx-text-fill:" + "#fff; \n"+
                            "-fx-font-weight:" + "700;\n");
                    hbCustomer.getChildren().add(btnDeleteCustomer);
                    btnDeleteCustomer.setOnAction(e -> {
                        Integer sumTotal = 0;
                        for (OrderDetail o : orderList) {
                            sumTotal += o.getProduct().getPrice(o.getSize().getSign()) * o.getQty();
                        }
                        totalmoneyLabel.setText(String.valueOf(sumTotal));
                        price.setText("");
                        labelCustomer.setText("");
                        hbCustomer.getChildren().remove(btnDeleteCustomer);
                        clearHbDiscount();
                    });
                    Integer sum = 0;
                    for (OrderDetail o : orderList) {
                        sum += o.getProduct().getPrice(o.getSize().getSign()) * o.getQty();
                    }
                    totalmoneyLabel.setText(String.valueOf(sum));
                    setDiscount(member);
                    pointOfMember(orderList);
                    btnClose();
                }
            } else if (btn.get() == ButtonType.NO) {
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

    private Order order(ArrayList<OrderDetail> orderList, Member member, Integer value) {
        Order newOder;

        newOder = orderManagement.insertOrder(orderList, member, value);
        return newOder;
    }

    private Order order(ArrayList<OrderDetail> orderList) {
        Order newOder;

        customerController customerController = new customerController();
        newOder = orderManagement.insertOrder(orderList);
        return newOder;
    }

    private void orderDialog() {
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
            OrderGUIController orderGUIController = new OrderGUIController();
            Order order = null;
            if (labelCustomer.getText().isEmpty() || labelCustomer.getText().equalsIgnoreCase("") || labelCustomer == null) {
                order = order(orderList);
            } else {
                MemberManagement memberManagement = new MemberManagement();
                order = order(orderList,
                        memberManagement.findByName(labelCustomer.getText()),
                        Integer.parseInt(totalmoneyLabel.getText()));
                if (memberManagement.findByName(labelCustomer.getText()).getPoint() > 10) {
                    memberManagement.updateSubPoint(memberManagement.findByName(labelCustomer.getText()), Integer.parseInt(cbPoint.getValue()));
                }
            }

            setBtnApply();
            OrderGUIController.orderGUIList.add(new OrderGUI(order));
            printOrder(order, txtCustomerpay.getText(), returnMoneyLabel.getText(), AppControl.currentUser.getName());
            preListTemp.forEach((k, v) -> {
                ProductPreparationManagement.preparedList.replace(k, ProductPreparationManagement.preparedList.get(k) - v);
            });
            preListTemp.clear();
            orderList.clear();
            orderTableObservableList.clear();
            orderDetailsTables.refresh();
            totalmoneyLabel.setText("0");
            txtCustomerpay.setText("0");
            returnMoneyLabel.setText("0");
            labelCustomer.setText("");
            Hbdiscount.getChildren().removeAll();
            hbCustomer.getChildren().remove(btnDeleteCustomer);
        } else if (clickedButton.get() == ButtonType.CANCEL) {
            dialog.close();
        }
    }

    private void orderEvent() {
        btnOrder.setOnAction(e -> {
            if (orderList.size() > 0) {
                orderDialog();
            } else {
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
            setBtnApply();
            orderEvent();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}