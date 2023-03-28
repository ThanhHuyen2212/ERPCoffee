package App.Model;

import Entity.Order;
import Entity.OrderDetail;

import java.sql.Date;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class OrderGUI {
    private Integer orderID;
    private String customerName;
    private Date date;
    private String totalPrice;
    private Order order;
    private ArrayList<OrderDetail> orderDetails;

    public OrderGUI(Order order){
        this.orderID=order.getOrderId();
        if(order.getCustomer()!=null){
            this.customerName=order.getCustomer().getFullName();
        }else{
            this.customerName="Alias";
        }
        this.date=order.getOrderDate();
        this.totalPrice= NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(order.getTotalPrice());
        this.orderDetails=order.getDetails();
        this.order=order;
    }
    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public ArrayList<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(ArrayList<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
