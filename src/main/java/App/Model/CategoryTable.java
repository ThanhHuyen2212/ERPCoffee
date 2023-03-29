package App.Model;

import App.Controller.CategoryCRUD;
import DAL.CategoryAccess;
import Entity.Category;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class CategoryTable extends Category  implements Initializable {
    public CategoryTable() {
    }

    public CategoryTable(int categoryId, String categoryName) {
        super(categoryId, categoryName);
    }

    public ArrayList<Category> getDataCategory(){
        CategoryAccess categoryAccess = new CategoryAccess();
        return categoryAccess.retrieve();
    }
    public void addCategory(){
        CategoryAccess categoryAccess = new CategoryAccess();
        categoryAccess.InsertCategory(this);
    }
    public void editCategory(){
        CategoryAccess categoryAccess = new CategoryAccess();
        categoryAccess.UpdateCategory(this);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
