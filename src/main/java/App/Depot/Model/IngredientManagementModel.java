package App.Depot.Model;

import Entity.Ingredient;
import Logic.Depot.IngredientManagement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class IngredientManagementModel {
    private IngredientManagement ingredientManagement;
    private ObservableList<Ingredient> list;

    public IngredientManagementModel() {
        ingredientManagement = new IngredientManagement();
        list = FXCollections.observableArrayList(ingredientManagement.getIngredients());
    }

    public IngredientManagement getIngredientManagement() {
        return ingredientManagement;
    }

    public void setIngredientManagement(IngredientManagement ingredientManagement) {
        this.ingredientManagement = ingredientManagement;
    }

    public ObservableList<Ingredient> getList() {
        return list;
    }

    public void setList(ObservableList<Ingredient> list) {
        this.list = list;
    }
    public void handleCreate(Ingredient i) {
        ingredientManagement.create(i);
        list = FXCollections.observableArrayList(ingredientManagement.getIngredients());
    }

    public void handleUpdate(Ingredient i, int index, String name, String type, int limit) {
        i.setIngredientName(name);
        i.setIngredientType(type);
        i.setIngredientLimit(limit);
        ingredientManagement.update(index, i);
//        list = FXCollections.observableArrayList(ingredientManagement.getIngredients());
    }

    public void handleDelete(Ingredient i) {
        ingredientManagement.delete(i);
        list = FXCollections.observableArrayList(ingredientManagement.getIngredients());
    }



}
