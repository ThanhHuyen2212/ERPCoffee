package App.Controller;

import App.Model.OrderGUI;
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
    private TableView<OrderGUI> OrderTable;

    @FXML
    private Button btnSearch;

    @FXML
    private TableColumn<OrderGUI, String> customerName;

    @FXML
    private TableColumn<OrderGUI, Date> orderDate;

    @FXML
    private TableColumn<OrderGUI, Integer> orderID;

    @FXML
    private TableColumn<OrderGUI, Integer> totalPrice;

    @FXML
    private TextField txtSearchOrder;
    ObservableList<OrderGUI> orderTableObservableList;


    public ArrayList<OrderGUI> changeToOrderGUI(ArrayList<Order> orders){
        ArrayList<OrderGUI> orderGUIS = new ArrayList<>();
        for(Order o :orders){
             orderGUIS.add(new OrderGUI(o));
         }
        return orderGUIS;
    }
    public void init(){
        orders=orderManagement.getOrderList();;
        orderTableObservableList= FXCollections.observableArrayList(changeToOrderGUI(orders));
        orderID.setCellValueFactory(new PropertyValueFactory<OrderGUI, Integer>("orderID"));
        customerName.setCellValueFactory(new PropertyValueFactory<OrderGUI, String>("customerName"));
        orderDate.setCellValueFactory(new PropertyValueFactory<OrderGUI,Date>("date"));
        totalPrice.setCellValueFactory(new PropertyValueFactory<OrderGUI, Integer>("totalPrice"));;
        OrderTable.setItems(orderTableObservableList);
    }
    public void reload(Order order){
        orders.add(order);
        orderTableObservableList= FXCollections.observableArrayList(changeToOrderGUI(orders));

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init();
    }
}
