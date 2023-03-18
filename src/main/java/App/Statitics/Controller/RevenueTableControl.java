package App.Statitics.Controller;

import Logic.Statitics.IStatitics;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RevenueTableControl implements Initializable {
    @FXML
    TableView<IStatitics.Order> dataTable;
    @FXML
    TableColumn<IStatitics.Order, Date> dateCol;
    @FXML
    TableColumn<IStatitics.Order,Number> totalCol;
    @FXML
    TableColumn<IStatitics.Order,Number> countCol;
    @FXML
    TableColumn<IStatitics.Order,Number> avgCol;

    public void setData(ArrayList<IStatitics.Order> data){
        dataTable.setItems(FXCollections.observableList(data));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        totalCol.setCellValueFactory(new PropertyValueFactory<>("totalRevenue"));
        avgCol.setCellValueFactory(new PropertyValueFactory<>("averageRevenue"));
        countCol.setCellValueFactory(new PropertyValueFactory<>("orderCount"));
    }
}
