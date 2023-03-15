package App.Controller;

import Entity.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.IOException;
public class itemController {
    @FXML
    private ImageView imgProduct;

    @FXML
    private Label textCategory;

    @FXML
    private Label textName;
    private MyListener myListener;
    @FXML
    private void click(MouseEvent mouseEvent) throws IOException {
        myListener.onClickListener(product);
    }
    Product product;
    public void setData(Product product, MyListener myListener) throws IOException {
        this.product = product;
        this.myListener= myListener;
        Image newImage;
        if(product != null){
            newImage = new Image(String.valueOf((new File(product.getImagePath()).toURI()).toURL()));
        }else{
            newImage = new Image(String.valueOf((new File("src/main/java/Assets/Images/WhiteCoffee.png").toURI()).toURL()));
        }
        imgProduct.setImage(newImage);
        textName.setText(product.getProductName());
        //textCategory.setText(product.getCategory().getCategoryName());
    }
}
