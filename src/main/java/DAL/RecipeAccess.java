package DAL;

import Entity.Ingredient;
import Entity.Product;
import Entity.ProductRecipe;
import Logic.Depot.IngredientManagement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RecipeAccess extends DataAccess {
    public ArrayList<Product> retrieve() {
        RecipeAccess recipeAccess = new RecipeAccess();
        IngredientManagement logic = new IngredientManagement();
        try {
            recipeAccess.createConnection();
            PreparedStatement prSt = recipeAccess.getConn().prepareStatement("call select_productrecipe()");
            ResultSet rs = prSt.executeQuery();
            ArrayList<Product> products = new ArrayList<>();
            while (rs.next()) {
                for (Product p : products) {
                    if (p.getProductName().equalsIgnoreCase(rs.getString(1))) {
                        p.getRecipe().addIngredient(
                                logic.findByName(rs.getString(2)),
                                rs.getInt(3)
                        );
                        p.getRecipe().setProductQty(rs.getInt(4));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void create(Product selected) {
    }
}

