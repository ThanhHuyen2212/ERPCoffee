package App.Depot.Model;

import Entity.PurchaseDetail;
import Entity.PurchaseOrder;
import Logic.Depot.PurchaseOrderManagement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class POModel {
    private PurchaseOrderManagement logic;
    private PurchaseOrder current;
    private ObservableList<PurchaseDetail> currentDetails;
    private ObservableList<PurchaseOrder> purchaseOrderObservableList;

    public POModel() {
    }

    public void init() {
        logic = new PurchaseOrderManagement();
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
        if(current.getEmployeeConfirm() != null) {
            return true;
        }
        return false;
    }

}
