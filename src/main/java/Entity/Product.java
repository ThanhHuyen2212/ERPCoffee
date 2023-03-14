package Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Product {

    private int productId;
    private String productName;
    private String imagePath;
    private Category category;
    private HashMap<Size,Integer> priceList;

    private ProductRecipe recipe;

    public Product() {
    }

    public Product(int productId, String productName, String imagePath, Category category, HashMap<Size, Integer> priceList) {
        this.productId = productId;
        this.productName = productName;
        this.imagePath = imagePath;
        this.category = category;
        this.priceList = priceList;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public HashMap<Size, Integer> getPriceList() {
        return priceList;
    }

    public void setPriceList(HashMap<Size, Integer> priceList) {
        this.priceList = priceList;
    }

    public int getPrice(Size size){
        return priceList.get(size);
    }

    public int getPrice(String size){
        for(Size s : priceList.keySet()){
            if(s.getSign().equals(size)){
                return priceList.get(s);
            }
        }
        return 0;
    }

    public ArrayList<String> getSizeList(){
        return new ArrayList<>(
                priceList.keySet()
                        .stream().map(
                            (Size::getSign)
                        )
                        .toList());
    }

    public ProductRecipe getRecipe() {
        return recipe;
    }

    public void setRecipe(ProductRecipe recipe) {
        this.recipe = recipe;
    }
}
