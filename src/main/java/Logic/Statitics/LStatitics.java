package Logic.Statitics;

import java.util.ArrayList;

public class LStatitics implements IStatitics{


    @Override
    public ArrayList<Order> getRevenueStatitics() {
        return new ArrayList<>();
    }

    @Override
    public ArrayList<Product> getProductStatitics() {
        return new ArrayList<>();
    }

    @Override
    public ArrayList<Category> getCategoryStatitics() {
        return new ArrayList<>(){
            {
                add(new Category("test",1,2));
                add(new Category("test2",1,2));
                add(new Category("test3",1,2));
                add(new Category("test4",1,2));
                add(new Category("test5",1,2));

            }
        };
    }
}
