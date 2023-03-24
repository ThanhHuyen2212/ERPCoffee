package Logic;

import DAL.CategoryAccess;
import DAL.ProductAccess;
import Entity.Category;
import Entity.Product;

import java.util.ArrayList;

public class CategoryManagement {
    private ArrayList<Category> categories;
    public CategoryManagement(){
        init();
    }

    private void init(){
        categories = CategoryAccess.retrieve();
    }
    public ArrayList<Category> getCategoriesList(){
        return categories;
    }
}
