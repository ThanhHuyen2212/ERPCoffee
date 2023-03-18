package App.Statitics.Controller;
import App.Statitics.Model.RevenueModel;
import Logic.Statitics.LStatitics;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
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
        btmScrollpane.setPrefWidth(400);
        setData();
        handleRevChartClick();
        handleProChartClick();
        handleCatChartClick();
    }

    public void handleRevChartClick(){
        revenueDayChart.setOnMouseClicked(e->{
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(
                        new File("src/main/java/App/Statitics/View/RevenueTable.fxml").toURI().toURL()
                );
                TableView table = fxmlLoader.load();
                btmScrollpane.setContent(table);
                ((RevenueTableControl)fxmlLoader.getController()).setData(model.getRevenueData());
                table.setPrefWidth(btmScrollpane.getPrefWidth());

            } catch (MalformedURLException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
    public void handleProChartClick(){
        revenueProductChart.setOnMouseClicked(e->{
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(
                        new File("src/main/java/App/Statitics/View/ProductTable.fxml").toURI().toURL()
                );
                TableView table = fxmlLoader.load();
                btmScrollpane.setContent(table);
                ((ProductTableControl)fxmlLoader.getController()).setData(model.getRevenueByProduct());
                table.setPrefWidth(btmScrollpane.getPrefWidth());

            } catch (MalformedURLException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
    public void handleCatChartClick(){
        revenueCateChart.setOnMouseClicked(e->{
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(
                        new File("src/main/java/App/Statitics/View/CategoryTable.fxml").toURI().toURL()
                );
                TableView table = fxmlLoader.load();
                btmScrollpane.setContent(table);
                ((CategoryTableControl)fxmlLoader.getController()).setData(model.getRevenueByCategory());
                table.setPrefWidth(btmScrollpane.getPrefWidth());

            } catch (MalformedURLException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}
