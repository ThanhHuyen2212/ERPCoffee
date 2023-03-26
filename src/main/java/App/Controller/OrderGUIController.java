package App.Controller;

import App.Model.OrderTable;
import Entity.Order;
import Logic.OrderManagement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OrderGUIController implements Initializable {
    OrderManagement orderManagement = new OrderManagement();
    ArrayList<Order> orders = new ArrayList<>();
    @FXML
    private TableView<Order> OrderTable;

    @FXML
    private Button btnSearch;

    @FXML
    private TableColumn<Order, String> customerName;

    @FXML
    private TableColumn<Order, Date> orderDate;

    @FXML
    private TableColumn<Order, Integer> orderID;

    @FXML
    private TableColumn<Order, Integer> totalPrice;

    @FXML
    private TextField txtSearchOrder;
    ObservableList<Order> orderTableObservableList;
    public void init(){
        orderTableObservableList= FXCollections.observableArrayList(orders);
        orderID.setCellValueFactory(new PropertyValueFactory<Order, Integer>(" "));
        customerName.setCellValueFactory(new PropertyValueFactory<Order, String>("customerName"));
        orderDate.setCellValueFactory(new PropertyValueFactory<Order,Date>("orderDate"));
        totalPrice.setCellValueFactory(new PropertyValueFactory<Order, Integer>("totalPrice"));;
        OrderTable.setItems(orderTableObservableList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init();
    }
}
