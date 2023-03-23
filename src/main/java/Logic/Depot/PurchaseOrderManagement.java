package Logic.Depot;

import DAL.POAccess;
import Entity.PurchaseOrder;

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
}
