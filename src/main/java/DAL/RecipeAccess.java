package DAL;

import Entity.Ingredient;
import Entity.Product;
import Logic.Depot.IngredientManagement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RecipeAccess extends DataAccess {
    public ArrayList<Product> retrieve(ArrayList<Product> products) {
        RecipeAccess recipeAccess = new RecipeAccess();
        IngredientManagement logic = new IngredientManagement();
        try {
            recipeAccess.createConnection();
            PreparedStatement prSt = recipeAccess.getConn().prepareStatement("call select_productrecipe_with_id_product(?);");
            for (Product p : products) {
                prSt.setInt(1, p.getProductId());
                ResultSet rs = prSt.executeQuery();
                while (rs.next()) {
                    p.getRecipe().addIngredient(
                            logic.findByName(rs.getString(2)),
                            rs.getInt(3)
                    );
                    p.getRecipe().setProductQty(rs.getInt(4));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void addRecipe(Product selected, Ingredient i) {
        RecipeAccess recipeAccess = new RecipeAccess();
        try {
            recipeAccess.createConnection();
            PreparedStatement prSt = recipeAccess.getConn().prepareStatement(
                    "call insert_productrecipe(?, ?, ?, ?);");
//            call insert_productrecipe(name_product, name_ingredient, qty_ingredient, qty_product)
            prSt.setString(1, selected.getProductName());
            prSt.setString(2, i.getIngredientName());
            prSt.setInt(3, selected.getRecipe().getIngredientCosts().get(i));
            prSt.setInt(4, selected.getRecipe().getProductQty());
            prSt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateRecipe(Product selected) {
    }

    public void deleteRecipe(Product selected, Ingredient i) {
        RecipeAccess recipeAccess = new RecipeAccess();
        try {
            recipeAccess.createConnection();
            PreparedStatement prSt = recipeAccess.getConn().prepareStatement(
                    "call delete_productrecipe_with_id(?, ?);");
            prSt.setInt(1, selected.getProductId());
            prSt.setInt(2, i.getIngredientId());
            prSt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updatePrdQty(Product selected) {
        RecipeAccess recipeAccess = new RecipeAccess();
        try {
            recipeAccess.createConnection();
            PreparedStatement prSt = recipeAccess.getConn().prepareStatement(
                    "call update_productrecipe(?, ?, ?, ?);");
//            call update_productrecipe(name_column, value_update, id_product, id_ingredient)
            prSt.setString(1,"ProductQty");
            prSt.setInt(2, selected.getRecipe().getProductQty());
            prSt.setInt(3, selected.getProductId());
            prSt.setString(4, null);
            prSt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

