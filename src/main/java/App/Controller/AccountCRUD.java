package App.Controller;

import App.Model.AccountTable;
import App.Model.EmployeeTable;
import DAL.EmployeeAccess;
import Entity.Role;
import Entity.WorkPosition;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AccountCRUD implements Initializable {
    @FXML
    private ComboBox CBRole;

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
    private PasswordField TFPass;

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
    @FXML
    private TextField TFSearch;
    public static ArrayList<AccountTable> AccountTableViewList;
    private ObservableList<AccountTable> accountTable;
    public void fillDataDetail(TableView tableView){
        tableView.setRowFactory( tv -> {
            TableRow<AccountTable> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    AccountTable rowData = row.getItem();
                    TFEmpID.setText(String.valueOf(rowData.getEmpId()));
                    TFEmpName.setText(rowData.getEmpName());
                    TFUsername.setText(rowData.getUsername());
                    TFPass.setText(rowData.getPassword());
                    CBRole.getSelectionModel().select(rowData.getPositionName());
//                    CBRole.getSelectionModel().select(rowData.getPosition());
                }
            });
            return row ;
        });

    }
    public void setDataCBRole(){
        AccountTable accTb = new AccountTable();
        ArrayList<Role> rolelist = new ArrayList<>();
        rolelist = accTb.getDataRole();
        int size = rolelist.size();
        String[] array = new String[size];
        for (int i = 0 ; i < size ; i++ ){
            array[i] = rolelist.get(i).getRoleName();
        }
        CBRole.getItems().addAll(array);
    }
    public void actionButton(){
        btnAddAccount.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String cb = String.valueOf(CBRole.getSelectionModel().getSelectedItem());
//                System.out.println(cb);
                try{
                    AccountTable account = new AccountTable(TFUsername.getText(),TFPass.getText(),Integer.parseInt(TFEmpID.getText()),TFEmpName.getText(),cb);
                    //Add
//                    account.AddAccount();
                    System.out.println(account.getEmpName());
                    showAlert("Success","Success!");
                }catch (Exception e){
                    System.out.println("fail");
                    showAlert("error","Fail!");
                }
            }
        });
        btnEditAccount.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });
        btnCancelAccount.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                TFEmpID.setText(null);
                TFEmpName.setText(null);
                TFUsername.setText(null);
                TFPass.setText(null);
                CBRole.getSelectionModel().select(0);
            }
        });
        btnDeleteAccount.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });
    }
    public static void showAlert(String type,String message){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    new File("src/main/java/App/View/Alert.fxml").toURI().toURL()
            );
            Pane alert = fxmlLoader.load();
            Dialog<ButtonType> btnType = new Dialog<>();
            btnType.setDialogPane((DialogPane) alert);
            AlertController alert1 = fxmlLoader.getController();
            try {
                alert1.RenderAlert(type,message);
            } catch (MalformedURLException exc) {
                throw new RuntimeException(exc);
            }
            btnType.showAndWait();
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }
    public void searchAccount(){
        ObservableList data =  TBAccount.getItems();
        TFSearch.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (oldValue != null && (newValue.length() < oldValue.length())) {
                TBAccount.setItems(data);
            }
            String value = newValue.toLowerCase();
            ObservableList<AccountTable> subentries = FXCollections.observableArrayList();

            long count = TBAccount.getColumns().stream().count();
            for (int i = 0; i < TBAccount.getItems().size(); i++) {
                for (int j = 0; j < count; j++) {
                    String entry = "" + TBAccount.getColumns().get(j).getCellData(i);
                    if (entry.toLowerCase().contains(value)) {
                        subentries.add(TBAccount.getItems().get(i));
                        break;
                    }
                }
            }
            TBAccount.setItems(subentries);
        });
    }
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
        fillDataDetail(TBAccount);
        setDataCBRole();
        actionButton();
        searchAccount();
    }
}
