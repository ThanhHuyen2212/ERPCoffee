package App.Statitics.Controller;

import Logic.Statitics.IStatitics;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Pair;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CategoryTableControl implements Initializable {
    @FXML
    TableView<IStatitics.Category> dataTable;
    @FXML
    TableColumn<IStatitics.Category,String> nameCol;
    @FXML
    TableColumn<IStatitics.Category,Number> revCol;
    @FXML
    TableColumn<IStatitics.Category,Number> countCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        revCol.setCellValueFactory(new PropertyValueFactory<>("totalRevenue"));
        countCol.setCellValueFactory(new PropertyValueFactory<>("productCount"));
    }

    public void setData(ArrayList<IStatitics.Category> data){
        dataTable.setItems(FXCollections.observableList(data));
    }
}
