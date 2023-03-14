package Entity;

public class PurchaseDetail {
    private Ingredient ingredient;
    private int orderQty;
    private int receiveQty;

    public PurchaseDetail() {
    }

    public PurchaseDetail(Ingredient ingredient, int orderQty) {
        this.ingredient = ingredient;
        this.orderQty = orderQty;
    }

    public PurchaseDetail(Ingredient ingredient, int orderQty, int receiveQty) {
        this.ingredient = ingredient;
        this.orderQty = orderQty;
        this.receiveQty = receiveQty;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public int getReceiveQty() {
        return receiveQty;
    }

    public void setReceiveQty(int receiveQty) {
        this.receiveQty = receiveQty;
    }
    public boolean equal(PurchaseDetail pd){
        return this.ingredient.getIngredientId() == pd.getIngredient().getIngredientId();
    }
}
