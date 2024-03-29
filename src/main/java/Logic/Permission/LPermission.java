package Logic.Permission;

import DAL.DataAccess;
import DAL.RolesAccess;
import Entity.Function;
import Entity.Role;

import java.util.ArrayList;

public class LPermission {
    private ArrayList<Role> roles;
    private ArrayList<Function> functions;
    private RolesAccess roleAccess;

    public LPermission() {
        roleAccess = new RolesAccess();
    }

    public ArrayList<Role> getRoles() {
        if (roles==null) roles = roleAccess.getData();
        return this.roles;
    }

    public ArrayList<Function> getFunctions(){
        if (functions==null) functions = roleAccess.getAllFuction();
        return this.functions;
    }

    public void saveRole(Role r) {
        roleAccess.saveRole(r);
    }

    public void saveFunction(Role selectedItem, Function fn) {
        roleAccess.saveFunction(selectedItem,fn);
    }

    public void removeFunction(Role selectedItem, Function fn){
        roleAccess.removeFunction(selectedItem,fn);
    }
}
