package Main;

import App.Statitics.Controller.RevenueControl;
import App.Statitics.Model.RevenueModel;
import Logic.Statitics.LStatitics;
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
        String filepath = "src/main/java/App/View/ShopGUI.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(new File(filepath).toURI().toURL());
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}
