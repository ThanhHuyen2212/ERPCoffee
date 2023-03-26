package Logic;

import DAL.LogInAccess;

import java.util.ArrayList;

public class LLogIn {
    private LogInAccess dataGetter;
    private ArrayList<String> permissions;
    private String username;

    public LLogIn() {
        dataGetter = new LogInAccess();
    }

    public LLogIn(LogInAccess dataGetter) {
        this.dataGetter = dataGetter;
    }

    public boolean getData(String username,String password){
        if(dataGetter.checkLogIn(username,password)){
            int roleId = dataGetter.getRoleId();
            this.username = username;
            this.permissions = dataGetter.getPermission(roleId);
            return true;
        }

        return false;
    }
    public ArrayList<String> getPermission(){
        return this.permissions;
    }
}
