package App.Statitics.Controller;
import App.Statitics.Model.RevenueModel;
import Logic.Statitics.IStatitics;
import Logic.Statitics.LStatitics;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Month;
import java.util.Arrays;
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
    private Button btnUp;

    @FXML
    private Button btnDown;
    @FXML
    private ChoiceBox<Month> cbMonth;

    @FXML
    private Button btnApply;

    @FXML
    private TextField txfYear;

    @FXML
    private ScrollPane btmScrollpane;

    @FXML
    private VBox btmBox;

    private RevenueModel model;

    private final Month[] months = {
            Month.JANUARY,Month.FEBRUARY,Month.MARCH,Month.APRIL,
            Month.MAY,Month.JUNE,Month.JULY,Month.AUGUST,
            Month.SEPTEMBER,Month.OCTOBER,Month.NOVEMBER,Month.DECEMBER};

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

    public void presentData(){
        setDayData();
        setProductData();
        setCateData();
        showRevenueTable();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.model = new RevenueModel();
        this.model.setDataGetter(new LStatitics());
        this.model.getData(new IStatitics.Time(2023,3));
        btmScrollpane.setPrefWidth(400);
        presentData();
        initGUI();
    }

    private void initGUI(){
        handleRevChartClick();
        handleProChartClick();
        handleCatChartClick();
        handleApplyBtnClick();
        handleYearBtn();
        cbMonth.setItems(FXCollections.observableList(Arrays.stream(months).toList()));
        cbMonth.setValue(Month.JANUARY);
    }

    private void handleApplyBtnClick(){
        btnApply.setOnMouseClicked(e ->{
            model.getData(new IStatitics.Time(
                    Integer.parseInt(txfYear.getText()),
                    cbMonth.getValue().getValue())
            );
            presentData();
        });
    }

    private void showRevenueTable(){
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
    }

    private void showProductTable(){
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
    }

    private void showCateTable(){
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
    }

    private void increaseYear(){
        txfYear.setText(String.valueOf(Integer.parseInt(txfYear.getText())+1));
    }

    private void decreaseYear(){
        txfYear.setText(String.valueOf(Integer.parseInt(txfYear.getText())-1));
    }

    private void handleYearBtn(){
        btnUp.setOnMouseClicked(e->{
            increaseYear();
        });
        btnDown.setOnMouseClicked(e->{
            decreaseYear();
        });
    }

    public void handleRevChartClick(){
        revenueDayChart.setOnMouseClicked(e->{
            showRevenueTable();
        });
    }
    public void handleProChartClick(){
        revenueProductChart.setOnMouseClicked(e->{
            showProductTable();
        });
    }
    public void handleCatChartClick(){
        revenueCateChart.setOnMouseClicked(e->{
            showCateTable();
        });
    }
}
