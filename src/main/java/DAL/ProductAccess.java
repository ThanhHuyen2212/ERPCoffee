package DAL;

import Entity.Product;
import Logic.ProductManagement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class ProductAccess extends DataAccess {

    public static ArrayList<Product> retrieve(){
        ProductAccess productAccess = new ProductAccess();
        //ProductManagement productManagement = new ProductManagement();
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
            PreparedStatement preparedStatement = productAccess.getConn().prepareStatement("call select_productsize_with_id(?)");
            for(Product  p: products){
                preparedStatement.setInt(1,p.getProductId());
                HashMap<String, Integer> sizePrice= new HashMap<>();
                ResultSet resultSet= preparedStatement.executeQuery();
                while (resultSet!=null && resultSet.next()){
                    sizePrice.put(resultSet.getString(2), resultSet.getInt(3));
                }
                p.setPriceList(sizePrice);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }
    public int create(Product product){
        return 1;
    }
    public void Update(Product product){

    }
    public void findById(int id){

    }

}
