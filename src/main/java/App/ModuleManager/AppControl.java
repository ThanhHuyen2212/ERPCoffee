package App.ModuleManager;


import App.Controller.AlertController;
import Entity.Function;
import Main.MainApp;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Optional;

public class AppControl {
    HashMap<String,String> modulePath = new HashMap<>(){{
        put("sale","src/main/java/App/View/ShopGUI.fxml");
        put("product","");
        put("size","");
        put("system","");
        put("category","");
        put("customer","");
        put("ingredient","");
        put("recipe","");
        put("purchase","");
        put("staff","");
        put("statistic","src/main/java/App/Statitics/View/RevenueChart.fxml");
    }};

    public ToggleButton getPOSButton(String functionName){
        ToggleButton btn = new ToggleButton(functionName.toUpperCase());
        btn.setOnMouseClicked(e->{
            try {
                MainApp.show(FXMLLoader.load(new File(modulePath.get(functionName)).toURI().toURL()));
            } catch (IOException ex) {
                showAlert("error","This function not ready yet !!!");
            }
        });
        return btn;
    }

    public static void showAlert(String type,String message){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    new File("src/main/java/App/View/Alert.fxml").toURI().toURL()
            );
            Pane alert = fxmlLoader.load();
            Dialog<ButtonType> btnType = new Dialog<>();
            btnType.setDialogPane((DialogPane) alert);
            AlertController alert1 = fxmlLoader.getController();
            try {
                alert1.RenderAlert(type,message);
            } catch (MalformedURLException exc) {
                throw new RuntimeException(exc);
            }
            btnType.showAndWait();
        } catch (MalformedURLException exc) {
            throw new RuntimeException(exc);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
