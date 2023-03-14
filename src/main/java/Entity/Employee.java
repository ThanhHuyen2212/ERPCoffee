package Entity;

public class Employee {
    private int employeeId;
    private String name;
    private String phone;
    private String address;
    private WorkPosition position;

    public Employee() {
    }

    public Employee(int employeeId, String name, String phone, String address, WorkPosition position) {
        this.employeeId = employeeId;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.position = position;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public WorkPosition getPosition() {
        return position;
    }

    public void setPosition(WorkPosition position) {
        this.position = position;
    }
}
