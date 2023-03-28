package Logic;

import DAL.MemberAccess;
import DAL.ProductAccess;
import Entity.Member;
import Entity.Product;

import java.util.ArrayList;

public class ProductManagement {
    private ArrayList<Product> products;
    ProductAccess productAccess = new ProductAccess();
    public ProductManagement(){
        init();
    }
    private void init(){
        products = productAccess.retrieve();
    }
    public ArrayList<Product> getProducts(){
        return products;
    }

}
