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
    public Order insertOrder(ArrayList<OrderDetail> orderDetails, Member member, Integer price){
        Order order = new Order();
        order.setDetails(orderDetails);
        order.setTotalPrice(price);
        order.setCustomer(member);
        Order newOrder=orderAccess.insertOrderWithPhone(order);
        order.setOrderId(newOrder.getOrderId());
        order.setOrderDate(newOrder.getOrderDate());
        for(OrderDetail o : orderDetails){
            if(orderAccess.insertOrderDetails(o,order)){
                System.out.println(order.getCustomer().getFullName());
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
        Order newOrder=orderAccess.insertOrderNoPhone(order);
        order.setOrderId(newOrder.getOrderId());
        order.setOrderDate(newOrder.getOrderDate());
        for(OrderDetail o : orderDetails){
            if(orderAccess.insertOrderDetails(o,order)){
                System.out.println("Them san pham thanh cong: "+o.getProduct().getProductName());
            }else{
                System.out.println("Them san pham that bai");
            }
        }
        return order;
    }
    public ArrayList<OrderDetail> selectOrderDetailsWithOrderID(Integer orderId){
        return orderAccess.selectOrderDetailsWithOrderID(orderId);
    }
}
