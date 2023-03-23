package App.Controller;

import App.Model.OrderTable;
import Entity.Member;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class customerController implements Initializable {
    @FXML
    private Button btnSearch;

    @FXML
    private TableColumn<Member, Integer> customerID;

    @FXML
    private TableColumn<Member, String> customerName;

    @FXML
    private TableColumn<Member, String> customerPhone;

    @FXML
    private TableColumn<Member, Integer> customerPoint;

    @FXML
    private TableView<Member> customerTable;

    @FXML
    private TextField txtSearchCustomer;
    ArrayList<Member> memberList = new ArrayList<>();
    ObservableList<Member> MemberObservableList;

    /**
     * Fake dữ liệu
     */

    private ArrayList<Member> getDataMemberList(){
        ArrayList<Member> mbList = new ArrayList<>();
        Member member;
        member = new Member();
        member.setFullName("Nguyễn Hữu Đại");
        member.setPhoneNumber("0900000");
        member.setPoint(10000);
        mbList.add(member);
        member = new Member();
        member.setFullName("Lê Ngô Hậu");
        member.setPhoneNumber("0900001");
        member.setPoint(10000);
        mbList.add(member);
        member = new Member();
        member.setFullName("Nguyễn Thị Thanh Huyền");
        member.setPhoneNumber("0900002");
        member.setPoint(10000);
        mbList.add(member);
        member = new Member();
        member.setFullName("Nguyễn Hoàng Gia Đại");
        member.setPhoneNumber("0900003");
        member.setPoint(10000);
        mbList.add(member);
        return  mbList;
    }
    private void initTable(ArrayList<Member> memberList){
        MemberObservableList = FXCollections.observableArrayList(memberList);
        customerID.setCellValueFactory(new PropertyValueFactory<Member, Integer>(""));
        customerName.setCellValueFactory(new PropertyValueFactory<Member, String>("fullName"));
        customerPhone.setCellValueFactory(new PropertyValueFactory<Member, String>("phoneNumber"));
        customerPoint.setCellValueFactory(new PropertyValueFactory<Member, Integer>("point"));
        customerTable.setItems(MemberObservableList);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        memberList=getDataMemberList();
        System.out.println(memberList.size());
        initTable(memberList);
    }
}
