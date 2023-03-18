package Logic.Statitics;

import javafx.util.Pair;

import java.sql.Date;
import java.util.ArrayList;

public class LStatitics implements IStatitics{


    @Override
    public ArrayList<Order> getRevenueStatitics() {
        return new ArrayList<>(){{
            add(new Order(new Date(2023-1900,3,10),10,100000,1000000));
            add(new Order(new Date(2023-1900,3,11),20,100000,2000000));
            add(new Order(new Date(2023-1900,3,12),15,100000,1500000));
            add(new Order(new Date(2023-1900,3,13),10,100000,1000000));
            add(new Order(new Date(2023-1900,3,14),10,100000,1000000));
        }};
    }

    @Override
    public ArrayList<Product> getProductStatitics() {
        return new ArrayList<>(){{
            ArrayList<Pair<String,Number>> tmp;
            tmp = new ArrayList<>();
            tmp.add(new Pair<>("S",10));
            tmp.add(new Pair<>("M",10));
            tmp.add(new Pair<>("L",10));
            add(new Product("product1",30,tmp,100000));
            tmp = new ArrayList<>();
            tmp.add(new Pair<>("S",10));
            tmp.add(new Pair<>("M",16));
            tmp.add(new Pair<>("L",14));
            add(new Product("product2",30,tmp,200000));
            tmp = new ArrayList<>();
            tmp.add(new Pair<>("S",20));
            tmp.add(new Pair<>("M",30));
            tmp.add(new Pair<>("L",40));
            add(new Product("product3",90,tmp,600000));
        }};
    }

    @Override
    public ArrayList<Category> getCategoryStatitics() {
        return new ArrayList<>(){
            {
                add(new Category("test",10,100000));
                add(new Category("test2",14,220000));
                add(new Category("test3",15,320000));
                add(new Category("test4",12,250000));
                add(new Category("test5",17,200000));

            }
        };
    }
}
