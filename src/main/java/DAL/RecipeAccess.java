package DAL;

import Entity.Ingredient;
import Entity.Product;
import Logic.Depot.IngredientManagement;
import Logic.Management;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class RecipeAccess extends DataAccess {
    public void retrieve(ArrayList<Product> products) {
        RecipeAccess recipeAccess = new RecipeAccess();
        IngredientManagement logic = Management.ingredientManagement;
        try {
//            recipeAccess.createConnection();
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
    }

    public void addRecipe(Product selected, Ingredient i) {
        RecipeAccess recipeAccess = new RecipeAccess();
        try {
//            recipeAccess.createConnection();
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
//            recipeAccess.createConnection();
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
//            recipeAccess.createConnection();
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

    public void reduceInventory(Product p, int batch) {
        RecipeAccess recipeAccess = new RecipeAccess();
        try {
            PreparedStatement prSt = recipeAccess.getConn().prepareStatement(
                    "call update_ingredient_where_name(?, ?, ?);");
//            call update_ingredient_where_name(name_column, value_update, name_ingredient)

//            System.out.println(p.getRecipe().getIngredientCosts().entrySet().size());
            for(Map.Entry<Ingredient, Integer> recipe : p.getRecipe().getIngredientCosts().entrySet()) {
                prSt.setString(1,"IngredientStorage");
                prSt.setInt(2, (recipe.getKey().getIngredientStorage() - batch * recipe.getValue()));
                prSt.setString(3, recipe.getKey().getIngredientName());
                prSt.executeQuery();
            }

        } catch (SQLException e) {
            System.out.println("So luong nguyen lieu khong du");
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Product> retrieveSimpleProduct() {
        ProductAccess productAccess = new ProductAccess();
        ArrayList<Product> products = new ArrayList<>();
        try {

            PreparedStatement prSt = productAccess.getConn().prepareStatement("call select_product_delete_is_null()");
            ResultSet resultSet = prSt.executeQuery();
            while (resultSet!=null &&resultSet.next()){
                products.add(new Product(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getDate(3),
                        resultSet.getDate(4),
                        resultSet.getString(5),
                        resultSet.getString(6)));
            }

            RecipeAccess recipeAccess = new RecipeAccess();
            recipeAccess.retrieve(products);
        }catch (SQLException e){
            throw new RuntimeException();
        }
        return products;
    }
}

