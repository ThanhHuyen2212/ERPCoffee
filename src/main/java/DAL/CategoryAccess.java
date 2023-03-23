package DAL;

import Entity.Category;
import Logic.CategoryManagement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryAccess  extends DataAccess {
    public static ArrayList<Category> retrieve(){
        CategoryAccess categoryAccess =new CategoryAccess();
        //CategoryManagement categoryManagement = new CategoryManagement();
        ArrayList<Category> categories = new ArrayList<>();
        try {
            categoryAccess.createConnection();
            PreparedStatement prSt = categoryAccess.getConn().prepareStatement("call select_category();");
            ResultSet rs = prSt.executeQuery();
            while (rs!=null && rs.next()){
                categories.add(new Category(rs.getInt(1),rs.getString(2)));
            }
        } catch (SQLException e) {
            System.out.println("CategoryAccess");
            System.out.println(e.getMessage());
        }
        return categories;
    }

}
