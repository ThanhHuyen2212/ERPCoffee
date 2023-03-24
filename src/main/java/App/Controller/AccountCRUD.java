package App.Controller;

import App.Model.AccountTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AccountCRUD implements Initializable {
    @FXML
    private ComboBox<?> CBRole;

    @FXML
    private TableColumn<AccountTable, Integer> ColEmpID;

    @FXML
    private TableColumn<AccountTable, String> ColPass;

    @FXML
    private TableColumn<AccountTable, String> ColRole;

    @FXML
    private TableColumn<AccountTable, String> ColUsername;

    @FXML
    private TableView<AccountTable> TBAccount;

    @FXML
    private TextField TFEmpID;

    @FXML
    private TextField TFEmpName;

    @FXML
    private TextField TFPass;

    @FXML
    private TextField TFUsername;

    @FXML
    private Button btnAddAccount;

    @FXML
    private Button btnCancelAccount;

    @FXML
    private Button btnDeleteAccount;

    @FXML
    private Button btnEditAccount;
    public static ArrayList<AccountTable> AccountTableViewList;
    private ObservableList<AccountTable> accountTable;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AccountTable accTb = new AccountTable();
        AccountTableViewList =  accTb.getDataAccount();
        accountTable = FXCollections.observableArrayList(AccountTableViewList);
        ColEmpID.setCellValueFactory(new PropertyValueFactory<AccountTable,Integer>("empId"));
        ColUsername.setCellValueFactory(new PropertyValueFactory<AccountTable,String>("username"));
        ColPass.setCellValueFactory(new PropertyValueFactory<AccountTable,String>("password"));
        ColRole.setCellValueFactory(new PropertyValueFactory<AccountTable,String>("positionName"));
        TBAccount.setItems(accountTable);
    }
}
