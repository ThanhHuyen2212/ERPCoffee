package Entity;

import javafx.collections.ObservableList;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

public class Product {

    private int productId;
    private String productName;
    private String imagePath;
    private String category;
    private Date createAt;
    private Date deleteAt;

    private HashMap<String,Integer> priceList;

    private ProductRecipe recipe;

    public Product() {
    }
    public Product(int productId, String productName, Date createAt, Date deleteAt, String imagePath, String category){
        this.productId=productId;
        this.productName=productName;
        this.createAt= createAt;
        this.deleteAt=deleteAt;
        this.imagePath=imagePath;
        this.category=category;

    }
    public Product(int productId, String productName, String imagePath, String category, HashMap<String, Integer> priceList) {
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public HashMap<String, Integer> getPriceList() {
        return priceList;
    }

    public void setPriceList(HashMap<String, Integer> priceList) {
        this.priceList = priceList;
    }

    public int getPrice(Size size){
        return priceList.get(size);
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getDeleteAt() {
        return deleteAt;
    }

    public void setDeleteAt(Date deleteAt) {
        this.deleteAt = deleteAt;
    }

    public int getPrice(String size){
        for(String s : priceList.keySet()){
            if(s.equals(size)){
                return priceList.get(s);
            }
        }
        return 0;
    }

//    public ArrayList<String> getSizeList(){
//        return new ArrayList<>(
//                priceList.keySet()
//                        .stream().map(
//                            (Size::getSign)
//                        )
//                        .toList());
//    }
    public ArrayList<String> getSizeList(){
        return  new ArrayList<>(priceList.keySet().stream().toList());
    }

    public ProductRecipe getRecipe() {
        if(recipe == null) {
            recipe = new ProductRecipe();
        }
        return recipe;
    }

    public void setRecipe(ProductRecipe recipe) {
        this.recipe = recipe;
    }
}
