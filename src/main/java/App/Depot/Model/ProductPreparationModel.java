package App.Depot.Model;

import Entity.Product;
import Logic.Depot.ProductPreparationManagement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;

public class ProductPreparationModel {
    private ObservableList<Product> products;
    private ProductPreparationManagement logic;

    public ProductPreparationModel() {
    }

    public ObservableList<Product> getProducts() {
        return products;
    }

    public void setProducts(ObservableList<Product> products) {
        this.products = products;
    }

    public ProductPreparationManagement getLogic() {
        return logic;
    }

    public void setLogic(ProductPreparationManagement logic) {
        this.logic = logic;
    }

    public void init() {
        logic = new ProductPreparationManagement();
        products = FXCollections.observableList(logic.getProducts());

    }

    public void handlePrepare() {
        logic.handlePrepare();
        logic.setPreparations(new HashMap<>());
    }

    public void handleFillQty() {
        logic.getProducts().forEach(product -> {
            logic.getPreparations().put(product, 10);
        });
    }
}
