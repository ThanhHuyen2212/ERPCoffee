package App.Permission.Controller;

import App.ModuleManager.AppControl;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
    private HBox controlBox;
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
        createFunctionControl();
        controlBox = new HBox();
        controlBox.getChildren().add(roleControlHolder);
        controlBox.getChildren().add(funcControlHolder);
        controlBox.setAlignment(Pos.CENTER);
        controlBox.getStyleClass().add("hau-container-color");
        view.setBottom(controlBox);
    }

    public void createRoleControl(){
        roleControlHolder = new FlowPane();
        Label label = new Label("Role Control:");
        Button addBtn = new Button("Add");
        Button editBtn = new Button("Edit");
        label.getStyleClass().add("hau-menu-label");
        label.setPrefWidth(100);
        addBtn.getStyleClass().add("hau-control-button");
        editBtn.getStyleClass().add("hau-control-button");
        roleControlHolder.getChildren().addAll(label,addBtn,editBtn);
        roleControlHolder.setPrefHeight(100);
        roleControlHolder.setVgap(10);
        roleControlHolder.setHgap(10);
        roleControlHolder.setAlignment(Pos.CENTER);
        addBtn.setFocusTraversable(false);
        addBtn.setOnMouseClicked(e->{
            try{
                String text = RoleBuilder();
                if(roleTable.getItems().stream().anyMatch(item->item.getRoleName().equalsIgnoreCase(text))){
                    AppControl.showAlert("error","Role Name has already existed !!!");
                }
                else
                {
                    Role role = new Role();
                    role.setRoleName(text);
                    role.setFunctions(new ArrayList<>());
                    model.insertNewRole(role);
                    roleTable.refresh();
                }
            }catch (Exception exception){
                exception.printStackTrace();
            }
        });
    }

    public void createFunctionControl(){
        funcControlHolder = new FlowPane();
        Label label = new Label("Manage Role's Functions:");
        Button addBtn = new Button("Add");
        Button delBtn = new Button("Delete");
        label.getStyleClass().add("hau-menu-label");
        label.setMinWidth(200);
        addBtn.getStyleClass().add("hau-control-button");
        delBtn.getStyleClass().add("hau-control-button");
        funcControlHolder.getChildren().addAll(label,addBtn,delBtn);
        funcControlHolder.setPrefHeight(100);
        funcControlHolder.setVgap(10);
        funcControlHolder.setHgap(10);
        funcControlHolder.setAlignment(Pos.CENTER);
        addBtn.setFocusTraversable(false);
        addBtn.setOnMouseClicked(e->{
            if(roleTable.getSelectionModel().getSelectedItem()!=null){
                try{
                    Function fn = FunctionBuilder();
                    model.saveFunction(roleTable.getSelectionModel().getSelectedItem(),fn);
                    roleTable.getSelectionModel().getSelectedItem().addFunction(fn);
                    functionTable.setItems(functionTable.getItems());
                    functionTable.refresh();
                }catch (Exception exception){
                    System.out.println(exception.getMessage());
                }

            }
        });
        delBtn.setOnMouseClicked(e->{
            if(roleTable.getSelectionModel().getSelectedItem()!=null && functionTable.getSelectionModel().getSelectedItem()!=null){
                model.removeFunction(
                        roleTable.getSelectionModel().getSelectedItem(),
                        functionTable.getSelectionModel().getSelectedItem()
                );
                roleTable.getSelectionModel().getSelectedItem().getFunctions().remove(
                        functionTable.getSelectionModel().getSelectedItem()
                );
                functionTable.refresh();
            }
        });

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
        dialog.getItems().addAll(
                model.getFunctions().stream().filter(
                    e->!roleTable.getSelectionModel().getSelectedItem().getFunctions().contains(e)).toList());
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

    private String RoleBuilder(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setContentText("Enter New Role's Name");

        dialog.setTitle("Add Role");
        dialog.setHeaderText("New Role Input");
        dialog.setGraphic(null);
        dialog.getDialogPane().setStyle(
                "-fx-background-color: linear-gradient(to right, #acb6e5, #86fde8);\n" +
                        "-fx-opacity: 0.95;" +
                        "-fx-color: #fff;");
        return dialog.showAndWait().get();
    }
}
