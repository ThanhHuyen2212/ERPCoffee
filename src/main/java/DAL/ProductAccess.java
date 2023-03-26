package DAL;

import Entity.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class ProductAccess extends DataAccess {
    public  ArrayList<Product> retrieve(){
        ArrayList<Product> products = new ArrayList<>();

        try {

            PreparedStatement prSt = getConn().prepareStatement("call select_product_delete_is_null()");
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
            PreparedStatement preparedStatement = getConn().prepareStatement("call select_productsize_with_id(?)");
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
//        Get recipe for each product
        RecipeAccess recipeAccess = new RecipeAccess();
        recipeAccess.retrieve(products);

        return products;
    }
    public int create(Product product){
        return 1;
    }
    public void Update(Product product){

    }
    public void findById(int id){

    }
    public Product findByName(String productName){
        Product product = new Product();
        try {
            PreparedStatement preparedStatement =getConn().prepareStatement("call select_product_with_name(?)");
            preparedStatement.setString(1,productName);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs!=null && rs.next()){
                product.setProductId(rs.getInt(1));
                product.setProductName(rs.getString(2));
                product.setCreateAt(rs.getDate(3));
                product.setDeleteAt(rs.getDate(4));
                product.setImagePath(rs.getString(5));
                product.setCategory(rs.getString(6));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            PreparedStatement preparedStatement =getConn().prepareStatement("call select_productsize_with_id(?)");
                preparedStatement.setInt(1,product.getProductId());
                HashMap<String, Integer> sizePrice= new HashMap<>();
                ResultSet resultSet= preparedStatement.executeQuery();
                while (resultSet!=null && resultSet.next()){
                    sizePrice.put(resultSet.getString(2), resultSet.getInt(3));
            }
            product.setPriceList(sizePrice);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return product;
    }

    public static void main(String[] args) {
        ProductAccess productAccess = new ProductAccess();
        Product product= productAccess.findByName("Cà phê sữa");
        System.out.println(product.getProductId());
    }
}
