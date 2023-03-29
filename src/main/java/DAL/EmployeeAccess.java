package DAL;

import App.Model.EmployeeTable;
import Entity.Employee;
import Entity.WorkPosition;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeAccess extends DataAccess {
    private ArrayList<EmployeeTable> employeelist = new ArrayList<>();
    private ArrayList<Employee> employees;

    public ResultSet init(String query) throws SQLException {
        createConnection();
        PreparedStatement prSt = getConn().prepareStatement(query);
        ResultSet rs = prSt.executeQuery();
        return rs;
    }

    public ArrayList<EmployeeTable> getData() {
        try {
            ResultSet rs = init("call select_employee();");
            while (rs.next()) {
                EmployeeTable empTb = new EmployeeTable(Integer.parseInt(rs.getString(1)),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5));
//                System.out.println(empTb.getId());
                employeelist.add(empTb);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employeelist;
    }

    public void add(EmployeeTable employeeTable) {
        try {
            createConnection();
            PreparedStatement prSt = getConn().prepareStatement("call insert_employee(?,?,?,?)");
            prSt.setString(1, employeeTable.getName());
            prSt.setString(2, employeeTable.getPhone());
            prSt.setString(3, employeeTable.getAddress());
            prSt.setString(4, employeeTable.getPosition());
            prSt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void edit(EmployeeTable employeeTable) {
        try {
            createConnection();
            PreparedStatement prSt = getConn().prepareStatement("call update_employee(?,?,?,?,?)");
            prSt.setInt(1, employeeTable.getId());
            prSt.setString(2, employeeTable.getName());
            prSt.setString(3, employeeTable.getPhone());
            prSt.setString(4, employeeTable.getAddress());
            prSt.setString(5, employeeTable.getPosition());
            prSt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getEmployee() {
        employees = new ArrayList<>();
        try {
            ResultSet rs = init("call select_employee();");
            while (rs.next()) {
                employees.add(new Employee(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4)
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Employee findById(int id) {
        for (Employee e : employees) {
            if (e.getEmployeeId() == id)
                return e;
        }
        return null;
    }
}
