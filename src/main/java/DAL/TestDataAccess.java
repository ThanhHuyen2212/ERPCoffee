package DAL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestDataAccess extends DataAccess {
    public static void main(String[] args) {
        TestDataAccess test = new TestDataAccess();
        try {
            test.createConnection();
            PreparedStatement prSt = test.getConn().prepareStatement("call select_category();");
//            prSt.execute();
            ResultSet rs = prSt.executeQuery();
            while(rs.next()){
                System.out.println(rs.getString(2));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
