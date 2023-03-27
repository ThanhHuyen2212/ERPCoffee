package Logic.Depot;

import DAL.IngredientAccess;
import Entity.Ingredient;

import java.sql.Date;
import java.util.ArrayList;

public class IngredientManagement {
    private ArrayList<Ingredient> ingredients;
    private IngredientAccess ingredientDAO;

    public IngredientManagement() {
        ingredientDAO = new IngredientAccess();
        ingredients = ingredientDAO.retrieve();
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
        ingredientDAO.create(i);
        long millis=System.currentTimeMillis();
        i.setCreateDate(new java.sql.Date(millis));
        i.setIngredientId(ingredients.size());
        ingredients.add(i);
    }

    public void update(int index, Ingredient i) {
        ingredientDAO.update(i);
        ingredients.set(index, i);
    }

    public void delete(Ingredient i) {
        long millis=System.currentTimeMillis();
        Date now = new java.sql.Date(millis);
        i.setCreateDate(now);
        ingredientDAO.delete(i, now);
//        ingredients.remove(i);
    }

    public Ingredient findById(int id) {
        for (Ingredient i : this.ingredients) {
            if(id == i.getIngredientId()) {
                return i;
            }
        }
        return null;
    }

    public Ingredient findByName(String name) {
        for (Ingredient i : this.ingredients) {
            if(name.equalsIgnoreCase(i.getIngredientName())) {
                return i;
            }
        }
        return null;
    }

    public ArrayList<String> getNameList() {
        ArrayList<String> list = new ArrayList<>();
        for (Ingredient i : this.ingredients) {
            list.add(i.getIngredientName());
        }
        return list;
    }
}
