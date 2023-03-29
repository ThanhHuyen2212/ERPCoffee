package DAL;

import Entity.PurchaseDetail;
import Entity.PurchaseOrder;
import Logic.Depot.IngredientManagement;
import Logic.Management;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class POAccess extends DataAccess {
    public ArrayList<PurchaseOrder> retrieve() {
        POAccess poAccess = new POAccess();
        ArrayList<PurchaseOrder> list = new ArrayList<>();
        EmployeeAccess employeeAccessManagement = new EmployeeAccess();
        employeeAccessManagement.getEmployee();
        try {
            PreparedStatement prSt = poAccess.getConn().prepareStatement("call select_purchaseorder();");
            ResultSet rs = prSt.executeQuery();
            while (rs.next()) {
                list.add(new PurchaseOrder(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getDate(3),
                        rs.getInt(4),
                        employeeAccessManagement.findById(rs.getInt(5)),
                        employeeAccessManagement.findById(rs.getInt(6))
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (PurchaseOrder po : list) {
            PODetailsRetrieve(po);
        }

        System.out.println(list.size());
        return list;
    }

    public void PODetailsRetrieve(PurchaseOrder po) {
        POAccess poAccess = new POAccess();
        try {
            PreparedStatement prSt = poAccess.getConn().prepareStatement("call select_purchaseorderdetail_with_id(?);");
//            call select_purchaseorderdetail_with_id(id_purchaseorder)
            prSt.setInt(1, po.getPurchaseOrderId());
            IngredientManagement ingredientManagement = Management.ingredientManagement;
            ResultSet rs = prSt.executeQuery();
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
    }

    public void update(PurchaseOrder curr) {
//        Update quantity and update employee confirm
        POAccess poAccess = new POAccess();
        try {
            PreparedStatement prSt1 = poAccess.getConn().prepareStatement(
                    "call update_employeeconfirm_purchaseorder(?, ?);");
//            call update_employeeconfirm_purchaseorder(id_purchaseorder, id_employeeconfirm)
            prSt1.setInt(1, curr.getPurchaseOrderId());
            prSt1.setInt(2, curr.getEmployeeConfirm().getEmployeeId());
            prSt1.executeQuery();

            PreparedStatement prSt = poAccess.getConn().prepareStatement(
                    "call update_receiveqty_purchaseorderdetail(?, ?, ?);");
//            call update_receiveqty_purchaseorderdetail(id_purchaseorder, id_ingredient, receiveqty)
            for(PurchaseDetail pd : curr.getDetails()) {
                prSt.setInt(1, curr.getPurchaseOrderId());
                prSt.setInt(2, pd.getIngredient().getIngredientId());
                prSt.setInt(3, pd.getReceiveQty());
                prSt.executeQuery();
            }

            RecipeAccess recipeAccess = new RecipeAccess();
            PreparedStatement prSt2 = recipeAccess.getConn().prepareStatement(
                    "call update_ingredient_where_name(?, ?, ?);");
//            call update_ingredient_where_name(name_column, value_update, name_ingredient)
            for(PurchaseDetail pd : curr.getDetails()) {
                prSt2.setString(1,"IngredientStorage");
                prSt2.setInt(2, pd.getIngredient().getIngredientStorage() + pd.getReceiveQty());
                prSt2.setString(3, pd.getIngredient().getIngredientName());
                prSt2.executeQuery();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int create(PurchaseOrder current) {
        POAccess poAccess = new POAccess();
        int id = 0;
        try {
            PreparedStatement prSt = poAccess.getConn().prepareStatement(
                    "call insert_purchaseorder(?, ?)");
//            call insert_purchaseorder(name_supplier, id_emp_create)
            prSt.setString(1, current.getSupplier());
            prSt.setInt(2, current.getEmployeeCreate().getEmployeeId());
            ResultSet rs = prSt.executeQuery();
            while (rs.next()) {
                id = rs.getInt(1);
            }
            addDetails(current, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    public void addDetails(PurchaseOrder current, int poId) {
        POAccess poAccess = new POAccess();
        try {
//            poAccess.createConnection();
            for(PurchaseDetail pd : current.getDetails()) {
                PreparedStatement prSt = poAccess.getConn().prepareStatement(
                        "call insert_purchaseorderdetail(?, ?, ?);");
//                call insert_purchaseorderdetail(id_purchaseorder, id_ingredient, qty_order)
                prSt.setInt(1, poId);
                prSt.setInt(2, pd.getIngredient().getIngredientId());
                prSt.setInt(3, pd.getOrderQty());
                ResultSet rs = prSt.executeQuery();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
