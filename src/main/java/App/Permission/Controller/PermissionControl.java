package App.Permission.Controller;

import App.Permission.Model.PermissionModel;
import Entity.Function;
import Entity.Role;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PermissionControl implements Initializable {
    @FXML
    private BorderPane view;
    @FXML
    private TableView<Function> functionTable;
    @FXML
    private TableView<Role> roleTable;
    @FXML
    private TableColumn<Role,String> roleNoCol;
    @FXML
    private TableColumn<Role,String> roleNameCol;
    @FXML
    private TableColumn<Function, String> funcNoCol;
    @FXML
    private TableColumn<Function, String> funcNameCol;

    private FlowPane roleControlHolder;
    private FlowPane funcControlHolder;
    private PermissionModel model;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        model = new PermissionModel();
        roleNoCol.setCellValueFactory(item ->new SimpleStringProperty(
            String.valueOf(roleTable.getItems().indexOf(item.getValue()) + 1
        )));
        funcNoCol.setCellValueFactory(item ->new SimpleStringProperty(
                String.valueOf(functionTable.getItems().indexOf(item.getValue()) + 1)
                ));
        roleNameCol.setCellValueFactory(new PropertyValueFactory<>("roleName"));
        funcNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        roleTable.setItems(FXCollections.observableList(model.getRoles()));
        initGUI();
        handleEvent();
    }

    private void initGUI(){
        createRoleControl();
        funcControlHolder = new FlowPane();
    }

    public void createRoleControl(){
        roleControlHolder = new FlowPane();
        Label label = new Label("Role Control:");
        Button addBtn = new Button("Add");
        Button editBtn = new Button("Edit");
        Button delBtn = new Button("Delete");
        roleControlHolder.getChildren().addAll(label,addBtn,editBtn,delBtn);
        roleControlHolder.setPrefHeight(100);
        roleControlHolder.setVgap(10);
        roleControlHolder.setHgap(10);
        roleControlHolder.setAlignment(Pos.CENTER);
        addBtn.setFocusTraversable(false);
        addBtn.setOnMouseClicked(e->{
            if(roleTable.getSelectionModel().getSelectedItem()!=null){
                roleTable.getSelectionModel().getSelectedItem().addFunction(FunctionBuilder());
                functionTable.refresh();
            }
        });

        view.setBottom(roleControlHolder);
    }

    private void handleEvent(){
        roleTable.setOnMouseClicked(e->{
            if(roleTable.getSelectionModel().getSelectedItem() !=null) functionTable.setItems(
                    FXCollections.observableList(roleTable.getSelectionModel().getSelectedItem().getFunctions())
            );
        });
    }

    private Function FunctionBuilder(){
        ChoiceDialog<Function> dialog = new ChoiceDialog<>();
        dialog.getItems().addAll(model.getFunctions());
        dialog.setContentText("Choice a Function to Add");

        dialog.setTitle("Add Permission");
        dialog.setHeaderText("Functional");
        dialog.setGraphic(null);
        dialog.getDialogPane().setStyle(
                "-fx-background-color: linear-gradient(to right, #acb6e5, #86fde8);\n" +
                "-fx-opacity: 0.95;" +
                "-fx-color: #fff;");
        return dialog.showAndWait().get();
    }
}
