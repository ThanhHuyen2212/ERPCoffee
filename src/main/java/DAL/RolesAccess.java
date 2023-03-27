package DAL;

import Entity.Role;
import Entity.WorkPosition;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RolesAccess extends DataAccess {
    private ArrayList<Role> roleslist = new ArrayList<>();
    public ArrayList<Role> getData(){
        Role role = new Role();
        try {
            createConnection();
            PreparedStatement prSt = getConn().prepareStatement("call select_roles();");
            ResultSet rs = prSt.executeQuery();
            while (rs!=null && rs.next()){
                role = new Role(rs.getInt(1),rs.getString(2),null);
                roleslist.add(role);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return roleslist;
    }

}
