package DAL;

import Logic.Statitics.IStatitics;

import java.sql.Date;
import java.util.ArrayList;

public interface IAccessStatitics {
    public ArrayList<IStatitics.Order> getRevenueStatitics(Date start, Date end);
    public ArrayList<IStatitics.Product> getProductStatitics(Date start, Date end);
    public ArrayList<IStatitics.Category> getCategoryStatitics(Date start, Date end);
}
