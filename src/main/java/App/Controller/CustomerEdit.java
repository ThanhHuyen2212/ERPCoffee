package App.Controller;

import Entity.Member;
import javafx.fxml.FXML;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;

public class CustomerEdit {
    @FXML
    private DialogPane dialogCategoryAdd;
    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtPoint;
    public void setData(Member member){
        txtName.setText(member.getFullName());
        txtPhone.setText(member.getPhoneNumber());
        txtPoint.setText(String.valueOf(member.getPoint()));
    }
    public Member newMember(){
        String name= txtName.getText();
        String phone =txtPhone.getText();
        int point = Integer.parseInt(txtPoint.getText());
        return new Member(phone,name,point);
    }

}
