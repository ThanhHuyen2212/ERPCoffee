package Entity;

public class OrderDetail {
    private Product product;
    private Size size;
    private int qty;

    public OrderDetail() {
    }

    public OrderDetail(Product product, Size size, int qty) {
        this.product = product;
        this.size = size;
        this.qty = qty;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }


    public boolean equals(OrderDetail od) {
        return this.product.getProductId() == od.getProduct().getProductId()
                && this.size.getSign().equals(od.getSize().getSign());
    }
}
