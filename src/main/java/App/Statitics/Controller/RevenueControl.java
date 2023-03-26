package App.Statitics.Controller;
import App.Statitics.Model.RevenueModel;
import Logic.Depot.IngredientManagement;
import Logic.Depot.PurchaseOrderManagement;
import Logic.Statitics.IStatitics;
import Logic.Statitics.LStatitics;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Month;
import java.util.Arrays;
import java.util.ResourceBundle;

public class RevenueControl implements Initializable{

    @FXML
    private BorderPane statisticView;
    @FXML
    private ScrollPane chartScrollpane;
    @FXML
    private Button btnShowTable;
    @FXML
    private Button btnShowChart;
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
    private BarChart<String, Number> ingChart;

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
    private ScrollPane tableScrollpane;

    @FXML
    private VBox tableBox;

    private RevenueModel model;
    private TranslateTransition translate;
    private TranslateTransition translate2;

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

    public void setIngData(){
        this.ingChart.getData().clear();
        this.ingChart.getData().add(model.getIngData());
        this.ingChart.getData().add(model.getPurchaseData());
    }

    public void presentData(){
        setDayData();
        setProductData();
        setCateData();
        setIngData();
        tableBox.getChildren().clear();
        showRevenueTable();
        showProductTable();
        showIngredientTable();
        showCateTable();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.model = new RevenueModel();
        this.model.setDataGetter(new LStatitics());
        this.model.setIngDataGetter(new IngredientManagement());
        this.model.setPurChaseDataGetter(new PurchaseOrderManagement());
        this.model.getData(new IStatitics.Time(2023,3));
        initGUI();
        presentData();
    }

    private void initGUI(){
        tableScrollpane = new ScrollPane();
        tableScrollpane.setPrefWidth(chartScrollpane.getPrefWidth());
        tableScrollpane.setFitToWidth(true);
        tableBox = new VBox();
        tableBox.setFillWidth(true);
        tableBox.setSpacing(10);
        tableScrollpane.setContent(tableBox);
        handleApplyBtnClick();
        handleYearBtn();
        cbMonth.setItems(FXCollections.observableList(Arrays.stream(months).toList()));
        cbMonth.setValue(Month.JANUARY);
        btnShowChart.setOnMouseClicked(e->{
            statisticView.setCenter(chartScrollpane);
            translate2.play();
        });
        btnShowTable.setOnMouseClicked(e->{
            statisticView.setCenter(tableScrollpane);
            translate.play();
        });
        createTransition();


    }

    private void createTransition(){
        translate = new TranslateTransition(Duration.millis(300),tableScrollpane);
        translate.setFromX(500f);
        translate.setToX(0);
        translate.play();
        translate2 = new TranslateTransition(Duration.millis(300),chartScrollpane);
        translate2.setFromX(-500f);
        translate2.setToX(0);
        translate2.play();
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
            Label textLabel = new Label("Revenue Data");
            textLabel.getStyleClass().add("hau-table-label");
            VBox tmp = new VBox(textLabel,table);
            tmp.setFillWidth(true);
            tableBox.getChildren().add(tmp);
            ((RevenueTableControl)fxmlLoader.getController()).setData(model.getRevenueData());
            table.setPrefWidth(tableScrollpane.getPrefWidth());

        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void showIngredientTable(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    new File("src/main/java/App/Statitics/View/IngredientTable.fxml").toURI().toURL()
            );
            TableView table = fxmlLoader.load();
            Label textLabel = new Label("Ingredient Data");
            textLabel.getStyleClass().add("hau-table-label");
            VBox tmp = new VBox(textLabel,table);
            tmp.setFillWidth(true);
            tableBox.getChildren().add(tmp);
            ((IngredientTableControl)fxmlLoader.getController()).setData(model.getIngredients(),model.getPurchaseData());
            table.setPrefWidth(tableScrollpane.getPrefWidth());

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
            Label textLabel = new Label("Product Sold In Month");
            textLabel.getStyleClass().add("hau-table-label");
            VBox tmp = new VBox(textLabel,table);
            tmp.setFillWidth(true);
            tableBox.getChildren().add(tmp);
            ((ProductTableControl)fxmlLoader.getController()).setData(model.getRevenueByProduct());
            table.setPrefWidth(tableScrollpane.getPrefWidth());

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
            Label textLabel = new Label("Product Data by Category");
            textLabel.getStyleClass().add("hau-table-label");
            VBox tmp = new VBox(textLabel,table);
            tmp.setFillWidth(true);
            tableBox.getChildren().add(tmp);
            ((CategoryTableControl)fxmlLoader.getController()).setData(model.getRevenueByCategory());
            table.setPrefWidth(tableScrollpane.getPrefWidth());

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

}
