package App.Model;

import Entity.OrderDetail;

import java.text.NumberFormat;
import java.util.Locale;

public class OrderTable {
    private int No;
    private String product;
    private int quantity;
    private String total;
    public OrderTable (OrderDetail orderDetail){
        this.product="ProductName: "+orderDetail.getProduct().getProductName()+"\n"
                    +"Price Unit: "+ NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(orderDetail.getProduct().getPrice(orderDetail.getSize().getSign()))+"\n"
                    +"Size: "+orderDetail.getSize().getSign()+"\n";
        this.quantity=orderDetail.getQty();
        this.total=NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(orderDetail.getProduct().getPrice(orderDetail.getSize().getSign())*quantity);

    }

    public int getNo() {
        return No;
    }

    public void setNo(int no) {
        No = no;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
