package Logic.Statitics;

import DAL.IAccessStatitics;
import DAL.StatiticsAccess;
import javafx.util.Pair;

import java.sql.Date;
import java.util.ArrayList;

public class LStatitics implements IStatitics{


    private IAccessStatitics dataAccess;
    private ArrayList<Order> revenueData;
    private ArrayList<Product> productsData;
    private ArrayList<Category> categoryData;


    public LStatitics() {
        dataAccess = new StatiticsAccess();
    }

    public LStatitics(IAccessStatitics dataAccess) {
        this.dataAccess = dataAccess;
    }

    @Override
    public void getData(Time time) {
        this.revenueData = dataAccess.getRevenueStatitics(time.getStart(),time.getEnd());
        this.productsData = dataAccess.getProductStatitics(time.getStart(),time.getEnd());
        this.categoryData = dataAccess.getCategoryStatitics(time.getStart(),time.getEnd());
    }

    @Override
    public ArrayList<Order> getRevenueStatitics() {
        if (this.revenueData == null) revenueData = new ArrayList<>();
        return this.revenueData;

    }

    @Override
    public ArrayList<Product> getProductStatitics() {
        if (this.productsData == null) productsData = new ArrayList<>();
        return this.productsData;
    }

    @Override
    public ArrayList<Category> getCategoryStatitics() {
        if (this.categoryData == null) categoryData = new ArrayList<>();
        return this.categoryData;
    }
}
