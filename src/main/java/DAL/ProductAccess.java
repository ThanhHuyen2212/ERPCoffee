package DAL;

import Entity.Product;
import Entity.ProductDetails;

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
                ArrayList<ProductDetails> productDetails = new ArrayList<>();
                ResultSet resultSet= preparedStatement.executeQuery();
                while (resultSet!=null && resultSet.next()){
                    productDetails.add(new ProductDetails(resultSet.getString(2), resultSet.getInt(3), resultSet.getInt(4)));
                }
                p.setProductDetails(productDetails);

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
        try {
            PreparedStatement prst =  getConn().prepareStatement("call update_product(?,?,?,?);");
            prst.setString(1, String.valueOf(product.getProductId()));
            prst.setString(2,product.getProductName());
            prst.setString(3,product.getImagePath());
            prst.setString(4,product.getCategory());
            prst.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void Insert(Product product){
        try {
            PreparedStatement prst =  getConn().prepareStatement("call insert_product(?,?,?);");
            prst.setString(1,product.getProductName());
            prst.setString(2,product.getImagePath());
            prst.setString(3,product.getCategory());
            prst.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void InsertProductSize(String name,String size,int vle,int  price){
        try {
            PreparedStatement prst =  getConn().prepareStatement("call insert_productsize(?,?,?,?);");
            prst.setString(1,name);
            prst.setString(2,size);
            prst.setInt(3,price);
            prst.setInt(4,vle);
            prst.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void UpdateProductSize(String name,String size,int vle,int  price){
        try {
            PreparedStatement prst =  getConn().prepareStatement("call update_productsize_with_name(?,?,?,?)");
            prst.setString(1,name);
            prst.setString(2,size);
            prst.setInt(3,price);
            prst.setInt(4,vle);
            prst.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
                ArrayList<ProductDetails> productDetails = new ArrayList<>();
                ResultSet resultSet= preparedStatement.executeQuery();
                while (resultSet!=null && resultSet.next()){
                    productDetails.add(new ProductDetails(resultSet.getString(2), resultSet.getInt(3), resultSet.getInt(4)));
            }
                product.setProductDetails(productDetails);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return product;
    }

    public static void main(String[] args) {
        ProductAccess productAccess = new ProductAccess();
        System.out.println(productAccess.findByName("Cà phê đen").getProductName());
    }
}
