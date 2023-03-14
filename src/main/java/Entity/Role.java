package Entity;

import java.util.ArrayList;

public class Role {
    private int roleId;
    private String roleName;
    private ArrayList<Function> functions;

    public Role() {
    }

    public Role(int roleId, String roleName, ArrayList<Function> functions) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.functions = functions;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public ArrayList<Function> getFunctions() {
        return functions;
    }

    public void setFunctions(ArrayList<Function> functions) {
        this.functions = functions;
    }

    public void addFunction(Function function){
        if(!this.functions.contains(function)) {
            this.functions.add(function);
        }
    }
}
