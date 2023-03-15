package Main;

import Util.DateTool;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.sql.Date;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(
                FXMLLoader.load(new File("src/main/java/App/Statitics/View/RevenueChart.fxml").toURI().toURL())
        ));
        stage.show();
        for (Date day : DateTool.getDayOfMonth(2020,2)){
            System.out.println(day.toString());
        }
    }
}
