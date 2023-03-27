package DAL;

import Entity.Function;
import Entity.Role;
import Entity.WorkPosition;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RolesAccess extends DataAccess {
    private ArrayList<Role> roleslist;
    private ArrayList<Function> functions;
    public ArrayList<Role> getData(){
        if(roleslist == null){
            roleslist = new ArrayList<>();
            try {
                PreparedStatement prSt = getConn().prepareStatement("call select_roles();");
                PreparedStatement prSt2 = getConn().prepareStatement("call select_functionalname_with_rolesid(?)");
                ResultSet rs = prSt.executeQuery();
                while (rs!=null && rs.next()){
                    prSt2.setInt(1,rs.getInt(1));
                    ResultSet rs2 = prSt2.executeQuery();
                    Role role = new Role(rs.getInt(1),rs.getString(2),new ArrayList<>());
                    while (rs2.next()){
                        for(Function func : getAllFuction()){
                            if(func.getName().equals(rs2.getString(1))) {
                                role.addFunction(func);
                                break;
                            }
                        }
                    }
                    roleslist.add(role);
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return roleslist;
    }

    public ArrayList<Function> getAllFuction(){
        if(functions == null){
            functions = new ArrayList<>();
            try{
                PreparedStatement pr = getConn().prepareStatement("call select_functional()");
                ResultSet rs = pr.executeQuery();
                while (rs.next()){
                    functions.add(new Function(rs.getInt(1),rs.getString(2)));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return functions;
    }

}
