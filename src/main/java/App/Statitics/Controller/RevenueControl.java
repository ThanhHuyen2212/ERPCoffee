package App.Statitics.Controller;
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
import java.util.ResourceBundle;

public class RevenueControl implements Initializable {

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




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("2003");
        series1.getData().add(new XYChart.Data("1994-07-09", 25601.34));
        series1.getData().add(new XYChart.Data("1994-07-10", 20148.82));
        series1.getData().add(new XYChart.Data("1994-07-11", 10000));
        series1.getData().add(new XYChart.Data("1994-07-12", 35407.15));
        series1.getData().add(new XYChart.Data("1994-07-13", 12000));
        series1.getData().add(new XYChart.Data("1994-07-14", 25601.34));
        series1.getData().add(new XYChart.Data("1994-07-15", 20148.82));
        series1.getData().add(new XYChart.Data("1994-07-16", 10000));
        series1.getData().add(new XYChart.Data("1994-07-17", 35407.15));
        series1.getData().add(new XYChart.Data("1994-07-18", 12000));
        series1.getData().add(new XYChart.Data("1994-07-19", 25601.34));
        series1.getData().add(new XYChart.Data("1994-07-20", 20148.82));
        series1.getData().add(new XYChart.Data("1994-07-21", 10000));
        series1.getData().add(new XYChart.Data("1994-07-22", 35407.15));
        series1.getData().add(new XYChart.Data("1994-07-23", 12000));
        series1.getData().add(new XYChart.Data("1994-07-24", 25601.34));
        series1.getData().add(new XYChart.Data("1994-07-25", 20148.82));
        series1.getData().add(new XYChart.Data("1994-07-26", 10000));
        series1.getData().add(new XYChart.Data("1994-07-27", 35407.15));
        series1.getData().add(new XYChart.Data("1994-07-28", 12000));
        revenueChart.getData().addAll(series1);

        for(Object xy : series1.getData()){
            btmBox.getChildren().add(
                    new HBox(new Label(((XYChart.Data<String,Integer>) xy).getXValue()+" : "),
                            new Label(String.valueOf(((XYChart.Data<String,Integer>) xy).getYValue()))
                    ));
        }


    }

}
