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
        this.currentDetails = FXCollections.observableArrayList(current.getDetails());
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
        return current.getEmployeeConfirm() != null;
    }

    public void receiveAll() {
        for(PurchaseDetail pd : current.getDetails()) {
            pd.setReceiveQty(pd.getOrderQty());
        }
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

    public int handleAdd(PurchaseOrder current, String vendor, Employee staff, Date date) {
        int id;
        id = logic.handleAdd(current, vendor, staff, date);
        purchaseOrderObservableList = FXCollections.observableArrayList(logic.getPurchaseOrders());
        return id;
    }

    public double calTotal() {
        double sum = 0;
        for (PurchaseDetail pd : current.getDetails()) {
            sum += calSubtotal(pd);
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
}
