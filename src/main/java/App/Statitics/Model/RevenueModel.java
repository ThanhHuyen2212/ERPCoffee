package App.Statitics.Model;

import Entity.Ingredient;
import Entity.Order;
import Entity.PurchaseDetail;
import Entity.PurchaseOrder;
import Logic.Depot.IngredientManagement;
import Logic.Depot.PurchaseOrderManagement;
import Logic.Statitics.IStatitics;
import Logic.Statitics.LStatitics;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RevenueModel {

    private IStatitics dataGetter;

    private ArrayList<IStatitics.Order> revenueData;
    private ArrayList<IStatitics.Product> revenueByProduct;
    private ArrayList<IStatitics.Category> revenueByCategory;

    private ArrayList<PurchaseOrder> purchases;
    private ArrayList<Ingredient> ingredients;

    private IngredientManagement ingDataGetter;
    private PurchaseOrderManagement purChaseDataGetter;

    public RevenueModel() {
        ingDataGetter = new IngredientManagement();
        purChaseDataGetter = new PurchaseOrderManagement();
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

        purchases = new ArrayList<>(purChaseDataGetter.getPurchaseOrders()
                .stream().filter(
                        e-> e.getPurchaseOrderDate().compareTo(time.getStart()) >= 0
                                && e.getPurchaseOrderDate().compareTo(time.getEnd()) <= 0)
                .toList());

        ingredients = new ArrayList<>(ingDataGetter.getIngredients());

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

    public XYChart.Series<String,Number> getIngData(){
        return new XYChart.Series<String,Number>("In Stock", FXCollections.observableList(
                    this.ingredients.stream().map(
                        data -> new XYChart.Data<>(data.getIngredientName(),(Number)data.getIngredientStorage())
                    ).toList()));
    }

    public XYChart.Series<String,Number> getPurchaseData(){
        ArrayList<String> ingName = new ArrayList<>(
                ingredients.stream().map(Ingredient::getIngredientName).toList());
        HashMap<String,Integer> ingOrder = new HashMap<>();
        for(String name :ingName){
            ingOrder.put(name, (int) (Math.random()*50));
//            ingOrder.put(name, 0);
        }
        for(PurchaseOrder order : purchases){
            for(PurchaseDetail detail : order.getDetails()){
                int value = ingOrder.get(detail.getIngredient().getIngredientName()) + detail.getReceiveQty();
                ingOrder.put(detail.getIngredient().getIngredientName(),value);
                System.out.println(value);
            }
        }

        return new XYChart.Series<>("Order", FXCollections.observableList(
                ingName.stream().map(e->new XYChart.Data<>(e,(Number)ingOrder.get(e))).toList()));
    }

}
