package App.Controller;

import Entity.OrderDetail;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;

public class OrderController {
    @FXML
    private Label CustomerLabel;
    @FXML
    private TableView<?> OrderDetailsTable;


    @FXML
    private TableColumn<?, ?> NoTableColunm;

    @FXML
    private Label PhoneLabel;

    @FXML
    private TableColumn<?, ?> PriceColumn;

    @FXML
    private TableColumn<?, ?> ProductColunm;

    @FXML
    private TableColumn<?, ?> QuantityColmn;

    @FXML
    private Label ReceiveLabel;

    @FXML
    private TableColumn<?, ?> SizeColum;

    @FXML
    private TableColumn<?, ?> TotalColumn;

    @FXML
    private Label TotalPriceLabel;

    @FXML
    private Label cashierLabel;

    @FXML
    private Label changeLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label hhmmssLabel;
    public boolean RenderOrder(ArrayList<OrderDetail> orderDetails, String phone, String customerName){
    return  true;
    }
}
