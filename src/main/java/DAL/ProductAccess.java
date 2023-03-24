package DAL;

import Entity.Product;
import Logic.ProductManagement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductAccess extends DataAccess {
    public ArrayList<Product> retrieve(){
        ProductAccess productAccess = new ProductAccess();
        ProductManagement productManagement = new ProductManagement();
        try {
            productAccess.createConnection();
            PreparedStatement preparedStatement = productAccess.getConn().prepareStatement("call select_product_delete_is_null()");
            ResultSet resultSet = preparedStatement.getResultSet();
            ArrayList<Product> products = new ArrayList<>();
            while (resultSet!=null &&resultSet.next()){

            }
        }catch (SQLException e){
            throw new RuntimeException();
        }
        return null;
    }
    public void create(Product product){

    }
    public void Update(Product product){

    }
    public void findById(int id){

    }
}
