package Logic.Depot;

import DAL.RecipeAccess;
import Entity.Product;

public class RecipeManagement {
    private RecipeAccess recipeDAO;

    public RecipeManagement() {
        recipeDAO = new RecipeAccess();
    }

    public void create(Product selected) {
        recipeDAO.create(selected);
    }
}
