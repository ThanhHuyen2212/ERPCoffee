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

public class ProductTableControl implements Initializable {
    @FXML
    TableView<IStatitics.Product> dataTable;
    @FXML
    TableColumn<IStatitics.Product,String> nameCol;
    @FXML
    TableColumn<IStatitics.Product,Number> revCol;
    @FXML
    TableColumn<IStatitics.Product,Number> countCol;
    @FXML
    TableColumn<IStatitics.Product,String> CntBySizeCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        revCol.setCellValueFactory(new PropertyValueFactory<>("totalRevenue"));
        countCol.setCellValueFactory(new PropertyValueFactory<>("productCount"));
        CntBySizeCol.setCellValueFactory(data ->{
            StringBuilder s = new StringBuilder();
            for(Pair<String,Number> p : data.getValue().getProductCountBySize()){
                s.append(String.format("Size %s: %s\n", p.getKey(), p.getValue()));
            }
            return new SimpleStringProperty(s.toString());
        });
    }

    public void setData(ArrayList<IStatitics.Product> data){
        dataTable.setItems(FXCollections.observableList(data));
    }
}
