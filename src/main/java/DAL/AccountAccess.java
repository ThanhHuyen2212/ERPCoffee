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
    public void Insert(String username,String pass,int empId,String rolename){
        try {
            createConnection();
            PreparedStatement prSt = getConn().prepareStatement("call insert_account(?,?,?,?)");
            prSt.setString(1,username);
            prSt.setString(2,pass);
            prSt.setInt(3,empId);
            prSt.setString(4,rolename);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
