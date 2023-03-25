package App.Controller;

import Entity.Member;
import Logic.MemberManagement;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class CustomerAdd {
    @FXML
    private DialogPane dialogCategoryAdd;
    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtPoint;
    MemberManagement memberManagement = new MemberManagement();
    customerController customerController = new customerController();
    public Member member(){
        String  name = null;
        String phone = null;
        Integer point =0;
        if(txtName!=null){
            name = txtName.getText();
        }
        if (txtPhone!=null){
         phone=txtPhone.getText();
        }
        if(txtPoint!=null){
            point= Integer.parseInt(txtPoint.getText());
        }
        return new Member(name,phone,point);
    }
    public void createMember(){
        Member newMember = member();
        memberManagement.createMember(newMember);
    }

}
