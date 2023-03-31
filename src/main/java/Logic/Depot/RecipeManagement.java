package Logic.Depot;

import DAL.RecipeAccess;
import Entity.Ingredient;
import Entity.Product;

public class RecipeManagement {
    private final RecipeAccess recipeDAO;

    public RecipeManagement() {
        recipeDAO = new RecipeAccess();
    }

    public void handleAdd(Product selected, String ingredientName, int qty) {
        Ingredient i = new IngredientManagement().findByName(ingredientName);
        selected.getRecipe().addIngredient(i, qty);
        recipeDAO.addRecipe(selected, i);
    }

    public void handleUpdate(Product selected, Ingredient selectedIg, String name, int qty) {
        selected.getRecipe().getIngredientCosts().remove(selectedIg);
        handleDelete(selected, selectedIg);
        handleAdd(selected, name, qty);
    }

    public void handleDelete(Product selected, Ingredient selectedIg) {
        selected.getRecipe().getIngredientCosts().remove(selectedIg);
        recipeDAO.deleteRecipe(selected, selectedIg);
    }

    public void handleUpdatePrdQty(Product selected, int newQty) {
        selected.getRecipe().setProductQty(newQty);
        recipeDAO.updatePrdQty(selected);
    }
}
