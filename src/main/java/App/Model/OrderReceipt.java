package App.Model;

import Entity.Order;
import Entity.OrderDetail;

import java.text.NumberFormat;
import java.util.Locale;

public class OrderReceipt {
    private String productName;
    private String size;
    private int quantity;
    private String priceUnit;
    private String total;
    private OrderDetail orderDetail;
    public OrderReceipt(OrderDetail orderDetail){
        this.productName="ProductName: "+orderDetail.getProduct().getProductName();
        this.size=orderDetail.getSize().getSign();
        this.priceUnit=NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(orderDetail.getProduct().getPrice(orderDetail.getSize().getSign()));
        this.quantity=orderDetail.getQty();
        this.total=NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(orderDetail.getProduct().getPrice(orderDetail.getSize().getSign())*orderDetail.getQty());
        this.orderDetail=orderDetail;
    }
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public OrderDetail getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
    }
}
