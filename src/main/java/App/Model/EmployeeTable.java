package App.Model;

import DAL.DataAccess;
import DAL.EmployeeAccess;
import DAL.PositionAccess;
import Entity.WorkPosition;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeTable extends DataAccess {
    private int id;
    private String name;
    private String phone;
    private String address;
    private String position;

    public EmployeeTable(int id, String name,String phone,String address,String position) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.position = position;
    }
    public EmployeeTable(){

    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
    public ArrayList<EmployeeTable> getData() {
        EmployeeAccess employeeAccess = new EmployeeAccess();
        return employeeAccess.getData();
    }
    public ArrayList<WorkPosition> getDataPosition(){
        PositionAccess positionAccess = new PositionAccess();
        return positionAccess.getData();
    }
    public void empAdd(){
        EmployeeAccess employeeAccess = new EmployeeAccess();
        EmployeeTable employeeTable = new EmployeeTable(id,name,phone,address,position);
        employeeAccess.add(employeeTable);
    }
}
