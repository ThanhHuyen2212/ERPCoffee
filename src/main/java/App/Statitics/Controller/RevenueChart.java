package App.Statitics.Controller;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class RevenueChart implements Initializable {

    @FXML
    private CategoryAxis category;

    @FXML
    private NumberAxis number;

    @FXML
    private BarChart<String, Integer> revenueChart;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("2003");
        series1.getData().add(new XYChart.Data("1994-07-09", 25601.34));
        series1.getData().add(new XYChart.Data("1994-07-10", 20148.82));
        series1.getData().add(new XYChart.Data("1994-07-11", 10000));
        series1.getData().add(new XYChart.Data("1994-07-12", 35407.15));
        series1.getData().add(new XYChart.Data("1994-07-13", 12000));
        revenueChart.getData().addAll(series1);
    }
}
