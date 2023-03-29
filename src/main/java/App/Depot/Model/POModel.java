package App.Depot.Model;

import Entity.Employee;
import Entity.PurchaseDetail;
import Entity.PurchaseOrder;
import Logic.Depot.IngredientManagement;
import Logic.Depot.PurchaseOrderManagement;
import Logic.Management;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;

public class POModel {
    private PurchaseOrderManagement logic;
    private PurchaseOrder current;
    private ObservableList<PurchaseDetail> currentDetails;
    private ObservableList<PurchaseOrder> purchaseOrderObservableList;

    public POModel() {
    }

    public void init() {
        logic = Management.purchaseOrderManagement;
        current = new PurchaseOrder();
        currentDetails = FXCollections.observableArrayList(current.getDetails());
        purchaseOrderObservableList = FXCollections.observableArrayList(logic.getPurchaseOrders());
    }

    public PurchaseOrderManagement getLogic() {
        return logic;
    }

    public void setLogic(PurchaseOrderManagement logic) {
        this.logic = logic;
    }

    public PurchaseOrder getCurrent() {
        return current;
    }

    public void setCurrent(PurchaseOrder current) {
        this.current = current;
    }

    public ObservableList<PurchaseDetail> getCurrentDetails() {
        return currentDetails;
    }

    public void setCurrentDetails(ObservableList<PurchaseDetail> currentDetails) {
        this.currentDetails = currentDetails;
    }

    public ObservableList<PurchaseOrder> getPurchaseOrderObservableList() {
        return purchaseOrderObservableList;
    }

    public void setPurchaseOrderObservableList(ObservableList<PurchaseOrder> purchaseOrderObservableList) {
        this.purchaseOrderObservableList = purchaseOrderObservableList;
    }

    public boolean isConfirm() {
        if (current.getEmployeeConfirm() != null) {
            return true;
        }
        return false;
    }

    public void handleUpdateRevQty(Employee currentUser) {
        current.setEmployeeConfirm(currentUser);
        logic.handleUpdateRevQty(current);
    }

    public void handleAddDetail(String value, int qty) {
        logic.handleAddDetail(current, new IngredientManagement().findByName(value), qty);
        currentDetails = FXCollections.observableArrayList(current.getDetails());
    }

    public void handleUpdateDetail(PurchaseDetail selected, String value, int qty) {
        logic.handleUpdateDetail(selected, new IngredientManagement().findByName(value), qty);
//        currentDetails = FXCollections.observableArrayList(current.getDetails());
    }

    public void handleDeleteDetail(PurchaseDetail selected) {
        logic.handleDeleteDetail(current, selected);
        currentDetails = FXCollections.observableArrayList(current.getDetails());
    }

    public void handleAdd(PurchaseOrder current, String vendor, Employee staff, Date date) {
        logic.handleAdd(current, vendor, staff, date);
        purchaseOrderObservableList = FXCollections.observableArrayList(logic.getPurchaseOrders());
    }

    public double calTotal() {
        double sum = 0;
        for (PurchaseDetail pd : current.getDetails()) {
            if (pd.getIngredient().getIngredientName().equalsIgnoreCase("trứng")
                    || pd.getIngredient().getIngredientName().equalsIgnoreCase("bánh mì")) {
                sum += (double) pd.getOrderQty() * pd.getIngredient().getPrice();
            } else {
                sum += (double) pd.getOrderQty() / 1000.0 * pd.getIngredient().getPrice();
            }
        }
        return sum;
    }

    public double calSubtotal(PurchaseDetail pd) {
        if (pd.getIngredient().getIngredientName().equalsIgnoreCase("trứng")
                || pd.getIngredient().getIngredientName().equalsIgnoreCase("bánh mì")) {
            return (double) pd.getOrderQty() * pd.getIngredient().getPrice();
        } else {
            return (double) pd.getOrderQty() / 1000.0 * pd.getIngredient().getPrice();
        }
    }

    public void handleConfirm(PurchaseOrder current, String vendor, Integer staffId, Date date) {
        purchaseOrderObservableList = FXCollections.observableArrayList(logic.getPurchaseOrders());
    }
}
