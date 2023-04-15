package DAL;

import Entity.*;
import Logic.MemberManagement;
import Logic.OrderManagement;
import Logic.ProductManagement;
import Logic.SizeManagement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class OrderAccess extends DataAccess {


    public   ArrayList<Order> retrieve(){
        ArrayList<Order> orders = new ArrayList<>();
        MemberAccess memberAccess = new MemberAccess();
        ProductAccess productAccess = new ProductAccess();
        SizeAccess sizeAccess = new SizeAccess();
        productAccess.createConnection();
        sizeAccess.createConnection();
        try {
            PreparedStatement prSt = getConn().prepareStatement("call select_orders()");
            ResultSet rs = prSt.executeQuery();
            Order newOrder=null;
            while (rs!=null && rs.next()){
                newOrder=new Order(rs.getInt(1),rs.getInt(2),rs.getDate(3));
                newOrder.setCustomer(memberAccess.findByPhone(rs.getString(4)));
                orders.add(newOrder);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
        PreparedStatement preparedStatement = getConn().prepareStatement("call select_orderdetail_with_orderid(?)");
        for(Order o : orders){
            preparedStatement.setInt(1,o.getOrderId());
            ResultSet rs= preparedStatement.executeQuery();
            ArrayList<OrderDetail> orderDetails = new ArrayList<>();
            while (rs!=null && rs.next()){
                Product product= productAccess.findByName(rs.getString(2));
                Integer qty=rs.getInt(3);
                Size size=  sizeAccess.findByName(rs.getString(4));
                orderDetails.add(new OrderDetail(product,size,qty));
            }
            o.setDetails(orderDetails);
        }
        }catch (SQLException e){
            System.out.println("OrderAccess(ArrayList)");
            System.out.println(e.getMessage());
        }
        return orders;
    }

    public boolean insertOrderDetails(OrderDetail orderDetail, Order order){

        try {
            PreparedStatement prSt= getConn().prepareStatement("call insert_orderdetail(?,?,?,?)");
            prSt.setInt(1,order.getOrderId());
            prSt.setString(2,orderDetail.getProduct().getProductName());
            prSt.setInt(3,orderDetail.getQty());
            prSt.setString(4,orderDetail.getSize().getSign());
            prSt.executeQuery();
            return true;
        } catch (SQLException e) {
            System.out.println("OrderAccess(Details)");
            System.out.println(e.getMessage());

        }

        return false;
    }
    public Order insertOrderWithPhone(Order order){
        OrderManagement orderManagement = new OrderManagement();
        MemberManagement memberManagement = new MemberManagement();
        Order newOrder = new Order();
        PreparedStatement prSt = null;
        try {
            prSt = getConn().prepareStatement("call insert_orders(?, ?)");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            prSt.setInt(1,order.getTotalPrice());
            prSt.setString(2,order.getCustomer().getPhoneNumber());
            ResultSet rs = prSt.executeQuery();
            while(rs!=null && rs.next()){
                newOrder.setOrderId(rs.getInt(1));
                newOrder.setCustomer(memberManagement.findByPhone(rs.getString(4)));
                newOrder.setOrderDate(rs.getDate(3));
                newOrder.setTotalPrice(rs.getInt(2));
            };
        } catch (SQLException e) {
            System.out.println("OrderAccess");
            System.out.println(e.getMessage());
        }
        return  newOrder;
    }
    public Order insertOrderNoPhone(Order order){
        Order newOrder = new Order();
        OrderManagement orderManagement = new OrderManagement();
        PreparedStatement prSt = null;
        try {
            prSt = getConn().prepareStatement("call insert_orders_without_phone(?)");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            prSt.setInt(1,order.getTotalPrice());
            ResultSet rs = prSt.executeQuery();
            while(rs!=null && rs.next()){
                newOrder.setOrderId(rs.getInt(1));
                newOrder.setOrderDate(rs.getDate(3));
                newOrder.setTotalPrice(rs.getInt(2));
            };
        } catch (SQLException e) {
            System.out.println("OrderAccess(No phone)");
            System.out.println(e.getMessage());
        }
        return  newOrder;
    }
    public ArrayList<OrderDetail> orderDetailsWithID(Integer orderID){
        ProductAccess productAccess = new ProductAccess();
        SizeManagement sizeManagement = new SizeManagement();
        ArrayList<OrderDetail> orderDetails = new ArrayList<>();
        try {
            PreparedStatement prSt = getConn().prepareStatement("call select_orderdetail_with_orderid(?)");
            prSt.setInt(1,orderID);
            ResultSet rs = prSt.executeQuery();
            System.out.println(rs.getRow());
            while (rs.next()){

                orderDetails.add(new OrderDetail(
                        productAccess.findByName(rs.getString(2)),sizeManagement.findByName(rs.getString(4)),
                        rs.getInt(3)
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return orderDetails;
    }


}
