package Logic.Statitics;

import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.util.Pair;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

public interface IStatitics {
    public void getData(Time time);
    public ArrayList<Order> getRevenueStatitics();
    public ArrayList<Product> getProductStatitics();
    public ArrayList<Category> getCategoryStatitics();
//    public XYChart.Series getIngredientStatitics();
    public class Order{
        private Date date;
        private Number orderCount;
        private Number averageRevenue;
        private Number totalRevenue;

        public Order() {
        }

        public Order(Date date, Number orderCount, Number averageRevenue, Number totalRevenue) {
            this.date = date;
            this.orderCount = orderCount;
            this.averageRevenue = averageRevenue;
            this.totalRevenue = totalRevenue;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Number getOrderCount() {
            return orderCount;
        }

        public void setOrderCount(Number orderCount) {
            this.orderCount = orderCount;
        }

        public Number getAverageRevenue() {
            return averageRevenue;
        }

        public void setAverageRevenue(Number averageRevenue) {
            this.averageRevenue = averageRevenue;
        }

        public Number getTotalRevenue() {
            return totalRevenue;
        }

        public void setTotalRevenue(Number totalRevenue) {
            this.totalRevenue = totalRevenue;
        }
    }

    public class Time{
        private Date start;
        private Date end;

        public Time() {
        }

        public Time(int year, int month){
            Calendar cal = Calendar.getInstance();
            this.start = new Date(year-1900,month-1,1);
            cal.setTime(start);
            this.end = new Date(year-1900,month-1,cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        }

        public Time(Date start, Date end) {
            this.start = start;
            this.end = end;
        }

        public Date getStart() {
            return start;
        }

        public void setStart(Date start) {
            this.start = start;
        }

        public Date getEnd() {
            return end;
        }

        public void setEnd(Date end) {
            this.end = end;
        }
    }
    public class Product{

        private String productName;
        private Number productCount;
        private ArrayList<Pair<String,Number>> productCountBySize;
        private Number totalRevenue;

        public Product() {
        }

        public Product(String productName, Number productCount, ArrayList<Pair<String, Number>> productCountBySize, Number totalRevenue) {
            this.productName = productName;
            this.productCount = productCount;
            this.productCountBySize = productCountBySize;
            this.totalRevenue = totalRevenue;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public Number getProductCount() {
            return productCount;
        }

        public void setProductCount(Number productCount) {
            this.productCount = productCount;
        }

        public ArrayList<Pair<String, Number>> getProductCountBySize() {
            return productCountBySize;
        }

        public void setProductCountBySize(ArrayList<Pair<String, Number>> productCountBySize) {
            this.productCountBySize = productCountBySize;
        }

        public Number getTotalRevenue() {
            return totalRevenue;
        }

        public void setTotalRevenue(Number totalRevenue) {
            this.totalRevenue = totalRevenue;
        }
    }
    public class Category{
        private String name;
        private Number productCount;
        private Number totalRevenue;

        public Category() {
        }

        public Category(String name, Number productCount, Number totalRevenue) {
            this.name = name;
            this.productCount = productCount;
            this.totalRevenue = totalRevenue;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Number getProductCount() {
            return productCount;
        }

        public void setProductCount(Number productCount) {
            this.productCount = productCount;
        }

        public Number getTotalRevenue() {
            return totalRevenue;
        }

        public void setTotalRevenue(Number totalRevenue) {
            this.totalRevenue = totalRevenue;
        }
    }
}
