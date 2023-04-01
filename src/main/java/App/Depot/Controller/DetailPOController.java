package App.Depot.Controller;

import App.Depot.Model.POModel;
import App.Depot.View.MessageDialog;
import App.ModuleManager.AppControl;
import Entity.PurchaseDetail;
import Entity.PurchaseOrder;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

import static App.Depot.Controller.IngredientManagementController.messageInvalidNumber;
import static App.Depot.Controller.IngredientManagementController.nf;

public class DetailPOController implements Initializable {
    @FXML
    private TableView<PurchaseDetail> detailTable;
    @FXML
    private TableColumn<PurchaseDetail, Integer> qtyCol;
    @FXML
    private TableColumn<PurchaseDetail, Integer> receiveCol;
    @FXML
    private TableColumn<PurchaseDetail, String> idCol;
    @FXML
    private TableColumn<PurchaseDetail, String> ingredientNameCol;
    @FXML
    private TableColumn<PurchaseDetail, String> priceCol;
    @FXML
    private TableColumn<PurchaseDetail, String> unitCol;
    @FXML
    private Label totalOrderLbl;
    @FXML
    private Label totalRevLbl;
    @FXML
    private Button cancelBtn;
    @FXML
    private Button confirmBtn;
    @FXML
    private Button revAllBtn;
    @FXML
    private Button printBtn;
    private POModel model;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new POModel();

        idCol.setCellValueFactory(data -> new SimpleStringProperty(
                String.valueOf(data.getValue().getIngredient().getIngredientId())
        ));
        ingredientNameCol.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getIngredient().getIngredientName()
        ));
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("orderQty"));
        receiveCol.setCellValueFactory(new PropertyValueFactory<>("receiveQty"));
        priceCol.setCellValueFactory(data -> new SimpleStringProperty(
                String.valueOf(data.getValue().getIngredient().getPrice())
        ));
        unitCol.setCellValueFactory(data -> new SimpleStringProperty(
                IngredientManagementController.setupUnit(data.getValue().getIngredient())
        ));

        printBtn.setVisible(false);

    }

    public void init(POModel modelOfSib, PurchaseOrder selected) {
        model = modelOfSib;
        model.setCurrent(selected);

        detailTable.setItems(model.getCurrentDetails());

        handleInitGUI();
        handleActionBtn();
    }

    public void handleInitGUI() {

        totalOrderLbl.setText(String.valueOf(nf.format(model.calTotal())));

        double sum = 0;
        for (PurchaseDetail pd : model.getCurrent().getDetails()) {
            sum += pd.getIngredient().getPrice() * pd.getReceiveQty();
        }
        totalRevLbl.setText(nf.format(sum));

//        If PO has been not confirmed, the confirmation button is visible
        if (model.isConfirm()) {
            confirmBtn.setVisible(false);
            revAllBtn.setVisible(false);
        } else {
            editableCols();
        }
    }

    public void editableCols() {
        receiveCol.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<>() {
            @Override
            public String toString(Integer integer) {
                return String.valueOf(integer);
            }

            @Override
            public Integer fromString(String s) {
                int value;
                try {
                    value = Integer.parseInt(s);
                    if(value < 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException e) {
                    AppControl.showAlert("error", messageInvalidNumber);
                    value = model.getCurrent().getDetails().get(detailTable.getSelectionModel().getSelectedIndex()).getReceiveQty();
                }
                return value;
            }
        }));

        receiveCol.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setReceiveQty(e.getNewValue());
        });

        detailTable.setEditable(true);
    }

    public void handleActionBtn() {
        EventHandler<ActionEvent> buttonRevAllHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                model.receiveAll();
                detailTable.refresh();
            }
        };

        EventHandler<ActionEvent> buttonConfirmHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int rs = MessageDialog.showAlert(
                        "Warning", "Bạn muốn lưu thông tin đơn đặt hàng?" +
                                "\n\nCác thay đổi sẽ mất nếu bạn không lưu.");
                if (rs == 1) {
                    boolean legal = true;
                    for (PurchaseDetail pd : model.getCurrent().getDetails()) {
                        if (pd.getReceiveQty() < 0 && legal) {
                            AppControl.showAlert("error", messageInvalidNumber);
                            legal = false;
                        }
                    }
                    if (legal) {
                        model.handleUpdateRevQty(AppControl.currentUser);
                        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
                    }
                }
            }
        };

        EventHandler<ActionEvent> buttonCancelHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int rs = 1;
                if (!model.isConfirm()) {
                    rs = MessageDialog.showAlert(
                            "Warning", "Bạn muốn thoát trạng thái hiện tại?" +
                                    "\n\nCác thay đổi sẽ mất nếu bạn không lưu.");
                }
                if (rs == 1) {
                    ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
                }
            }
        };

        revAllBtn.setOnAction(buttonRevAllHandler);
        confirmBtn.setOnAction(buttonConfirmHandler);
        cancelBtn.setOnAction(buttonCancelHandler);
    }
}
