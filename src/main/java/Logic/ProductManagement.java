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
    public void addProduct(Product product){productAccess.Insert(product);}
    public void updateProduct(Product product){
        ProductAccess productAccess1 = new ProductAccess();
        productAccess1.Update(product);
    }
    public void insertProdSize(String name,String size,int vle,int price){
        productAccess.InsertProductSize(name,size,vle,price);
    }
    public Product findByName(String name){
        return productAccess.findByName(name);
    }
}
