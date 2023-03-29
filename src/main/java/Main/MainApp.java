package Main;

import App.ModuleManager.AppControl;
import Entity.Employee;
import Logic.Management;
import Logic.Depot.IngredientManagement;
import Logic.Depot.PurchaseOrderManagement;
import Logic.ProductManagement;
import Util.FileTool;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;


public class MainApp extends Application {

    private static BorderPane mainView;
    private static VBox mainMenu;
    public static Employee currentUser;

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
        mainMenu.setPrefWidth(110);
        mainMenu.setSpacing(5);
        mainMenu.setAlignment(Pos.CENTER);
        ScrollPane scrollPane = new ScrollPane(mainMenu);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setFitToHeight(true);
        mainView.setLeft(scrollPane);
    }

    private void initData() {
        Management.ingredientManagement = new IngredientManagement();
        Management.purchaseOrderManagement = new PurchaseOrderManagement();
        Management.productManagement = new ProductManagement();
    }

    @Override
    public void start(Stage stage) throws Exception {
//        String defaultpath = "src/main/java/App/LogIn/View/LogIn.fxml";
        String defaultpath = "src/main/java/App/View/CategoryCRUD.fxml";
        System.out.println("Tại sao code ko chạy");
        initGUI();
        initData();
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
       // new FileTool().createPdf("src/main/resources/test.pdf",mainView,FXMLLoader.load(new File(defaultpath).toURI().toURL()));
    }
}
