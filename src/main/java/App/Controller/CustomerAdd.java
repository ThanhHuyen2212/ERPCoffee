package App.Controller;
import Entity.Member;
import Logic.MemberManagement;
import javafx.fxml.FXML;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;

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
        return new Member(phone,name,point);
    }
    public void createMember(){
        Member newMember = member();
        customerController.memberList.add(newMember);
        memberManagement.createMember(newMember);
    }

}
