package App.Depot.Model;

import Entity.Ingredient;
import Entity.Product;
import Logic.Depot.IngredientManagement;
import Logic.Depot.RecipeManagement;

import java.util.HashMap;

public class DetailRecipeModel {
    private Product selected;
    private HashMap<Ingredient,Integer> details;
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
    }

    public HashMap<Ingredient, Integer> getDetails() {
        if(details == null) details = new HashMap<>(){{
            Ingredient ig = new Ingredient();
            ig.setIngredientName("asdasd");
            put(ig,1);
            ig = new Ingredient();
            ig.setIngredientName("asdasd");
            put(ig,1);
            ig = new Ingredient();
            ig.setIngredientName("asdasd");
            put(ig,1);
            ig = new Ingredient();
            ig.setIngredientName("asdasd");
            put(ig,1);
        }};
        return details;
    }

    public void setDetails(HashMap<Ingredient, Integer> details) {
        this.details = details;
    }

    public void handleCreate(String name, int qty) {
        selected.getRecipe().addIngredient(new IngredientManagement().findByName(name), qty);
        logic.create(selected);
        System.out.println(selected.getRecipe().getIngredientCosts().size());
    }
}
