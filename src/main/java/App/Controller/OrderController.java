package App.Controller;

import App.Model.OrderReceipt;
import App.Model.OrderTable;
import Entity.Order;
import Entity.OrderDetail;
import Logic.OrderManagement;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class OrderController implements Initializable{
    @FXML
    private Label CustomerLabel;
    @FXML
    private TableView<OrderReceipt> OrderDetailsTable;


    @FXML
    private TableColumn<OrderReceipt, String> NoTableColunm;

    @FXML
    private Label PhoneLabel;

    @FXML
    private TableColumn<OrderReceipt, String> PriceColumn;

    @FXML
    private TableColumn<OrderReceipt, String> ProductColunm;

    @FXML
    private TableColumn<OrderReceipt, Integer> QuantityColmn;

    @FXML
    private Label ReceiveLabel;

    @FXML
    private TableColumn<OrderReceipt, String> SizeColum;

    @FXML
    private TableColumn<OrderReceipt, String> TotalColumn;

    @FXML
    private Label TotalPriceLabel;

    @FXML
    private Label cashierLabel;

    @FXML
    private Label changeLabel;
    @FXML
    private Label orderIdLabel;

    @FXML
    private Label dateLabel;



    ObservableList<OrderReceipt> orderTableObservableList;

    public void initTable(ArrayList<OrderDetail> orderDetails) {
        ArrayList<OrderReceipt> orderReceipts = changeToOrderDetails(orderDetails);
        orderTableObservableList = FXCollections.observableArrayList(orderReceipts);
        NoTableColunm.setCellValueFactory(data->new SimpleStringProperty(String.valueOf(orderReceipts.indexOf(data.getValue())+1)));
        ProductColunm.setCellValueFactory(new PropertyValueFactory<OrderReceipt, String>("productName"));
        PriceColumn.setCellValueFactory(new PropertyValueFactory<OrderReceipt, String>("priceUnit"));
        QuantityColmn.setCellValueFactory(new PropertyValueFactory<OrderReceipt, Integer>("quantity"));
        SizeColum.setCellValueFactory(new PropertyValueFactory<OrderReceipt, String>("size"));
        TotalColumn.setCellValueFactory(new PropertyValueFactory<OrderReceipt,String>("total"));
        OrderDetailsTable.setItems(orderTableObservableList);
    }
    public ArrayList<OrderReceipt> changeToOrderDetails(ArrayList<OrderDetail> orderDetails){
        ArrayList<OrderReceipt> orderReceipts = new ArrayList<>();
        for (OrderDetail o : orderDetails) {
            OrderReceipt newOrderReceipt= new OrderReceipt(o);
            orderReceipts.add(newOrderReceipt);
        }
        return orderReceipts;
    }

    public void RenderOrder(Order order, String payCustomer, String returnMoney){
        initTable(order.getDetails());
        if(order.getCustomer()==null){
            CustomerLabel.setText("Alias");
            PhoneLabel.setText("Alias");
        }else{
            CustomerLabel.setText(order.getCustomer().getFullName());
            PhoneLabel.setText(order.getCustomer().getPhoneNumber());
        }
        TotalPriceLabel.setText(NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(order.getTotalPrice()));
        dateLabel.setText(String.valueOf(order.getOrderDate()));
        orderIdLabel.setText("#"+String.valueOf(order.getOrderId()));
        ReceiveLabel.setText(payCustomer);
        changeLabel.setText(returnMoney);
    }

    public void RenderOrder(Order order){
        initTable(order.getDetails());
        System.out.println("SizeOrderDetails:"+order.getDetails().size());
        if(order.getCustomer()==null){
            CustomerLabel.setText("Alias");
            PhoneLabel.setText("Alias");
        }else{
            CustomerLabel.setText(order.getCustomer().getFullName());
            PhoneLabel.setText(order.getCustomer().getPhoneNumber());
        }
        TotalPriceLabel.setText(NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(order.getTotalPrice()));
        dateLabel.setText(String.valueOf(order.getOrderDate()));
        orderIdLabel.setText("#"+String.valueOf(order.getOrderId()));
        ReceiveLabel.setText("payCustomer");
        changeLabel.setText("returnMoney");
    }
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

        }

}
