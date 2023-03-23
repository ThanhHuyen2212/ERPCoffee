package Logic.Depot;

import App.Depot.Controller.IngredientManagementController;
import DAL.IngredientAccess;
import Entity.Ingredient;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;

public class IngredientManagement {
    private ArrayList<Ingredient> ingredients;
    private IngredientAccess ingredientDAO;

    public IngredientManagement() {
        ingredientDAO = new IngredientAccess();
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

//        Code initialize java.sql.Date

        try {
            ingredients.get(0).setDeleteDate(new Date(
                    IngredientManagementController.sdf.parse("2002-12-22").getTime()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
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
        ingredients.remove(i);
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
