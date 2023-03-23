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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


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

    public static void getFunction(String[] functions){
        AppControl app = new AppControl();
        for(String func : functions){
            addButton(app.getPOSButton(func));
        }
    }

    private void initGUI(){
        mainView = new BorderPane();
        mainMenu = new VBox();
        group = new ToggleGroup();
        ScrollPane scrollPane = new ScrollPane(mainMenu);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setMaxHeight(Screen.getPrimary().getBounds().getHeight()-200);
        mainView.setLeft(scrollPane);
    }

    @Override
    public void start(Stage stage) throws Exception {
        String defaultpath = "src/main/java/App/View/ShopGUI.fxml";
        initGUI();
        getFunction(new String[]{
                "product","size","category","ingredient","recipe","customer","purchase","staff","statistic","sale"
        });
        Scene scene = new Scene(mainView);
        scene.getStylesheets().add(
                new File("src/main/java/App/View/css/Application.css")
                        .toURI().toURL().toExternalForm());

        MainApp.show(FXMLLoader.load(new File(defaultpath).toURI().toURL()));
        stage.setScene(scene);
        stage.show();
    }
}
