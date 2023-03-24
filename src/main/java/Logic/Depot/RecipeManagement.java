package Logic.Depot;

import DAL.RecipeAccess;
import Entity.Ingredient;
import Entity.Product;

public class RecipeManagement {
    private RecipeAccess recipeDAO;

    public RecipeManagement() {
        recipeDAO = new RecipeAccess();
    }

    public void handleAdd(Product selected, String name, int qty) {
        selected.getRecipe().addIngredient(new IngredientManagement().findByName(name), qty);
    }

    public void handleUpdate(Product selected, Ingredient selectedIg, String name, int qty) {
        selected.getRecipe().getIngredientCosts().remove(selectedIg);
        handleAdd(selected, name, qty);
    }

    public void handleDelete(Product selected, Ingredient selectedIg) {
        selected.getRecipe().getIngredientCosts().remove(selectedIg);
    }

}
