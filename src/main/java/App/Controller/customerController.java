package App.Controller;

import App.Model.OrderTable;
import Entity.Member;
import Logic.MemberManagement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class customerController implements Initializable {
    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnEdit;
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
    public static ArrayList<Member> memberList;

    private MemberManagement memberManagement = new MemberManagement();
    ObservableList<Member> MemberObservableList;

    public customerController() {
        memberList = memberManagement.getMembers();
    }

    /**
     * Fake dữ liệu
     */

    private void initTable(ArrayList<Member> memberList){
        MemberObservableList = FXCollections.observableArrayList(memberList);
        customerID.setCellValueFactory(new PropertyValueFactory<Member, Integer>(""));
        customerName.setCellValueFactory(new PropertyValueFactory<Member, String>("fullName"));
        customerPhone.setCellValueFactory(new PropertyValueFactory<Member, String>("phoneNumber"));
        customerPoint.setCellValueFactory(new PropertyValueFactory<Member, Integer>("point"));
        customerTable.setItems(MemberObservableList);

    }

    public void changeSceneAddEvent(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new File("src/main/java/App/View/CustomerAdd.fxml").toURI().toURL());
        Pane ProductAddViewParent = loader.load();
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initStyle(StageStyle.TRANSPARENT);
        dialog.setDialogPane((DialogPane) ProductAddViewParent);
        Optional<ButtonType> ClickedButton = dialog.showAndWait();
        CustomerAdd AddController = loader.getController();
        if (ClickedButton.get() == ButtonType.APPLY) {
            AddController.createMember();
            customerTable.setItems(FXCollections.observableArrayList(memberList));
            customerTable.refresh();
        } else if (ClickedButton.get() == ButtonType.CLOSE) {
            dialog.close();
        }
    }
    public void changeSceneEditEvent(ActionEvent e) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader();
        Member selected = customerTable.getSelectionModel().getSelectedItem();
        if (selected==null){
            loader.setLocation(this.getClass().getResource("../View/Alert.fxml"));
            Pane EditParentView = loader.load();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane((DialogPane) EditParentView);
            dialog.showAndWait();
        }else{
            loader.setLocation(new File("src/main/java/App/View/CustomerEdit.fxml").toURI().toURL());
            Pane ProductEditViewParent = loader.load();
            CustomerEdit customerEdit = loader.getController();
            //customerEdit.handleEvent(selected);
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane((DialogPane) ProductEditViewParent);
            dialog.initStyle(StageStyle.TRANSPARENT);
            Optional<ButtonType> clickedButton = dialog.showAndWait();
            if(clickedButton.get() == ButtonType.APPLY){
                //customerEdit.EditProduct(selected);
                customerTable.setItems(FXCollections.observableArrayList(memberList));
                customerTable.refresh();
            }else if(clickedButton.get()==ButtonType.CLOSE){
                dialog.close();
            }
        }

    }
    public Member getMember(){
        return customerTable.getSelectionModel().getSelectedItem();
    }
    public Member findByName(String name){
        for (Member member: memberList){
            if(member.getFullName().equalsIgnoreCase(name)){
                return member;
            }
        }
        return null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(memberList.size());
        initTable(memberList);
    }
}
