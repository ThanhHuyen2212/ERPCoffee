package DAL;

import Entity.PurchaseDetail;
import Entity.PurchaseOrder;
import Logic.Depot.IngredientManagement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class POAccess extends DataAccess {
    public ArrayList<PurchaseOrder> retrieve() {
        POAccess poAccess = new POAccess();
        ArrayList<PurchaseOrder> list = new ArrayList<>();

        try {
            poAccess.createConnection();
            PreparedStatement prSt = poAccess.getConn().prepareStatement("call select_purchaseorder()");
            ResultSet rs = prSt.executeQuery();
            while (rs.next()) {
                list.add(new PurchaseOrder(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getDate(3),
                        rs.getInt(4)
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (PurchaseOrder po : list) {
            po.setDetails(PODetailsRetrieve(po));
        }

        return list;
    }

    public ArrayList<PurchaseDetail> PODetailsRetrieve(PurchaseOrder po) {
        POAccess poAccess = new POAccess();
        try {
            poAccess.createConnection();
            PreparedStatement prSt = poAccess.getConn().prepareStatement("call select_purchaseorderdetailwithid()");
            ResultSet rs = prSt.executeQuery();
            IngredientManagement ingredientManagement = new IngredientManagement();
            while (rs.next()) {
                po.addDetails(
                        ingredientManagement.findByName(rs.getString(2)),
                        rs.getInt(3),
                        rs.getInt(4)
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void create(PurchaseOrder selected) {
    }

    public void update(PurchaseOrder curr) {
//        Update quantity and update employee confirm
    }

    public int add(PurchaseOrder current) {
        POAccess poAccess = new POAccess();
        int id = 0;
        try {
            poAccess.createConnection();
            PreparedStatement prSt = poAccess.getConn().prepareStatement(
                    "call insert_purchaseorder(?, ?, ?)");
//            "call insert_purchaseorder(name_supplier, totalprice_purchase, phone_emp_create)");
            prSt.setString(1, current.getSupplier());
            prSt.setInt(2, current.getTotalPrice());
            prSt.setString(3, current.getEmployeeCreate().getPhone());
            ResultSet rs = prSt.executeQuery();
            while (rs.next()) {
                id = rs.getInt(1);
            }
            addDetails(current);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    public void addDetails(PurchaseOrder current) {
        POAccess poAccess = new POAccess();
        try {
            poAccess.createConnection();
            for(PurchaseDetail pd : current.getDetails()) {
                PreparedStatement prSt = poAccess.getConn().prepareStatement(
                        "call insert_purchaseorderdetail(phone_emp_create, name_ingredient, qty_order)");
//            call insert_purchaseorderdetail(phone_emp_create, name_ingredient, qty_order)
                prSt.setInt(1, current.getEmployeeCreate().getEmployeeId());
                prSt.setString(2, pd.getIngredient().getIngredientName());
                prSt.setInt(3, pd.getOrderQty());
                ResultSet rs = prSt.executeQuery();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
