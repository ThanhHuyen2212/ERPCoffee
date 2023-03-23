package DAL;

import Entity.Product;
import Logic.ProductManagement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class ProductAccess extends DataAccess {
    public static  boolean checkProduct(String name, ArrayList<Product> products){
        for (Product p: products){
            if(p.getProductName().equalsIgnoreCase(name)){
                return  true;
            }
        }
        return  false;
    }
    public static ArrayList<Product> retrieve(){
        ProductAccess productAccess = new ProductAccess();
        ProductManagement productManagement = new ProductManagement();
        ArrayList<Product> products = new ArrayList<>();
        try {
            productAccess.createConnection();
            PreparedStatement prSt = productAccess.getConn().prepareStatement("call select_product_delete_is_null()");
            ResultSet resultSet = prSt.executeQuery();
            while (resultSet!=null &&resultSet.next()){
                products.add(new Product(resultSet.getInt(1),
                                          resultSet.getString(2),
                        resultSet.getDate(3),
                        resultSet.getDate(4),
                        resultSet.getString(5),
                        resultSet.getString(6)));
            }
        }catch (SQLException e){
            throw new RuntimeException();
        }
        try {
            PreparedStatement preparedStatement = productAccess.getConn().prepareStatement("call select_productsize()");
            ResultSet resultSet= preparedStatement.executeQuery();
            for(Product p : products){
                HashMap<String, Integer> sizePrice = new HashMap<>();
                while (resultSet!=null && resultSet.next()){
                if(p.getProductName().equalsIgnoreCase(resultSet.getString(1))){
                        sizePrice.put(resultSet.getString(2),resultSet.getInt(3));
                    }
                p.setPriceList(sizePrice);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }
    public void create(Product product){

    }
    public void Update(Product product){

    }
    public void findById(int id){

    }

    public static void main(String[] args) {
        retrieve();
    }
}
