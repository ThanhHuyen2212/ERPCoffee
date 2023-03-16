package App.Statitics.Model;

import Entity.Order;
import javafx.scene.chart.XYChart;

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

    public void addOrder(Order od){
        orders.get(od.getOrderDate()).add(od);
    }

    public XYChart.Series getData(){
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("2003");
        series1.getData().add(new XYChart.Data("1994-07-09", 25601.34));
        series1.getData().add(new XYChart.Data("1994-07-10", 20148.82));
        series1.getData().add(new XYChart.Data("1994-07-11", 10000));
        series1.getData().add(new XYChart.Data("1994-07-12", 35407.15));
        series1.getData().add(new XYChart.Data("1994-07-13", 12000));
        series1.getData().add(new XYChart.Data("1994-07-14", 25601.34));
        series1.getData().add(new XYChart.Data("1994-07-15", 20148.82));
        series1.getData().add(new XYChart.Data("1994-07-16", 10000));
        series1.getData().add(new XYChart.Data("1994-07-17", 35407.15));
        series1.getData().add(new XYChart.Data("1994-07-18", 12000));
        series1.getData().add(new XYChart.Data("1994-07-19", 25601.34));
        series1.getData().add(new XYChart.Data("1994-07-20", 20148.82));
        series1.getData().add(new XYChart.Data("1994-07-21", 10000));
        series1.getData().add(new XYChart.Data("1994-07-22", 35407.15));
        series1.getData().add(new XYChart.Data("1994-07-23", 12000));
        series1.getData().add(new XYChart.Data("1994-07-24", 25601.34));
        series1.getData().add(new XYChart.Data("1994-07-25", 20148.82));
        series1.getData().add(new XYChart.Data("1994-07-26", 10000));
        series1.getData().add(new XYChart.Data("1994-07-27", 35407.15));
        series1.getData().add(new XYChart.Data("1994-07-28", 12000));
        return  series1;
    }
}
