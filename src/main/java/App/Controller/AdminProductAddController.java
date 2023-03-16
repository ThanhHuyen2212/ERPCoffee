package App.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javax.swing.text.html.ImageView;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminProductAddController implements Initializable {
    @FXML
    private ImageView ImgProduct;

    @FXML
    private Button btnUploadfile;

    @FXML
    private ComboBox<?> cbSize;

    @FXML
    private DialogPane dialogCategoryAdd;

    @FXML
    private ImageView imgProductSize;

    @FXML
    private Label labelCateogory;

    @FXML
    private Label labelProduct;

    @FXML
    private Tab tabSize;

    @FXML
    private Tab tabSizeProduct;

    @FXML
    private TextField txtCategory;

    @FXML
    private TextField txtProductName;

    @FXML
    private TextField txtprice;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
