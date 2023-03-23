package Main;

import App.ModuleManager.AppControl;
import App.Statitics.Controller.RevenueControl;
import App.Statitics.Model.RevenueModel;
import Logic.Statitics.LStatitics;
import Util.DateTool;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;


public class MainApp extends Application {

    private static BorderPane mainView;
    private static VBox mainMenu;

    private static ToggleGroup group;

    public static void show(Node view){
        mainView.setCenter(view);
    }

    public static void addButton(ToggleButton funcBtn){
        mainMenu.getChildren().add(funcBtn);
        funcBtn.setToggleGroup(group);
    }

    @Override
    public void start(Stage stage) throws Exception {

//        String defaultpath = "src/main/java/App/View/ShopGUI.fxml";
        String defaultpath = "src/main/java/App/View/EmployeeCRUD.fxml";
        mainView = new BorderPane();
        mainMenu = new VBox();
        group = new ToggleGroup();
        mainView.setLeft(mainMenu);
//        addButton(new AppControl().getPOSButton("product"));
//        addButton(new AppControl().getPOSButton("sale"));
//        addButton(new AppControl().getPOSButton("statistic"));
        Scene scene = new Scene(mainView);
        MainApp.show(FXMLLoader.load(new File(defaultpath).toURI().toURL()));
        stage.setScene(scene);
        stage.show();
    }
}
