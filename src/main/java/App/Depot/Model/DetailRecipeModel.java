package App.Depot.Model;

import Entity.Ingredient;
import Entity.Product;
import Entity.ProductRecipe;
import Logic.Depot.RecipeManagement;
public class DetailRecipeModel {
    private Product selected;
    private RecipeManagement logic;

    public DetailRecipeModel(Product selected) {
        this.selected = selected;
    }

    public DetailRecipeModel() {
        logic = new RecipeManagement();
    }

    public Product getSelected() {
        return selected;
    }

    public void setSelected(Product selected) {
        this.selected = selected;
        if(selected.getRecipe() == null) {
            selected.setRecipe(new ProductRecipe());
        }
    }

    public void handleAdd(String ingredientName, int qty) {
        logic.handleAdd(selected, ingredientName, qty);
    }

    public void handleUpdate(Ingredient selectedIg, String value, int qty) {
        logic.handleUpdate(selected, selectedIg, value, qty);
    }

    public void handleDelete(Ingredient selectedIg) {
        logic.handleDelete(selected, selectedIg);
    }

    public void handleUpdatePrdQty(int newQty) {
        logic.handleUpdatePrdQty(selected, newQty);
    }
}
