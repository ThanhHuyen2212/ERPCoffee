package DAL;

import Entity.Member;
import Entity.Order;
import Entity.OrderDetail;
import Entity.Product;
import Logic.MemberManagement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class OrderAccess extends DataAccess {

    MemberManagement memberManagement = new MemberManagement();
    public static  ArrayList<Order> retrieve(){
        return null;
    }

    public boolean insertOrderDetails(OrderDetail orderDetail, Order order){
        OrderAccess orderAccess = new OrderAccess();
        orderAccess.createConnection();
        try {
            PreparedStatement prSt= orderAccess.getConn().prepareStatement("call insert_orderdetail(?,?,?,?)");
            prSt.setInt(1,order.getOrderId());
            prSt.setString(2,orderDetail.getProduct().getProductName());
            prSt.setInt(3,orderDetail.getQty());
            prSt.setString(4,orderDetail.getSize().getSign());
            return true;
        } catch (SQLException e) {
            System.out.println("OrderAccess(Details)");
            System.out.println(e.getMessage());

        }
        return false;
    }
    public int insertOrderWithPhone(Order order){
        OrderAccess orderAccess = new OrderAccess();
        orderAccess.createConnection();
        PreparedStatement prSt = null;
        try {
            prSt = orderAccess.getConn().prepareStatement("call insert_orders(?, ?)");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            prSt.setInt(1,order.getTotalPrice());
            prSt.setString(2,order.getCustomer().getPhoneNumber());
            ResultSet rs = prSt.executeQuery();
            while(rs!=null && rs.next()){
                return rs.getInt(1);
            };
        } catch (SQLException e) {
            System.out.println("OrderAccess");
            System.out.println(e.getMessage());
        }
        return  0;
    }
    public int insertOrderNoPhone(Order order){
        OrderAccess orderAccess = new OrderAccess();
        orderAccess.createConnection();
        PreparedStatement prSt = null;
        try {
            prSt = orderAccess.getConn().prepareStatement("call insert_orders_without_phone(?)");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            prSt.setInt(1,order.getTotalPrice());
            ResultSet rs = prSt.executeQuery();
            while(rs!=null && rs.next()){
                return rs.getInt(1);
            };
        } catch (SQLException e) {
            System.out.println("OrderAccess(No phone)");
            System.out.println(e.getMessage());
        }
        return  0;
    }

}
