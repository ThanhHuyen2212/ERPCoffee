package DAL;

import Entity.Category;
import Entity.Size;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SizeAccess extends DataAccess {

    public  ArrayList<Size> retrieve(){
        ArrayList<Size> sizes = new ArrayList<>();
        try {
            PreparedStatement prSt = getConn().prepareStatement("call select_sizes()");
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
    public Size findByName(String size){
        Size newSize = new Size();
        try {
            PreparedStatement prSt = getConn().prepareStatement("call select_sizes_with_sign(?)");
            prSt.setString(1,size);
            ResultSet rs= prSt.executeQuery();
            while (rs!=null && rs.next()){
                newSize.setSign(rs.getString(1));
                newSize.setDescription(rs.getString(2));
            }
        }catch (SQLException e){
            throw new RuntimeException();
        }
        return newSize;
    }

    public void saveSize(Size s){
        try {
            PreparedStatement prS = getConn().prepareStatement( "call insert_sizes(?, ?)");
            prS.setString(1,s.getSign());
            prS.setString(2,s.getDescription());
            prS.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteSize(Size s){

    }
    public void editSize(String oldsize,Size s){
        try {
            PreparedStatement prS = getConn().prepareStatement( "call update_sizes(?,?,?)");
            prS.setString(1,oldsize);
            prS.setString(2,s.getSign());
            prS.setString(3,s.getDescription());
            prS.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
