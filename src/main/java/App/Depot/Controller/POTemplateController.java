package App.Depot.Controller;

import App.Depot.Model.POModel;
import Entity.PurchaseDetail;
import Entity.PurchaseOrder;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

import static App.Depot.Controller.IngredientManagementController.nf;
import static App.Depot.Controller.IngredientManagementController.sdf;

public class POTemplateController implements Initializable {
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
    private Label dateLbl;
    @FXML
    private Label idLbl;
    @FXML
    private Label confirmStaffLbl;
    @FXML
    private Label createStaffLbl;
    @FXML
    private Label totalOrderLbl;
    @FXML
    private Label totalRevLbl;
    @FXML
    private Label vendorLbl;
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

    }

    public void init(POModel modelOfSib, PurchaseOrder selected) {
        model = modelOfSib;
        model.setCurrent(selected);

        detailTable.setItems(model.getCurrentDetails());
        dateLbl.setText(sdf.format(model.getCurrent().getPurchaseOrderDate()));
        idLbl.setText(String.valueOf(model.getCurrent().getPurchaseOrderId()));
        createStaffLbl.setText(model.getCurrent().getEmployeeCreate().getName());
        confirmStaffLbl.setText(
                model.getCurrent().getEmployeeConfirm() == null
                        ? ""
                        : model.getCurrent().getEmployeeConfirm().getName()
                );
        totalOrderLbl.setText(String.valueOf(nf.format(model.calTotal())));
        totalRevLbl.setText(nf.format(model.getCurrent().getTotalPrice()));
        vendorLbl.setText(model.getCurrent().getSupplier());
    }


}


