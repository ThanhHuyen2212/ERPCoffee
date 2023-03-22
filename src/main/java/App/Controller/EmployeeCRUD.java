package App.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javax.swing.text.TabableView;
import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeCRUD implements Initializable {
    @FXML
    private TableView empTable;
    @FXML
    private TableColumn IdCol;
    @FXML
    private TableColumn NameCol;
    @FXML
    private TableColumn PositionCol;
    @FXML
    private TextField IdTF;
    @FXML
    private TextField NameTF;
    @FXML
    private TextField PhoneTF;
    @FXML
    private TextField AddressTF;
    @FXML
    private ComboBox PositionTF;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
