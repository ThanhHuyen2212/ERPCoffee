package Main;

import App.ModuleManager.AppControl;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class MainApp extends Application {

    private static BorderPane mainView;
    private static VBox mainMenu;

    private static ToggleGroup group;

    public static AppControl APP;

    public static void show(Node view){
        mainView.setCenter(view);
    }

    public static void addButton(ToggleButton funcBtn){
        mainMenu.getChildren().add(funcBtn);
        TranslateTransition transition = new TranslateTransition(Duration.millis(300),funcBtn);
        transition.setFromX(200);
        transition.setToX(0);
        transition.play();
        funcBtn.setToggleGroup(group);
    }

    public static void getFunction(){
        mainMenu.getChildren().clear();
        for(String func : APP.getPermission()){
            addButton(APP.getPOSButton(func));
        }
    }

    private void initGUI(){
        mainView = new BorderPane();
        mainMenu = new VBox();
        group = new ToggleGroup();
        ScrollPane scrollPane = new ScrollPane(mainMenu);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToHeight(true);
        mainView.setLeft(scrollPane);
    }

    @Override
    public void start(Stage stage) throws Exception {
//        String defaultpath = "src/main/java/App/LogIn/View/LogIn.fxml";
        String defaultpath = "src/main/java/App/View/EmployeeCRUD.fxml";
        initGUI();
        APP = new AppControl();
        stage.getIcons().add(new Image(new FileInputStream("src/main/java/Assets/Icons/coffee-shop.png")));
        stage.setMaximized(true);
        stage.setTitle("ERP Coffee");
        Scene scene = new Scene(mainView);
        scene.getStylesheets().add(
                new File("src/main/java/App/View/css/Application.css")
                        .toURI().toURL().toExternalForm());
        scene.getStylesheets().add(
                new File("src/main/java/App/View/css/Hau_Style.css")
                        .toURI().toURL().toExternalForm());

        MainApp.show(FXMLLoader.load(new File(defaultpath).toURI().toURL()));
        stage.setScene(scene);
        stage.show();
    }
}
