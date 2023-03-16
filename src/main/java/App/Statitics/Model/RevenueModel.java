package App.Statitics.Model;

import Entity.Order;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

public class RevenueModel {
    private HashMap<Date,ArrayList<Order>> orders;

    public RevenueModel() {
        orders = new HashMap<>();
    }

    public RevenueModel(HashMap<Date, ArrayList<Order>> orders) {
        this.orders = orders;
    }

    public HashMap<Date, ArrayList<Order>> getOrders() {
        return orders;
    }

    public void setOrders(HashMap<Date, ArrayList<Order>> orders) {
        this.orders = orders;
    }

    public Number getRevenue(Date date){
        double sum = 0;
        for(Order od : orders.get(date)){
            sum += od.getTotalPrice();
        }
        return sum;
    }
}
