package Entity;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductRecipe {

    private HashMap<Ingredient,Integer> ingredientCosts;
    private int productQty;

    public ProductRecipe() {
    }

    public ProductRecipe(HashMap<Ingredient, Integer> ingredientCosts, int productQty) {
        this.ingredientCosts = ingredientCosts;
        this.productQty = productQty;
    }

    public HashMap<Ingredient, Integer> getIngredientCosts() {
        return ingredientCosts;
    }

    public void setIngredientCosts(HashMap<Ingredient, Integer> ingredientCosts) {
        this.ingredientCosts = ingredientCosts;
    }

    public int getProductQty() {
        return productQty;
    }

    public void setProductQty(int productQty) {
        this.productQty = productQty;
    }

    public ArrayList<Ingredient> getIngredientList(){
        return new ArrayList<>(
            ingredientCosts.keySet().stream().toList()
        );
    }

    public void addIngredient(Ingredient ingredient,int qty){
        ingredientCosts.put(ingredient,qty);
    }
}
