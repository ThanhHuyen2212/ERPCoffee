package Entity;

public class ProductDetails {
    private String size;
    private Integer Price;
    private Integer vle;

    public ProductDetails(String size, Integer price, Integer vle) {
        this.size = size;
        Price = price;
        this.vle = vle;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getPrice() {
        return Price;
    }

    public void setPrice(Integer price) {
        Price = price;
    }

    public Integer getVle() {
        return vle;
    }

    public void setVle(Integer vle) {
        this.vle = vle;
    }
}
