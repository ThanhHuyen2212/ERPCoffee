package Entity;

public class Ingredient {
    private int ingredientId;
    private String ingredientName;
    private String ingredientType;
    private int ingredientStorage;
    private int ingredientLimit;

    private int price;

    public Ingredient() {
    }

    public Ingredient(int ingredientId, String ingredientName, String ingredientType, int ingredientStorage, int ingredientLimit, int price) {
        this.ingredientId = ingredientId;
        this.ingredientName = ingredientName;
        this.ingredientType = ingredientType;
        this.ingredientStorage = ingredientStorage;
        this.ingredientLimit = ingredientLimit;
        this.price = price;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String getIngredientType() {
        return ingredientType;
    }

    public void setIngredientType(String ingredientType) {
        this.ingredientType = ingredientType;
    }

    public int getIngredientStorage() {
        return ingredientStorage;
    }

    public void setIngredientStorage(int ingredientStorage) {
        this.ingredientStorage = ingredientStorage;
    }

    public int getIngredientLimit() {
        return ingredientLimit;
    }

    public void setIngredientLimit(int ingredientLimit) {
        this.ingredientLimit = ingredientLimit;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
