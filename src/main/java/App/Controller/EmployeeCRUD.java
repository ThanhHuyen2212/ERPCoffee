package App.Controller;

import App.Model.EmployeeTable;
import DAL.DataAccess;
import Entity.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.sound.midi.Soundbank;
import javax.swing.text.TabableView;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.ResourceBundle;

public class EmployeeCRUD extends DataAccess implements Initializable {
    @FXML
    private TableView<EmployeeTable> empTable;
    @FXML
    private TableColumn<EmployeeTable, Integer> IdCol;
    @FXML
    private TableColumn<EmployeeTable, String> NameCol;
    @FXML
    private TableColumn<EmployeeTable, String> PositionCol;
    @FXML
    private TextField IdTF;
    @FXML
    private TextField NameTF;
    @FXML
    private TextField PhoneTF;
    @FXML
    private TextField AddressTF;
    @FXML
    private ComboBox PositionTF;

    public static ArrayList<EmployeeTable> EmployeeTableViewList;
    private ObservableList<EmployeeTable> employeeTable;
    public void getData(){
        ArrayList<EmployeeTable> list  = new ArrayList<>();
        try {
            createConnection();
            PreparedStatement prSt = getConn().prepareStatement("call select_employee();");
            ResultSet rs = prSt.executeQuery();
            while(rs.next()){
                EmployeeTable empTb = new EmployeeTable(Integer.parseInt(rs.getString(1))
                        ,rs.getString(2),
                        rs.getString(5));
                System.out.println(empTb.getId());
                System.out.println(empTb.getName());
                System.out.println(empTb.getPosition());
                list.add(empTb);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//        employeeTable = FXCollections.observableArrayList(list);
//        IdCol.setCellValueFactory(new PropertyValueFactory<>("EmployeeId"));
//        NameCol.setCellValueFactory(new PropertyValueFactory<>("EmployeeName"));
//        PositionCol.setCellValueFactory(new PropertyValueFactory<>("PositionName"));
//        empTable.setItems(employeeTable);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getData();
        employeeTable = FXCollections.observableArrayList(EmployeeTableViewList);
//        IdCol.setCellValueFactory(new PropertyValueFactory<EmployeeTable,Integer>("id"));
//        NameCol.setCellValueFactory(new PropertyValueFactory<EmployeeTable,String>("name"));
//        PositionCol.setCellValueFactory(new PropertyValueFactory<EmployeeTable,String>("position"));
//        empTable.setItems(employeeTable);
    }

}
