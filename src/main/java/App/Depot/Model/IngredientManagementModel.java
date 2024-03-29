package App.Depot.Model;

import Entity.Ingredient;
import Logic.Depot.IngredientManagement;
import Logic.Management;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class IngredientManagementModel {
    private IngredientManagement ingredientManagementLogic;
    private ObservableList<Ingredient> list;

    public IngredientManagementModel() {
        ingredientManagementLogic = Management.ingredientManagement;
        list = FXCollections.observableArrayList(ingredientManagementLogic.getIngredients());
    }

    public IngredientManagement getIngredientManagementLogic() {
        return ingredientManagementLogic;
    }

    public void setIngredientManagementLogic(IngredientManagement ingredientManagementLogic) {
        this.ingredientManagementLogic = ingredientManagementLogic;
    }

    public ObservableList<Ingredient> getList() {
        return list;
    }

    public void setList(ObservableList<Ingredient> list) {
        this.list = list;
    }
    public void handleCreate(Ingredient i) {
        ingredientManagementLogic.create(i);
        list = FXCollections.observableArrayList(ingredientManagementLogic.getIngredients());
    }

    public void handleUpdate(Ingredient i, int index, String name, String type, int price, int limit) {
        i.setIngredientName(name);
        i.setIngredientType(type);
        i.setPrice(price);
        i.setIngredientLimit(limit);
        ingredientManagementLogic.update(index, i);
    }

    public void handleDelete(Ingredient i) {
        ingredientManagementLogic.delete(i);
        list = FXCollections.observableArrayList(ingredientManagementLogic.getIngredients());
    }



}
