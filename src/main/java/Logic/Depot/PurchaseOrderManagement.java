package Logic.Depot;

import DAL.POAccess;
import Entity.Employee;
import Entity.Ingredient;
import Entity.PurchaseDetail;
import Entity.PurchaseOrder;

import java.sql.Date;
import java.util.ArrayList;

public class PurchaseOrderManagement {
    private ArrayList<PurchaseOrder> purchaseOrders;
    private POAccess purchaseOrderDAO;

    public PurchaseOrderManagement() {
        purchaseOrderDAO = new POAccess();
        this.purchaseOrders = purchaseOrderDAO.retrieve();
    }

    public ArrayList<PurchaseOrder> getPurchaseOrders() {
        return purchaseOrders;
    }

    public void setPurchaseOrders(ArrayList<PurchaseOrder> purchaseOrders) {
        this.purchaseOrders = purchaseOrders;
    }

    public void handleUpdateRevQty(PurchaseOrder curr) {
//        Not handling employee
        for(PurchaseOrder po : purchaseOrders) {
            if (po.equals(curr)) {
                curr.setEmployeeConfirm(
                        new Employee()
                );
            }
        }
        purchaseOrderDAO.update(curr);
    }

    public void handleAddDetail(PurchaseOrder current, Ingredient ingredient, int qty) {
        current.addDetails(ingredient, qty);
    }

    public void handleUpdateDetail(PurchaseDetail selected, Ingredient ingredient, int qty) {
        selected.setIngredient(ingredient);
        selected.setOrderQty(qty);
    }

    public void handleDeleteDetail(PurchaseOrder current, PurchaseDetail selected) {
        current.getDetails().remove(selected);
    }

    public void handleAdd(PurchaseOrder current, String vendor, Integer staffId, Date date) {
        current.setSupplier(vendor);
//        Xu ly findById employee
        current.setEmployeeCreate(new Employee());
        current.setPurchaseOrderDate(date);
        purchaseOrders.add(current);
        int id = purchaseOrderDAO.create(current);
        current.setPurchaseOrderId(id);
    }
}
