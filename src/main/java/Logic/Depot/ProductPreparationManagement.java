package Logic.Depot;

import App.ModuleManager.AppControl;
import DAL.RecipeAccess;
import Entity.Ingredient;
import Entity.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductPreparationManagement {
    public static HashMap<Product, Integer> preparedList = new HashMap<>();
    private ArrayList<Product> products;
    private HashMap<Product, Integer> preparations;
    private RecipeAccess recipeAccess;

    public ProductPreparationManagement() {
        products = RecipeAccess.retrieveSimpleProduct();
        preparations = new HashMap<>();
        recipeAccess = new RecipeAccess();
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public HashMap<Product, Integer> getPreparations() {

        return preparations;
    }

    public void setPreparations(HashMap<Product, Integer> preparations) {
        this.preparations = preparations;
    }

    public void addPreparation(Product p, int qty) {
        if (preparations.containsKey(p)) {
            preparations.replace(p, qty);
        } else {
            preparations.put(p, qty);
        }
    }

    public boolean isEnoughInventory(Product p, int batch) {
        for (Map.Entry<Ingredient, Integer> recipe : p.getRecipe().getIngredientCosts().entrySet()) {
            Ingredient i = new IngredientManagement().findById(recipe.getKey().getIngredientId());
            if (batch * recipe.getValue() > i.getIngredientStorage()) {
                AppControl.showAlert("error", "Số lượng nguyên liệu tồn kho không đủ để sản xuất " +
                        p.getProductName());
                return false;
            }
        }
        return true;
    }

    public void handlePrepare() {
        for (Map.Entry<Product, Integer> productEntryPrep : preparations.entrySet()) {
            if(isEnoughInventory(productEntryPrep.getKey(), productEntryPrep.getValue())) {
                recipeAccess.reduceInventory(productEntryPrep.getKey(), productEntryPrep.getValue());
                ProductPreparationManagement.preparedList.put(
                        productEntryPrep.getKey(),
                        productEntryPrep.getValue() * productEntryPrep.getKey().getRecipe().getProductQty()
                );
            }

        }
    }
}
