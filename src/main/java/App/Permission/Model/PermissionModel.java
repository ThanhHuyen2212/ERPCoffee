package App.Permission.Model;

import Entity.Function;
import Entity.Role;
import Logic.Permission.LPermission;

import java.util.ArrayList;

public class PermissionModel {
    private ArrayList<Role> roles;
    private ArrayList<Function> functions;
    private LPermission dataGetter;

    public PermissionModel() {
        dataGetter = new LPermission();
    }

    public PermissionModel(ArrayList<Role> roles) {
        this.roles = roles;
    }

    public ArrayList<Role> getRoles() {
        if (roles==null) roles = dataGetter.getRoles();
        return roles;
    }

    public void setRoles(ArrayList<Role> roles) {
        this.roles = roles;
    }

    public ArrayList<Function> getFunctions() {
        if (functions==null) functions = dataGetter.getFunctions();
        return functions;
    }

    public void insertNewRole(Role r){
        dataGetter.saveRole(r);
        roles.add(r);
    }

    public void saveFunction(Role selectedItem, Function fn) {
        dataGetter.saveFunction(selectedItem,fn);
    }

    public void removeFunction(Role selectedItem, Function fn) {
        dataGetter.removeFunction(selectedItem,fn);
    }
}
