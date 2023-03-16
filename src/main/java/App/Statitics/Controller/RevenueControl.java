package App.Statitics.Controller;
import App.Statitics.Model.RevenueModel;
import Entity.Order;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class RevenueControl{

    @FXML
    private CategoryAxis category;

    @FXML
    private NumberAxis number;

    @FXML
    private BarChart<String, Integer> revenueChart;

    @FXML
    private ScrollPane btmScrollpane;

    @FXML
    private VBox btmBox;

    private RevenueModel model;

    public RevenueModel getModel() {
        return model;
    }

    public void setModel(RevenueModel model) {
        this.model = model;
    }

    private XYChart.Series dataSeries;


    public void renderChart(){
        setDataSeries(model.getData());
        renderTableData();
        revenueChart.getData().add(dataSeries);
    }

    public void renderTableData(){
        btmBox.getChildren().clear();
        for(Object xy : dataSeries.getData()){
            btmBox.getChildren().add(
                    new HBox(new Label(((XYChart.Data<String,Integer>) xy).getXValue()+" : "),
                            new Label(String.valueOf(((XYChart.Data<String,Integer>) xy).getYValue()))
                    ));
        }
    }


    public XYChart.Series getDataSeries() {
        return dataSeries;
    }

    public void setDataSeries(XYChart.Series dataSeries) {
        this.dataSeries = dataSeries;
    }
}
