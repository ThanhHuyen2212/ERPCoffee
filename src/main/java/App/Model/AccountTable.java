package App.Model;

import DAL.AccountAccess;
import DAL.DataAccess;
import DAL.RolesAccess;
import Entity.Role;

import java.util.ArrayList;

public class AccountTable {
    private  String username;
    private  String password;
    private  int empId;
    private  String empName;
    private  String positionName;

    public AccountTable(String username, String password, int empId, String empName, String positionName) {
        this.username = username;
        this.password = password;
        this.empId = empId;
        this.empName = empName;
        this.positionName = positionName;
    }
    public AccountTable(){

    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }
    public ArrayList<AccountTable> getDataAccount(){
        AccountAccess accountAccess = new AccountAccess();
        return accountAccess.getData();
    }
    public ArrayList<Role> getDataRole(){
        RolesAccess rolesAccess = new RolesAccess();
        return rolesAccess.getData();
    }
    public void AddAccount(){
        AccountAccess accountAccess = new AccountAccess();
        accountAccess.Insert(getUsername(),getPassword(),getEmpId(),getPositionName());
    }
}
