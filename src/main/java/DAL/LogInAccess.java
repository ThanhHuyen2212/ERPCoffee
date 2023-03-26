package DAL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LogInAccess extends DataAccess{

    private int roleId;
    public LogInAccess() {
    }

    public boolean checkLogIn(String username, String password){
        createConnection();
        try {
            PreparedStatement prst = getConn().prepareStatement("call check_user_pass(?, ?)");
            prst.setString(1,username);
            prst.setString(2,password);
            ResultSet rs = prst.executeQuery();
            if (rs.next()){
                roleId = rs.getInt(1);
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public ArrayList<String> getPermission(int roleId){
        ArrayList<String> tmp = new ArrayList<>();
        try {
            PreparedStatement prst = getConn().prepareStatement("call select_functionalname_with_rolesid(?)");
            prst.setInt(1,roleId);
            ResultSet rs = prst.executeQuery();
            while (rs.next()){
                System.out.println(rs.getString(1));
                tmp.add(rs.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(roleId);
        return tmp;
    }

    public int getRoleId() {
        return roleId;
    }
}
