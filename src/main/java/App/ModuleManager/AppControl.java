package App.ModuleManager;


import Entity.Function;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
import java.util.Locale;

public class AppControl {

    
    public static final String[] MIS = {
            "PRODUCT", "CATEGORY", "SIZE", "STAFF", "STATISTIC"
    };

    public static final String[] POS = {
            "SALE"
    };

    public static final String[] DEPOT = {
            "INGREDIENT", "PURCHASE", "RECIPE"
    };
    public static final String[] SYSTEM = {"SYSTEM"};

    private ArrayList<Function> permission;

    private BorderPane view;

    public AppControl() {
        view = new BorderPane();
        permission = new ArrayList<>();
    }

    public AppControl(ArrayList<Function> permission, BorderPane view) {
        this.permission = permission;
        this.view = view;
    }

    public ArrayList<Function> getPermission() {
        return permission;
    }

    public void setPermission(ArrayList<Function> permission) {
        this.permission = permission;
    }

    public BorderPane getView() {
        return view;
    }

    public void setView(BorderPane view) {
        this.view = view;
    }

    public void renderMIS(){
        boolean isPermission = false;
        for(String func : MIS){
            for(Function function : permission) {
                if(function.getName().toUpperCase().equals(func)){
                    isPermission = true;

                }
            }
        }
    };
    public void renderPOS(){};
    private void renderDEPOT(){};
    private void renderSYSTEM(){};
}
