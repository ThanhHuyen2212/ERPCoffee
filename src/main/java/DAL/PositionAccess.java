package DAL;

import Entity.Category;
import Entity.WorkPosition;

import javax.swing.text.Position;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PositionAccess extends DataAccess{
    private ArrayList<WorkPosition> PositionList = new ArrayList<>();
    public ArrayList<WorkPosition> getData(){
        WorkPosition wpst = new WorkPosition();
        try {
            createConnection();
            PreparedStatement prSt = getConn().prepareStatement("call select_workposition();");
            ResultSet rs = prSt.executeQuery();
            while (rs!=null && rs.next()){
                wpst = new WorkPosition(rs.getInt(1),rs.getString(2),rs.getInt(3));
                PositionList.add(wpst);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return PositionList;
    }
}
