package Main;

import App.Statitics.Controller.RevenueControl;
import App.Statitics.Model.RevenueModel;
import Logic.Statitics.LStatitics;
import Util.DateTool;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;


public class MainApp extends Application {

    private static BorderPane mainView;
    private ToggleGroup mainMenu;

    public static void show(Node view){
        mainView.setCenter(view);
    }

    @Override
    public void start(Stage stage) throws Exception {
        String defaultpath = "src/main/java/App/View/ShopGUI.fxml";
        mainView = new BorderPane();
        Scene scene = new Scene(mainView);
        MainApp.show(FXMLLoader.load(new File(defaultpath).toURI().toURL()));
        stage.setScene(scene);
        stage.show();
    }
}
