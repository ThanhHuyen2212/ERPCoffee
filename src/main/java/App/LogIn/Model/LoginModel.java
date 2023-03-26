package App.LogIn.Model;

import Logic.LLogIn;

import java.util.ArrayList;

public class LoginModel {

    private LLogIn loginLogic;
    public LoginModel() {
        loginLogic = new LLogIn();
    }

    public boolean checkLogIn(String username, String password){
        return loginLogic.getData(username, password);
    }

    public ArrayList<String> getPermission(){
        return loginLogic.getPermission();
    }
}
