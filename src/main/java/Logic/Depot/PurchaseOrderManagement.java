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
        purchaseOrderDAO.update(curr);
        for(PurchaseDetail pd : curr.getDetails()) {
            pd.getIngredient().setIngredientStorage(
                    pd.getIngredient().getIngredientStorage() + pd.getReceiveQty()
            );
        }
        curr.setTotalPrice((int) Math.round(calTotalRev(curr)));
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

    public int handleAdd(PurchaseOrder current, String vendor, Employee staff, Date date) {
        int id;
        current.setSupplier(vendor);
        current.setEmployeeCreate(staff);
        current.setPurchaseOrderDate(date);
        purchaseOrders.add(current);
        id = purchaseOrderDAO.create(current);
        current.setPurchaseOrderId(id);
        return id;
    }

    private double calTotalRev(PurchaseOrder current) {
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
}
