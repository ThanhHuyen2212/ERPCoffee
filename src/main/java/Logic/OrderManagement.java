package Logic;

import DAL.OrderAccess;
import Entity.Member;
import Entity.Order;
import Entity.OrderDetail;

import java.util.ArrayList;

public class OrderManagement {
    private ArrayList<Order> orders;
    OrderAccess orderAccess;
    public OrderManagement(){
        orderAccess = new OrderAccess();
        orders= orderAccess.retrieve();
    }
    public ArrayList<Order> getOrderList() {
        return orders;
    }
    public ArrayList<OrderDetail> findDetailsByOrderId(){
        return null;
    }
    public Order insertOrder(ArrayList<OrderDetail> orderDetails, Member member){
        Order order = new Order();
        order.setDetails(orderDetails);
        int sum=0;
        for(OrderDetail p: orderDetails){
            sum+=(p.getProduct().getPrice(p.getSize().getSign())*p.getQty());
        }
        order.setCustomer(member);
        order.setTotalPrice(sum);
        order.setOrderId(orderAccess.insertOrderWithPhone(order));
        for(OrderDetail o : orderDetails){
            if(orderAccess.insertOrderDetails(o,order)){
                System.out.println("Them san pham thanh cong: "+o.getProduct().getProductName());
            }else{
                System.out.println("Them san pham that bai");
            }
        }
        return order;
    }
    public Order insertOrder(ArrayList<OrderDetail> orderDetails){
        Order order = new Order();
       order.setDetails(orderDetails);
        int sum=0;
        for(OrderDetail p: orderDetails){
            sum+=(p.getProduct().getPrice(p.getSize().getSign())*p.getQty());
        }
        order.setTotalPrice(sum);
        order.setOrderId(orderAccess.insertOrderNoPhone(order));
        for(OrderDetail o : orderDetails){
            if(orderAccess.insertOrderDetails(o,order)){
                System.out.println("Them san pham thanh cong: "+o.getProduct().getProductName());
            }else{
                System.out.println("Them san pham that bai");
            }
        }
        return order;
    }
}
