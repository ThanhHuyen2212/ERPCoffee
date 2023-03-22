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
}
