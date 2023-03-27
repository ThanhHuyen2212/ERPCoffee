package App.Controller;

import App.Model.Alert;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class AlertController {
    @FXML
    private Label statusLabel;
    @FXML
    private Label informationLabel;
    @FXML
    private ImageView imageStatus;
    @FXML
    private Button cancelButton;

    private ArrayList<Alert> getDataAlert() {
        ArrayList<Alert> alertList = new ArrayList<Alert>();
        alertList.add(new Alert("Error", "#c0392b", "src/main/java/Assets/Icons/warning (1).png"));
        alertList.add(new Alert("Warning", "#f1c40f", "src/main/java/Assets/Icons/warning.png"));
        alertList.add(new Alert("Success", "#27ae60", "src/main/java/Assets/Icons/check.png"));
        return alertList;
    }
    public boolean RenderAlert(String status, String infomation) throws MalformedURLException {
        ArrayList<Alert> alerts = getDataAlert();
        for (Alert alert : alerts) {
            if (status.equalsIgnoreCase(alert.getStatus())) {
                alert.setInformation(alert.getInformation());
                statusLabel.setText(alert.getStatus());
                alert.setInformation(infomation);
                informationLabel.setText(alert.getInformation());
                System.out.println(alert.getImgSrc());
                imageStatus.setImage(new Image(String.valueOf(new File(alert.getImgSrc()).toURI().toURL())));
                return true;
            }
        }
        return false;
    }
}
