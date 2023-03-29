package App.Depot.Controller;

import App.Depot.Model.POModel;
import App.Depot.View.MessageDialog;
import App.ModuleManager.AppControl;
import Entity.Employee;
import Entity.PurchaseDetail;
import Logic.Depot.IngredientManagement;
import Main.MainApp;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class POInputController implements Initializable {
    @FXML
    private TableView<PurchaseDetail> ingredientTable;
    @FXML
    private TableColumn<PurchaseDetail, String> ingredientCol;
    @FXML
    private TableColumn<PurchaseDetail, String> priceCol;
    @FXML
    private TableColumn<PurchaseDetail, String> qtyCol;
    @FXML
    private TableColumn<PurchaseDetail, String> idCol;
    @FXML
    private TableColumn<PurchaseDetail, String> typeCol;
    @FXML
    private TableColumn<PurchaseDetail, String> subtotalCol;
    @FXML
    private TableColumn<PurchaseDetail, String> unitCol;
    @FXML
    private ComboBox<String> ingredientCb;
    @FXML
    private Label dateLbl;
    @FXML
    private Label idLbl;
    @FXML
    private TextField qtyTxf;
    @FXML
    private Label totalLbl;
    @FXML
    private TextField vendorTxf;
    @FXML
    private Button addBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button confirmBtn;
    @FXML
    private Button cancelBtn;

    private POModel model;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new POModel();

        idCol.setCellValueFactory(data -> new SimpleStringProperty(
                String.valueOf(data.getValue().getIngredient().getIngredientId())
        ));
        ingredientCol.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getIngredient().getIngredientName()
        ));
        typeCol.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getIngredient().getIngredientType()
        ));
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("orderQty"));
        priceCol.setCellValueFactory(data -> new SimpleStringProperty(
                String.valueOf(data.getValue().getIngredient().getPrice())
        ));
        subtotalCol.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(
                model.calSubtotal(data.getValue())
        )));
        unitCol.setCellValueFactory(data -> new SimpleStringProperty(
                IngredientManagementController.setupUnit(data.getValue().getIngredient())
        ));

        dateLbl.setText(IngredientManagementController.sdf.format(new Date(new java.util.Date().getTime())));
        ingredientCb.setItems(FXCollections.observableArrayList(new IngredientManagement().getNameList()));
    }

    public void init(POModel modelOfSib) {
        this.model = modelOfSib;

        idLbl.setText(String.valueOf(model.getCurrent().getPurchaseOrderId()));

        handleActionOnRow();
        handleActionBtn();
        handleActionForm();
    }

    public void handleActionOnRow() {
        ingredientTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                ingredientCb.getSelectionModel().select(newSelection.getIngredient().getIngredientName());
                qtyTxf.setText(String.valueOf(newSelection.getOrderQty()));
            }
        });
    }

    public void handleActionBtn() {
        EventHandler<ActionEvent> buttonAddHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    model.handleAddDetail(
                            ingredientCb.getValue(),
                            Integer.parseInt(qtyTxf.getText())
                    );
                } catch (Exception e) {
                    AppControl.showAlert("error", "Giá trị số lượng không hợp lệ!");
                }
                ingredientTable.refresh();
                ingredientTable.setItems(model.getCurrentDetails());
                totalLbl.setText(String.valueOf(model.calTotal()));
            }
        };


        EventHandler<ActionEvent> buttonUpdateHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PurchaseDetail selected = ingredientTable.getSelectionModel().getSelectedItem();
                int rs = MessageDialog.showAlert(
                        "Warning", "Bạn muốn thay đổi thông tin nguyên liệu đặt?" +
                                "\n\nCác thay đổi sẽ mất nếu bạn không lưu.");
                if (rs == 1) {
                    try {
                        model.handleUpdateDetail(
                                selected,
                                ingredientCb.getValue(),
                                Integer.parseInt(qtyTxf.getText())
                        );
                    } catch (Exception e) {
                        AppControl.showAlert("error", "Giá trị số lượng không hợp lệ!" +
                                "\n\nHoặc bạn chưa chọn thành phần cần thay đổi");
                    }
                    ingredientTable.refresh();
                    totalLbl.setText(String.valueOf(model.calTotal()));
                }
            }
        };

        EventHandler<ActionEvent> buttonDeleteHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PurchaseDetail selected = ingredientTable.getSelectionModel().getSelectedItem();
                int rs = MessageDialog.showAlert(
                        "Warning", "Bạn muốn xóa nguyên liệu đặt?" +
                                "\n\nCác thay đổi sẽ mất nếu bạn không lưu.");
                if (rs == 1) {
                    try {
                        model.handleDeleteDetail(selected);
                    } catch (Exception e) {
                        System.out.println("Loi dong 172 file POInputController");
                    }
                    ingredientTable.setItems(model.getCurrentDetails());
                    totalLbl.setText(String.valueOf(model.calTotal()));
                }
            }
        };

        addBtn.setOnAction(buttonAddHandler);
        updateBtn.setOnAction(buttonUpdateHandler);
        deleteBtn.setOnAction(buttonDeleteHandler);
    }

    public void handleActionForm() {
        EventHandler<ActionEvent> buttonConfirmHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int rs = MessageDialog.showAlert(
                        "Warning", "Bạn muốn tạo mới đơn đặt hàng?" +
                                "\n\nCác thay đổi sẽ mất nếu bạn không lưu.");
                if (rs == 1) {
//                    try {
                        String vendor = vendorTxf.getText();
                        Employee staff = AppControl.currentUser;
                        Date date = Date.valueOf(dateLbl.getText());
                        model.handleAdd(
                                model.getCurrent(),
                                vendor,
                                staff,
                                date
                        );
//                    } catch (Exception e) {
//                        AppControl.showAlert("error", "Giá trị số lượng không hợp lệ!");
//                    }
                    ingredientTable.setItems(model.getCurrentDetails());
                }
            }
        };


        EventHandler<ActionEvent> buttonCancelHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int rs = MessageDialog.showAlert(
                        "Warning", "Bạn muốn thoát trạng thái hiện tại?" +
                                "\n\nCác thay đổi sẽ mất nếu bạn không lưu.");
                if (rs == 1) {
                    String filepath = "src/main/java/App/Depot/View/POManagementView.fxml";
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(new File(filepath).toURI().toURL());
                        MainApp.show(fxmlLoader.load());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };

        confirmBtn.setOnAction(buttonConfirmHandler);
        cancelBtn.setOnAction(buttonCancelHandler);
    }

}
