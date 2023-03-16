package Main;

import App.Statitics.Controller.RevenueControl;
import App.Statitics.Model.RevenueModel;
import Util.DateTool;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;


public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        String filepath = "src/main/java/App/Statitics/View/RevenueChart.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(new File(filepath).toURI().toURL());
        Parent root = fxmlLoader.load();
        ((RevenueControl)fxmlLoader.getController()).setModel(new RevenueModel());
        ((RevenueControl)fxmlLoader.getController()).renderChart();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}
