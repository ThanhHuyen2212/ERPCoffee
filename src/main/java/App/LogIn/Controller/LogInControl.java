package App.LogIn.Controller;

import App.LogIn.Model.LoginModel;
import App.ModuleManager.AppControl;
import DAL.DataAccess;
import Entity.Employee;
import Main.MainApp;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.util.Duration;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static Main.MainApp.APP;
import static Main.MainApp.currentUser;


public class LogInControl implements Initializable {
    @FXML
    private GridPane logInHolder;
    @FXML
    private VBox logInBox;
    @FXML
    private ImageView imgBackground;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnClear;
    @FXML
    private Button btnEnter;

    private LoginModel model;

    public LogInControl() {
    }

    public void clearTxt(){
        txtUsername.setText("");
        txtPassword.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnClear.setOnMouseClicked(e-> clearTxt());
        btnEnter.setOnMouseClicked(e-> checkLogIn());
        model = new LoginModel();
        imgBackground.setFitWidth(Screen.getPrimary().getBounds().getWidth()-100);
    }

    private void checkLogIn() {
        if(model.checkLogIn(txtUsername.getText(),txtPassword.getText())){
            AppControl.currentUser = model.getEmployee();
            APP.setPermissons(model.getPermission());
            logInHolder.getChildren().remove(logInBox);
            System.out.println(AppControl.currentUser.getName());
        }else{
            AppControl.showAlert("error","Log In Fail !!!");
        }
    }
}
