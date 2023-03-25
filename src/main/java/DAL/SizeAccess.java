package DAL;

import Entity.Category;
import Entity.Size;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SizeAccess extends DataAccess {
    public static ArrayList<Size> retrieve(){
        SizeAccess categoryAccess =new SizeAccess();
        //CategoryManagement categoryManagement = new CategoryManagement();
        ArrayList<Size> sizes = new ArrayList<>();
        try {
            categoryAccess.createConnection();
            PreparedStatement prSt = categoryAccess.getConn().prepareStatement("call select_sizes()");
            ResultSet rs = prSt.executeQuery();
            while (rs!=null && rs.next()){
                sizes.add(new Size(rs.getString(1),rs.getString(2)));
            }
        } catch (SQLException e) {
            System.out.println("SizeAccess");
            System.out.println(e.getMessage());
        }
        return sizes;
    }


}
