package App.Statitics.Model;

import Entity.Order;

import java.util.ArrayList;

public class RevenueModel {
    private ArrayList<Order> orders;

    public RevenueModel() {
        orders = new ArrayList<>();
    }

    public RevenueModel(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }


}
