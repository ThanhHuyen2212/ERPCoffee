package App.ModuleManager;


import App.Controller.AlertController;
import App.Model.Alert;
import Entity.Function;
import Main.MainApp;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
        put("ingredient","src/main/java/App/Depot/View/IngredientManagementView.fxml");
        put("recipe","src/main/java/App/Depot/View/RecipeManagementView.fxml");
        put("purchase","src/main/java/App/Depot/View/POManagementView.fxml");
        put("staff","");
        put("statistic","src/main/java/App/Statitics/View/RevenueChart.fxml");
    }};
    HashMap<String,String> iconPath = new HashMap<>(){{
        put("sale","src/main/java/Assets/Icons/cashier.png");
        put("product","src/main/java/Assets/Icons/latte.png");
        put("size","src/main/java/Assets/Icons/ruler.png");
        put("system","src/main/java/Assets/Icons/data-complexity.png");
        put("category","src/main/java/Assets/Icons/category.png");
        put("customer","src/main/java/Assets/Icons/rating.png");
        put("ingredient","src/main/java/Assets/Icons/olive-oil.png");
        put("recipe","src/main/java/Assets/Icons/recipe.png");
        put("purchase","src/main/java/Assets/Icons/checklist.png");
        put("staff","src/main/java/Assets/Icons/latte.png");
        put("statistic","src/main/java/Assets/Icons/analytics.png");
    }};

    private HashMap<String,Node> views;

    public AppControl() {
    }

    public AppControl(ArrayList<String> functions) {
        views = new HashMap<>();
        for(String func : functions){
            try {
                views.put(func,FXMLLoader.load(
                        new File(modulePath.get(func)).toURI().toURL()));
            } catch (IOException e) {
                views.put(func,null);
            }
        }
    }

    public ToggleButton getPOSButton(String functionName){
        ToggleButton btn = new ToggleButton(functionName.toUpperCase());
        btn.getStyleClass().add("button-admin");
        btn.setContentDisplay(ContentDisplay.TOP);
        if(!iconPath.get(functionName).equals("")){
            try {
                ImageView img = new ImageView(new Image(new FileInputStream(iconPath.get(functionName))));
                img.setFitHeight(50);
                img.setFitWidth(50);
                btn.setGraphic(img);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        btn.setOnMouseClicked(e->{
            if(views.get(functionName) != null) MainApp.show(views.get(functionName));
            else showAlert("error","This function not ready yet !!!");
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
                alert1.RenderAlert("error",message);
            } catch (MalformedURLException exc) {
                throw new RuntimeException(exc);
            }
            btnType.showAndWait();
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }
}
