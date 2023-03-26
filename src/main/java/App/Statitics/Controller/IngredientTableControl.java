package App.Statitics.Controller;

import Entity.Ingredient;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class IngredientTableControl implements Initializable {
    public TableColumn<Ingredient,String> nameCol;
    public TableColumn<Ingredient,String> stockCol;
    public TableColumn<Ingredient,String> orderCol;
    public TableView<Ingredient> dataTable;
    private ArrayList<Ingredient> ingData;
    private XYChart.Series<String,Number> purData;

    public void setData(ArrayList<Ingredient> ing, XYChart.Series<String,Number> pur){
        this.ingData = ing;
        this.purData = pur;
        dataTable.setItems(FXCollections.observableList(ingData));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("ingredientName"));
        stockCol.setCellValueFactory(new PropertyValueFactory<>("ingredientStorage"));
        orderCol.setCellValueFactory(data -> {
            for(XYChart.Data<String,Number> item : purData.getData()){
                if(data.getValue().getIngredientName().equals(item.getXValue())){
                    return new SimpleStringProperty(item.getYValue().toString());
                }
            }
            return new SimpleStringProperty("0");
        });
    }
}
