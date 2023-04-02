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

    public ArrayList<ProductDetails> getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(ArrayList<ProductDetails> productDetails) {
        this.productDetails = productDetails;
    }

    private ArrayList<ProductDetails> productDetails;

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
    public Product(int productId, String productName, String imagePath, String category, ArrayList<ProductDetails> productDetails) {
        this.productId = productId;
        this.productName = productName;
        this.imagePath = imagePath;
        this.category = category;
        this.productDetails=productDetails;
    }
    public Product(int productId, String productName, String imagePath, String category){
        this.productId = productId;
        this.productName = productName;
        this.imagePath = imagePath;
        this.category = category;
    }
    public Product(String productName, String imagePath, String category){
        this.productName = productName;
        this.imagePath = imagePath;
        this.category = category;
    }
    public Product(int productId,String productName, String category){
        this.productId = productId;
        this.productName = productName;
        this.category = category;
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

    public int getPrice(Size size){
        Integer price = null;
        for(ProductDetails pd : productDetails){
            if(size.getSign().equalsIgnoreCase(pd.getSize())){
                price=pd.getPrice();
            }
        }
        return price;
    }
    public int getVolume(Size size){
        Integer price = null;
        for(ProductDetails pd : productDetails){
            if(size.getSign().equalsIgnoreCase(pd.getSize())){
                price=pd.getVle();
            }
        }
        return price;
    }
    public int getVolume(String size){
        int vle = 0;
        if(productDetails!=null && size!=null){
            for(ProductDetails pd : productDetails){
                if(size.equalsIgnoreCase(pd.getSize())){
                    vle=pd.getVle();
                }
            }
        }
        return vle;
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
        int price = 0;
        if(productDetails!=null && size!=null){
            for(ProductDetails pd : productDetails){
                if(size.equalsIgnoreCase(pd.getSize())){
                    price=pd.getPrice();
                }
            }
        }
        return price;
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
        ArrayList<String> sizeList = new ArrayList<>();
        if(productDetails!=null){
            for(ProductDetails pd :productDetails){
                sizeList.add(pd.getSize());
            }
        }

        return sizeList;
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
