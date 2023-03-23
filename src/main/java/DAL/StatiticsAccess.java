package DAL;

import Logic.Statitics.IStatitics;
import javafx.util.Pair;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class StatiticsAccess extends DataAccess implements IAccessStatitics{
    public StatiticsAccess() {
        createConnection();
    }

    @Override
    public ArrayList<IStatitics.Order> getRevenueStatitics(Date start, Date end) {
        createConnection();
        ArrayList<IStatitics.Order> staData = new ArrayList<>();
        ResultSet rs = null;
        try {
            PreparedStatement prStm = getConn().prepareStatement("call select_statistic_day(?,?)");
            prStm.setDate(1,start);
            prStm.setDate(2,end);
            rs = prStm.executeQuery();
            while(rs.next()) {
                staData.add(new IStatitics.Order(
                        rs.getDate(1),
                        rs.getDouble(2),
                        rs.getDouble(4),
                        rs.getDouble(3)
                        )
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        closeConnection();
        return staData;
    }

    @Override
    public ArrayList<IStatitics.Product> getProductStatitics(Date start, Date end) {
        createConnection();
        ArrayList<IStatitics.Product> staData = new ArrayList<>();
        HashMap<String,ArrayList<Pair<String,Number>>> sizeList = new HashMap<>();
        ResultSet rs = null;
        try {
            PreparedStatement preStmt = getConn().prepareStatement(
                    "call select_statistic_product_size(?, ?)"
            );
            preStmt.setDate(1,start);
            preStmt.setDate(2,end);
            rs = preStmt.executeQuery();
            while(rs.next()){
                if(sizeList.get(rs.getString(1)) == null){
                    ArrayList<Pair<String, Number>> tmp = new ArrayList<>();
                    tmp.add(new Pair<>(rs.getString(2),rs.getDouble(3)));
                    sizeList.put(rs.getString(1),tmp);
                }else{
                    sizeList.get(rs.getString(1))
                            .add(new Pair<>(
                                    rs.getString(2),
                                    rs.getDouble(3)
                            ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            PreparedStatement preStmt = getConn().prepareStatement(
                    "call select_statistic_product(?, ?)"
            );
            preStmt.setDate(1,start);
            preStmt.setDate(2,end);
            rs = preStmt.executeQuery();
            while(rs.next()){
                staData.add(new IStatitics.Product(
                        rs.getString(1),
                        rs.getDouble(2),
                        sizeList.get(rs.getString(1)),
                        rs.getDouble(3))
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        closeConnection();
        return staData;
    }

    @Override
    public ArrayList<IStatitics.Category> getCategoryStatitics(Date start, Date end) {

        createConnection();
        ArrayList<IStatitics.Category> staData = new ArrayList<>();
        ResultSet rs = null;
        try {
            PreparedStatement prStm = getConn().prepareStatement("call select_statistic_category(?,?)");
            prStm.setDate(1,start);
            prStm.setDate(2,end);
            rs = prStm.executeQuery();
            while(rs.next()) {
                staData.add(new IStatitics.Category(
                        rs.getString(1),
                        rs.getDouble(3),
                        rs.getDouble(2)
                        )
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        closeConnection();
        return staData;

    }
}
