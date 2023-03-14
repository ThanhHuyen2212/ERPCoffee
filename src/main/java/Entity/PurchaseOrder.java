package Entity;

import java.sql.Date;
import java.util.ArrayList;

public class PurchaseOrder {
    private int purchaseOrderId;
    private String supplier;
    private Date purchaseOrderDate;
    private int totalPrice;
    private Employee employeeCreate;
    private Employee employeeComfirm;

    private ArrayList<PurchaseDetail> details;

    public PurchaseOrder() {
        this.details = new ArrayList<>();
    }

    public PurchaseOrder(int purchaseOrderId, String supplier, Date purchaseOrderDate, int totalPrice,
                         Employee employeeCreate, Employee employeeComfirm, ArrayList<PurchaseDetail> details) {
        this.purchaseOrderId = purchaseOrderId;
        this.supplier = supplier;
        this.purchaseOrderDate = purchaseOrderDate;
        this.totalPrice = totalPrice;
        this.employeeCreate = employeeCreate;
        this.employeeComfirm = employeeComfirm;
        this.details = details;
    }

    public PurchaseOrder(int purchaseOrderId, String supplier, Date purchaseOrderDate, int totalPrice, Employee employeeCreate) {
        this.purchaseOrderId = purchaseOrderId;
        this.supplier = supplier;
        this.purchaseOrderDate = purchaseOrderDate;
        this.totalPrice = totalPrice;
        this.employeeCreate = employeeCreate;
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

    public Employee getEmployeeComfirm() {
        return employeeComfirm;
    }

    public void setEmployeeComfirm(Employee employeeComfirm) {
        this.employeeComfirm = employeeComfirm;
    }

    public ArrayList<PurchaseDetail> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<PurchaseDetail> details) {
        this.details = details;
    }

    public void addIngredient(Ingredient ingredient,int qty){
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
}
