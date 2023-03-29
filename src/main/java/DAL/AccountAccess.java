package DAL;

import App.Model.AccountTable;
import App.Model.EmployeeTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AccountAccess extends DataAccess {
    private ArrayList<AccountTable> accountlist = new ArrayList<>();
    public ArrayList<AccountTable> getData() {
        try {
            createConnection();
            PreparedStatement prSt = getConn().prepareStatement("call select_account()");
            ResultSet rs = prSt.executeQuery();
            while (rs.next()) {
                AccountTable accTb = new AccountTable(rs.getString(1),
                        rs.getString(2),
                        Integer.parseInt(rs.getString(3)) ,
                        rs.getString(4),
                        rs.getString(6));
                accountlist.add(accTb);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return accountlist;
    }
    public void Insert(AccountTable accountTable){
        try {
            createConnection();
            PreparedStatement prSt = getConn().prepareStatement("call insert_account(?,?,?,?)");
            prSt.setString(1,accountTable.getUsername());
            prSt.setString(2,accountTable.getPassword());
            prSt.setInt(3,accountTable.getEmpId());
            prSt.setString(4,accountTable.getPositionName());
            prSt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void Update(AccountTable accountTable){
        try {
            createConnection();
            PreparedStatement prSt = getConn().prepareStatement("call update_password_rolesid_account(?,?,?)");
            prSt.setString(1,accountTable.getUsername());
            prSt.setString(2,accountTable.getPassword());
            prSt.setString(3,accountTable.getPositionName());
            prSt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
