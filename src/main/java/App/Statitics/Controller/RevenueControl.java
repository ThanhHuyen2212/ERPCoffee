package App.Statitics.Controller;
import App.Statitics.Model.RevenueModel;
import Logic.Statitics.LStatitics;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class RevenueControl implements Initializable{


    @FXML
    private NumberAxis revDAxis;

    @FXML
    private CategoryAxis revDCate;

    @FXML
    private NumberAxis revPAxis;

    @FXML
    private CategoryAxis revPCate;

    @FXML
    private PieChart revenueCateChart;

    @FXML
    private BarChart<String, Number> revenueDayChart;

    @FXML
    private BarChart<String, Number> revenueProductChart;

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

    public void setDayData(){
        this.revenueDayChart.getData().clear();
        this.revenueDayChart.getData().add(model.getRevenueChartData());
    }

    public void setProductData(){
        this.revenueProductChart.getData().clear();
        this.revenueProductChart.getData().add(model.getRevenueChartByProduct());

    }

    public void setCateData(){
        this.revenueCateChart.setData(model.getRevenueChartByCategory());
    }

    public void setData(){
        setDayData();
        setProductData();
        setCateData();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.model = new RevenueModel();
        this.model.setDataGetter(new LStatitics());
        setData();
    }
}
