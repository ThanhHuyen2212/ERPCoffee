package Entity;

import java.sql.Date;

public class Ingredient {
    private int ingredientId;
    private String ingredientName;
    private String ingredientType;
    private int ingredientStorage;
    private int ingredientLimit;
    private int price;
    private Date createDate;
    private Date deleteDate;

    public Ingredient() {
    }

    public Ingredient(int ingredientId, String ingredientName, String ingredientType, int price, int ingredientStorage, int ingredientLimit, Date createDate, Date deleteDate) {
        this.ingredientId = ingredientId;
        this.ingredientName = ingredientName;
        this.ingredientType = ingredientType;
        this.price = price;
        this.ingredientStorage = ingredientStorage;
        this.ingredientLimit = ingredientLimit;
        this.createDate = createDate;
        this.deleteDate = deleteDate;
    }

    public Ingredient(String ingredientName, String ingredientType, int price, int ingredientLimit) {
        this.ingredientName = ingredientName;
        this.ingredientType = ingredientType;
        this.price = price;
        this.ingredientLimit = ingredientLimit;
    }

    public Ingredient(int ingredientId, String ingredientName, String ingredientType, int price, int ingredientStorage, int ingredientLimit) {
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    public boolean isDeleted() {
        return this.getDeleteDate() != null;
    }
}
