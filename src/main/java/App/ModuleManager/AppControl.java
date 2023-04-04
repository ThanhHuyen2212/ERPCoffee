package App.ModuleManager;


import App.Controller.AlertController;
import Entity.Employee;
import Logic.Depot.ProductPreparationManagement;
import Main.MainApp;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import static Main.MainApp.addButton;
import static Main.MainApp.getFunction;

public class AppControl {
    public static Employee currentUser;
    public static Connection conn;
    public static HashMap<Integer, Integer> preparedList;


    HashMap<String,String> modulePath = new HashMap<>(){{
        put("sale","src/main/java/App/View/ShopGUI.fxml");
        put("product","src/main/java/App/View/ProductGUI.fxml");
        put("size","src/main/java/App/Size/View/Size.fxml");
        put("account","src/main/java/App/View/AccountCRUD.fxml");
        put("system","src/main/java/App/Permission/View/Permission.fxml");
        put("category","src/main/java/App/View/CategoryCRUD.fxml");
        put("customer","");
        put("ingredient","src/main/java/App/Depot/View/IngredientManagementView.fxml");
        put("recipe","src/main/java/App/Depot/View/RecipeManagementView.fxml");
        put("manufacturing","src/main/java/App/Depot/View/ProductPreparationView.fxml");
        put("purchase","src/main/java/App/Depot/View/POManagementView.fxml");
        put("staff","src/main/java/App/View/EmployeeCRUD.fxml");
        put("statistic","src/main/java/App/Statitics/View/RevenueChart.fxml");
        put("order","src/main/java/App/View/OrderGUI.fxml");
    }};
    HashMap<String,String> iconPath = new HashMap<>(){{
        put("sale","src/main/java/Assets/Icons/cashier.png");
        put("product","src/main/java/Assets/Icons/latte.png");
        put("size","src/main/java/Assets/Icons/ruler.png");
        put("account","src/main/java/Assets/Icons/data-complexity.png");
        put("system","src/main/java/Assets/Icons/data-complexity.png");
        put("category","src/main/java/Assets/Icons/category.png");
        put("customer","src/main/java/Assets/Icons/rating.png");
        put("ingredient","src/main/java/Assets/Icons/olive-oil.png");
        put("recipe","src/main/java/Assets/Icons/recipe.png");
        put("manufacturing","src/main/java/Assets/Icons/recipe.png");
        put("purchase","src/main/java/Assets/Icons/checklist.png");
        put("staff","src/main/java/Assets/Icons/latte.png");
        put("statistic","src/main/java/Assets/Icons/analytics.png");
        put("order","src/main/java/Assets/Icons/orders.png");
        put("logout","src/main/java/Assets/Icons/log-out1.png");
    }};


    private HashMap<String, Node> views;
    private ArrayList<String> permissons;
    private HashMap<String,ToggleButton> buttons;
    private ToggleButton logoutButton;


    public AppControl() {
        views = new HashMap<>();
        buttons = new HashMap<>();
        preparedList = ProductPreparationManagement.preparedList;
    }

    public AppControl(ArrayList<String> permissons) {
        this.permissons = permissons;
    }

    public void setPermissons(ArrayList<String> permissons){
        this.permissons = permissons;
        for(String func : this.permissons){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        views.put(func,FXMLLoader.load(
                                new File(modulePath.get(func)).toURI().toURL()));
                    } catch (IOException e) {
                        views.put(func,null);
                    }
                    getPOSButton(func).setDisable(false);

                }
            }).start();
        }
        getFunction();
    }

    public ToggleButton getPOSButton(String functionName){
        if(buttons.get(functionName)==null){
            ToggleButton btn = new ToggleButton(functionName.toUpperCase());
            btn.getStyleClass().add("hau-menu-button");
            btn.getStyleClass().add("hau-toggle-button");
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
            btn.setDisable(true);
            buttons.put(functionName,btn);
        }

        return buttons.get(functionName);
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
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }

    public ArrayList<String> getPermission() {
        return this.permissons;
    }

    public HashMap<String, Node> getViews() {
        return views;
    }
    public Parent getLogoutView(){
        try {
            return FXMLLoader.load(
                    new File("src/main/java/App/LogIn/View/LogIn.fxml").toURI().toURL());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public ToggleButton getLogoutButton(){
        if(logoutButton == null){
            logoutButton = new ToggleButton("LogOut");
            logoutButton.getStyleClass().add("hau-menu-button");
            logoutButton.getStyleClass().add("hau-toggle-button");
            logoutButton.setContentDisplay(ContentDisplay.TOP);
            if(!iconPath.get("logout").equals("")){
                try {
                    ImageView img = new ImageView(new Image(new FileInputStream(iconPath.get("logout"))));
                    img.setFitHeight(50);
                    img.setFitWidth(50);
                    logoutButton.setGraphic(img);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            logoutButton.setOnMouseClicked( e->{
                currentUser = null;
                this.permissons.clear();
                MainApp.getFunction();
                MainApp.show(getLogoutView());
            });
        }
        return logoutButton;
    }
}
