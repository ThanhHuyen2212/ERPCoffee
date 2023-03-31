package App.Model;

import DAL.ProductAccess;
import Entity.Product;
import Logic.ProductManagement;

import java.util.ArrayList;

public class ProductModel {
    private ArrayList<Product> products;
    private ProductManagement dataGetter;

    public ProductModel(){

    }
    public ProductModel(ArrayList<Product> products){
        this.products = products;
    }

    public ArrayList<Product> getProducts() {
        dataGetter = new ProductManagement();
        return dataGetter.getProducts();
    }
    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public ProductManagement getDataGetter() {
        return dataGetter;
    }

    public void setDataGetter(ProductManagement dataGetter) {
        this.dataGetter = dataGetter;
    }
    public void addNewProduct(Product product){
        ProductAccess productAccess = new ProductAccess();
        productAccess.Insert(product);
    }
    public void updateOldProduct(Product product){
        dataGetter.updateProduct(product);
    }
}
