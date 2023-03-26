package App.Controller;

import App.Model.EmployeeTable;
import DAL.DataAccess;
import App.Model.OrderTable;
import DAL.CategoryAccess;
import DAL.EmployeeAccess;
import Entity.*;
import Logic.CategoryManagement;
import Logic.SizeManagement;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.Year;
import java.util.*;

public class EmployeeCRUD implements Initializable {
    @FXML
    private TableView<EmployeeTable> empTable;
    @FXML
    private TableColumn<EmployeeTable, Integer> IdCol;
    @FXML
    private TableColumn<EmployeeTable, String> NameCol;
    @FXML
    private TableColumn<EmployeeTable, String> PositionCol;
    @FXML
    private TextField TFEmpID;
    @FXML
    private TextField TFEmpName;
    @FXML
    private TextField TFEmpPhone;
    @FXML
    private TextField TFEmpAddress;
    @FXML
    private ComboBox CBEmpPosition;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnCancel;

    public static ArrayList<EmployeeTable> EmployeeTableViewList;
    private ObservableList<EmployeeTable> employeeTable;

    public Button getBtnAdd() {
        return btnAdd;
    }

    public void setBtnAdd(Button btnAdd) {
        this.btnAdd = btnAdd;
    }

    public Button getBtnEdit() {
        return btnEdit;
    }

    public void setBtnEdit(Button btnEdit) {
        this.btnEdit = btnEdit;
    }

    public Button getBtnDelete() {
        return btnDelete;
    }

    public void setBtnDelete(Button btnDelete) {
        this.btnDelete = btnDelete;
    }

    public Button getBtnCancel() {
        return btnCancel;
    }

    public void setBtnCancel(Button btnCancel) {
        this.btnCancel = btnCancel;
    }
    public void actionButton(){
        btnAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String cb = String.valueOf(CBEmpPosition.getSelectionModel().getSelectedItem());
//                System.out.println(cb);
                try{
                    EmployeeTable employee = new EmployeeTable(Integer.parseInt(TFEmpID.getText()),TFEmpName.getText(),TFEmpPhone.getText(),TFEmpAddress.getText(),cb);
                    System.out.println(employee.getName());
                    //Add
                    EmployeeAccess employeeAccess = new EmployeeAccess();
                    employeeAccess.add(employee);
                    EmployeeTable tb = new EmployeeTable();
                    EmployeeTableViewList = tb.getData();
                    employeeTable = FXCollections.observableArrayList(EmployeeTableViewList);
                    empTable.setItems(employeeTable);
                    showAlert("Success","Thành công!");
                }catch (Exception e){
                    System.out.println("fail");
                    showAlert("error","Thất bại!");
                }
            }
        });
        btnEdit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String cb = String.valueOf(CBEmpPosition.getSelectionModel().getSelectedItem());
                try{
                    EmployeeTable employee = new EmployeeTable(Integer.parseInt(TFEmpID.getText()),TFEmpName.getText(),TFEmpPhone.getText(),TFEmpAddress.getText(),cb);
                    System.out.println(employee.getName());
                    //Edit
                    EmployeeAccess employeeAccess = new EmployeeAccess();
                    employeeAccess.edit(employee);
                    EmployeeTable tb = new EmployeeTable();
                    EmployeeTableViewList = tb.getData();
                    employeeTable = FXCollections.observableArrayList(EmployeeTableViewList);
                    empTable.setItems(employeeTable);
                    showAlert("Success","Success!");
                }catch (Exception e){
                    System.out.println("fail");
                    showAlert("error","Fail!");
                }
            }
        });
        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                TFEmpID.setText(null);
                TFEmpName.setText(null);
                TFEmpPhone.setText(null);
                TFEmpAddress.setText(null);
                CBEmpPosition.getSelectionModel().select(0);
            }
        });
        btnDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });
    }
    public void fillDataDetail(TableView emp){
        emp.setRowFactory( tv -> {
            TableRow<EmployeeTable> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    EmployeeTable rowData = row.getItem();
                    TFEmpID.setText(String.valueOf(rowData.getId()));
                    TFEmpID.setDisable(true);
                    TFEmpName.setText(rowData.getName());
                    TFEmpPhone.setText(rowData.getPhone());
                    TFEmpAddress.setText(rowData.getAddress());
                    CBEmpPosition.getSelectionModel().select(rowData.getPosition());
                }
            });
            return row ;
        });

    }
    public void setDataCBPosition(){
        EmployeeTable tb = new EmployeeTable();
        ArrayList<WorkPosition> positionlist = new ArrayList<>();
        positionlist = tb.getDataPosition();
        int size = positionlist.size();
        String[] array = new String[size];
        for (int i = 0 ; i < size ; i++ ){
            array[i] = positionlist.get(i).getName();
        }
        CBEmpPosition.getItems().addAll(array);
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            EmployeeTable tb = new EmployeeTable();
            EmployeeTableViewList = tb.getData();
            employeeTable = FXCollections.observableArrayList(EmployeeTableViewList);
            IdCol.setCellValueFactory(new PropertyValueFactory<EmployeeTable,Integer>("id"));
            NameCol.setCellValueFactory(new PropertyValueFactory<EmployeeTable,String>("name"));
            PositionCol.setCellValueFactory(new PropertyValueFactory<EmployeeTable,String>("position"));
            empTable.setItems(employeeTable);
            TFEmpID.setDisable(true);
            fillDataDetail(empTable);
            setDataCBPosition();
            actionButton();
    }

}
