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
    }

    public void getData(IStatitics.Time time){
        dataGetter.getData(time);
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

}
