package Entity;

import java.sql.Date;
import java.util.ArrayList;

public class Order {
    private int orderId;
    private int totalPrice;
    private Date orderDate;
    private Member customer;

    private ArrayList<OrderDetail> details;

    public Order() {
    }

    public Order(int orderId, int totalPrice, Date orderDate, Member customer, ArrayList<OrderDetail> details) {
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.customer = customer;
        this.details = details;
    }
    public Order(int orderId, int totalPrice, Date orderDate, ArrayList<OrderDetail> details) {
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.details = details;
    }
    public Order(int orderId, int totalPrice, Date orderDate) {
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Member getCustomer() {
        return customer;
    }

    public void setCustomer(Member customer) {
        this.customer = customer;
    }

    public ArrayList<OrderDetail> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<OrderDetail> details) {
        this.details = details;
    }

    public void addProduct(Product product,Size size, int qty){
        OrderDetail tmp = new OrderDetail(product,size,qty);
        boolean existed = false;
        for(OrderDetail od : details){
            if(od.equals(tmp)){
                od.setQty(od.getQty() + tmp.getQty());
                existed = true;
            }
        }
        if(!existed){
            details.add(tmp);
        }
    }
}
