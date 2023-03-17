package App.Statitics.Model;

import Entity.Order;
import Logic.Statitics.IStatitics;
import Logic.Statitics.LStatitics;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

public class RevenueModel {

    private IStatitics dataGetter;

    private ArrayList<IStatitics.Order> revenueData;
    private ArrayList<IStatitics.Product> revenueByProduct;
    private ArrayList<IStatitics.Category> revenueByCategory;

    public RevenueModel() {
    }

    public IStatitics getDataGetter() {
        return dataGetter;
    }

    public void setDataGetter(IStatitics dataGetter) {
        this.dataGetter = dataGetter;
        this.revenueData = dataGetter.getRevenueStatitics();
        this.revenueByProduct = dataGetter.getProductStatitics();
        this.revenueByCategory = dataGetter.getCategoryStatitics();
    }

    public ArrayList<IStatitics.Order> getRevenueData() {
        if(revenueData == null) revenueData = new ArrayList<>();
        return revenueData;
    }

    public XYChart.Series<String,Number> getRevenueChartData(){
        return new XYChart.Series<>("Revenue", FXCollections.observableList(
                revenueData.stream().map(
                        data -> new XYChart.Data<>(data.getDate().toString(),data.getTotalRevenue())
                ).toList()
        ));
    }

    public ArrayList<IStatitics.Product> getRevenueByProduct() {
        if (revenueByProduct == null) revenueByProduct = new ArrayList<>();
        return revenueByProduct;
    }

    public XYChart.Series<String,Number> getRevenueChartByProduct(){
        return new XYChart.Series<>("Revenue by Product", FXCollections.observableList(
                revenueByProduct.stream().map(
                        data -> new XYChart.Data<>(data.getProductName(),data.getTotalRevenue())
                ).toList()
        ));
    }

    public ArrayList<IStatitics.Category> getRevenueByCategory() {
        if(revenueByCategory == null) revenueByCategory = new ArrayList<>();
        return revenueByCategory;
    }

    public ObservableList<PieChart.Data> getRevenueChartByCategory(){
        return FXCollections.observableList(
                revenueByCategory.stream().map(
                        data -> new PieChart.Data(
                                data.getName(),
                                Double.parseDouble(data.getTotalRevenue().toString()))
                ).toList()
        );
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
