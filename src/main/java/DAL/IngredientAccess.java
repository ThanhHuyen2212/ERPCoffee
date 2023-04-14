package DAL;

import Entity.Ingredient;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class IngredientAccess extends DataAccess {
    public ArrayList<Ingredient> retrieve() {
        IngredientAccess ingredientAccess = new IngredientAccess();
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        try {
            ingredientAccess.createConnection();
            PreparedStatement prSt = ingredientAccess.getConn().prepareStatement("call select_ingredient();");
            ResultSet rs = prSt.executeQuery();
            while(rs.next()){
                ingredients.add(new Ingredient(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getDate(7),
                        rs.getDate(8)
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ingredients;
    }


    public void create(Ingredient i) {
        IngredientAccess ingredientAccess = new IngredientAccess();
        try {
//            ingredientAccess.createConnection();
            PreparedStatement prSt = ingredientAccess.getConn().prepareStatement(
                    "call insert_ingredient(?, ?, ?, ?);");
//            call insert_ingredient(name_ingredient, type_ingredient, price_ingredient, limit_inventory);
            prSt.setString(1, i.getIngredientName());
            prSt.setString(2, i.getIngredientType());
            prSt.setInt(3, i.getPrice());
            prSt.setInt(4, i.getIngredientLimit());
            ResultSet rs = prSt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Ingredient i) {
        IngredientAccess ingredientAccess = new IngredientAccess();
        try {
//            ingredientAccess.createConnection();
            PreparedStatement prSt = ingredientAccess.getConn().prepareStatement(
                    "call update_ingredient(?, ?, ?, ?, ?);");
//            call update_ingredient(id_ingredient, name_ingredient, type_ingredient,
//                                      price_ingredient, limit_ingredient, date_delete)
            prSt.setInt(1, i.getIngredientId());
            prSt.setString(2, i.getIngredientName());
            prSt.setString(3, i.getIngredientType());
            prSt.setInt(4, i.getPrice());
            prSt.setInt(5, i.getIngredientLimit());
            prSt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void delete(Ingredient i, Date now) {
        IngredientAccess ingredientAccess = new IngredientAccess();
        try {
//            ingredientAccess.createConnection();
            PreparedStatement prSt = ingredientAccess.getConn().prepareStatement(
                    "call update_ingredient_where_name(?, ?, ?)");
//            call update_ingredient_where_name(name_column, value_update, name_ingredient)
            prSt.setString(1, "DeleteAt");
            prSt.setDate(2, now);
            prSt.setString(3, i.getIngredientName());
            prSt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
