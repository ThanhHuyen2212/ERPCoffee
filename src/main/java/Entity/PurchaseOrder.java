package Entity;

import java.sql.Date;
import java.util.ArrayList;

public class PurchaseOrder {
    private int purchaseOrderId;
    private String supplier;
    private Date purchaseOrderDate;
    private int totalPrice;
    private Employee employeeCreate;
    private Employee employeeConfirm;

    private ArrayList<PurchaseDetail> details;

    public PurchaseOrder() {
        this.details = new ArrayList<>();
    }

    public PurchaseOrder(int purchaseOrderId, String supplier, Date purchaseOrderDate, int totalPrice,
                         Employee employeeCreate, Employee employeeConfirm, ArrayList<PurchaseDetail> details) {
        this.purchaseOrderId = purchaseOrderId;
        this.supplier = supplier;
        this.purchaseOrderDate = purchaseOrderDate;
        this.totalPrice = totalPrice;
        this.employeeCreate = employeeCreate;
        this.employeeConfirm = employeeConfirm;
        this.details = details;
    }

    public PurchaseOrder(int purchaseOrderId, String supplier, Date purchaseOrderDate, int totalPrice, Employee employeeCreate, Employee employeeConfirm) {
        this.purchaseOrderId = purchaseOrderId;
        this.supplier = supplier;
        this.purchaseOrderDate = purchaseOrderDate;
        this.totalPrice = totalPrice;
        this.employeeCreate = employeeCreate;
        this.employeeConfirm = employeeConfirm;
        this.details = new ArrayList<>();
    }

    public PurchaseOrder(int purchaseOrderId, String supplier, Date purchaseOrderDate, int totalPrice) {
        this.purchaseOrderId = purchaseOrderId;
        this.supplier = supplier;
        this.purchaseOrderDate = purchaseOrderDate;
        this.totalPrice = totalPrice;
        this.details = new ArrayList<>();
    }

    public int getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(int purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public Date getPurchaseOrderDate() {
        return purchaseOrderDate;
    }

    public void setPurchaseOrderDate(Date purchaseOrderDate) {
        this.purchaseOrderDate = purchaseOrderDate;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Employee getEmployeeCreate() {
        return employeeCreate;
    }

    public void setEmployeeCreate(Employee employeeCreate) {
        this.employeeCreate = employeeCreate;
    }

    public Employee getEmployeeConfirm() {
        return employeeConfirm;
    }

    public void setEmployeeConfirm(Employee employeeConfirm) {
        this.employeeConfirm = employeeConfirm;
    }

    public ArrayList<PurchaseDetail> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<PurchaseDetail> details) {
        this.details = details;
    }

    public void addDetails(Ingredient ingredient, int qty){
        PurchaseDetail tmp = new PurchaseDetail(ingredient,qty);
        boolean existed = false;
        for(PurchaseDetail pd : details){
            if(pd.equal(tmp)){
                pd.setOrderQty(pd.getOrderQty() + tmp.getOrderQty());
                existed = true;
            }
        }
        if(!existed){
            details.add(tmp);
        }
    }

    public void addDetails(Ingredient ingredient, int orderQty, int revAty){
        PurchaseDetail tmp = new PurchaseDetail(ingredient, orderQty, revAty);
        boolean existed = false;
        for(PurchaseDetail pd : details){
            if(pd.equal(tmp)){
                pd.setOrderQty(pd.getOrderQty() + tmp.getOrderQty());
                existed = true;
            }
        }
        if(!existed){
            details.add(tmp);
        }
    }
}
