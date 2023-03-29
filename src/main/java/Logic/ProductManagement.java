package Logic;

import DAL.MemberAccess;
import DAL.POAccess;
import DAL.ProductAccess;
import Entity.Member;
import Entity.Product;

import java.util.ArrayList;

public class ProductManagement {
    private ArrayList<Product> products;

    ProductAccess productAccess;

    public ProductManagement(){
        productAccess = new ProductAccess();
        products=productAccess.retrieve();
    }
    public ArrayList<Product> getProducts(){
        return products;
    }

}
