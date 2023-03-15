package App.Contoller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class AdminProductController {
    @FXML
    private TableColumn<?, ?> CategoryNameTableColumn;

    @FXML
    private Label header;

    @FXML
    private TableColumn<?, ?> productIDTableColumn;

    @FXML
    private TableColumn<?, ?> productNameTableColumn;

    @FXML
    private TableColumn<?, ?> productPriceByMTableColumn;

    @FXML
    private TableColumn<?, ?> productPriceBySTableColumn;

    @FXML
    private TableView<?> productTableView;

    @FXML
    private TextField txtSearchName;
    public void changeSceneAddEvent(ActionEvent e) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new File("src/main/java/App/View/AdminProductAdd.fxml").toURI().toURL());
        Pane ProductAddViewParent = loader.load();
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initStyle(StageStyle.TRANSPARENT);
        dialog.setDialogPane((DialogPane) ProductAddViewParent);
        Optional<ButtonType> ClickedButton = dialog.showAndWait();
        AdminProductAddController AddController = loader.getController();
        if(ClickedButton.get()==ButtonType.APPLY){
            productTableView.refresh();
        }else if(ClickedButton.get()==ButtonType.CLOSE){
            dialog.close();
        }
    }

}
