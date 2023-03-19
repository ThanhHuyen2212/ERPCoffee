package DAL;

import Logic.Statitics.IStatitics;
import javafx.util.Pair;

import java.sql.Date;
import java.util.ArrayList;

public class StatiticsAccess implements IAccessStatitics{
    @Override
    public ArrayList<IStatitics.Order> getRevenueStatitics(Date start, Date end) {
        return new ArrayList<>(){{
            add(new IStatitics.Order(new Date(2023-1900,3,10),10,100000,1000000));
            add(new IStatitics.Order(new Date(2023-1900,3,11),20,100000,2000000));
            add(new IStatitics.Order(new Date(2023-1900,3,12),15,100000,1500000));
            add(new IStatitics.Order(new Date(2023-1900,3,13),10,100000,1000000));
            add(new IStatitics.Order(new Date(2023-1900,3,14),10,100000,1000000));
        }};
    }

    @Override
    public ArrayList<IStatitics.Product> getProductStatitics(Date start, Date end) {

        return new ArrayList<>(){{
            ArrayList<Pair<String,Number>> tmp;
            tmp = new ArrayList<>();
            tmp.add(new Pair<>("S",10));
            tmp.add(new Pair<>("M",10));
            tmp.add(new Pair<>("L",10));
            add(new IStatitics.Product("product1",30,tmp,100000));
            tmp = new ArrayList<>();
            tmp.add(new Pair<>("S",10));
            tmp.add(new Pair<>("M",16));
            tmp.add(new Pair<>("L",14));
            add(new IStatitics.Product("product2",30,tmp,200000));
            tmp = new ArrayList<>();
            tmp.add(new Pair<>("S",20));
            tmp.add(new Pair<>("M",30));
            tmp.add(new Pair<>("L",40));
            add(new IStatitics.Product("product3",90,tmp,600000));
        }};
    }

    @Override
    public ArrayList<IStatitics.Category> getCategoryStatitics(Date start, Date end) {

        return new ArrayList<>(){
            {
                add(new IStatitics.Category("test",10,100000));
                add(new IStatitics.Category("test2",14,220000));
                add(new IStatitics.Category("test3",15,320000));
                add(new IStatitics.Category("test4",12,250000));
                add(new IStatitics.Category("test5",17,200000));

            }
        };
    }
}
