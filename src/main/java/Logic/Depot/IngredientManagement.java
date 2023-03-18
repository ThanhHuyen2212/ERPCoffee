package Logic.Depot;

import DAL.IngredientDAO;
import Entity.Ingredient;

import java.util.ArrayList;

public class IngredientManagement {
    private ArrayList<Ingredient> ingredients;
    private IngredientDAO ingredientDAO;

    public IngredientManagement() {
//        ingredients = ingredientDAO.retrieve();
        ingredients = new ArrayList<>();
        ingredients.add(new Ingredient(
                1,
                "Ca phe hat",
                "Nguyen lieu kho",
                12,
                10,
                120000));
        ingredients.add(new Ingredient(
                1,
                "Bot matcha",
                "Nguyen lieu kho",
                22,
                7,
                90000));
    }

    public IngredientManagement(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
    public void create(Ingredient i) {
//        int id = ingredientDAO.create(i);
        ingredients.add(i);
    }

    public void update(int index, Ingredient i) {
//        int id = ingredientDAO.create(i);
        ingredients.set(index, i);
    }

    public void delete(Ingredient i) {
//        ingredientDAO.delete(i);
    }
}
