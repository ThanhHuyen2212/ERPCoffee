package Logic;

import DAL.LogInAccess;
import Entity.Employee;

import java.util.ArrayList;

public class LLogIn {
    private LogInAccess dataGetter;
    private ArrayList<String> permissions;
    private Employee employee;

    public LLogIn() {
        dataGetter = new LogInAccess();
    }

    public LLogIn(LogInAccess dataGetter) {
        this.dataGetter = dataGetter;
    }

    public boolean getData(String username,String password){
        if(dataGetter.checkLogIn(username,password)){
            int roleId = dataGetter.getRoleId();
            this.permissions = dataGetter.getPermission(roleId);
            this.employee = dataGetter.getEmployee(username);
            return true;
        }

        return false;
    }
    public ArrayList<String> getPermission(){
        return this.permissions;
    }

    public Employee getEmployee() {
        return this.employee;
    }
}
