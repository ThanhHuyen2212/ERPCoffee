package App.Depot.View;

import App.Controller.Alert2Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

public class MessageDialog {
    private String title;
    private String header;
    private String message;
    private String type;
    public static final HashMap<String, String> TYPES = new HashMap<>() {{
                put("Information", "info");
                put("Warning", "warning");
                put("Error", "error");
                put("Confirmation", "confirmation");
    }};


    public MessageDialog(String title, String header, String message, String type) {
        this.title = title;
        this.header = header;
        this.message = message;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int showMessage() {
        Alert alert;
        switch (this.type) {
            case "info" -> alert = new Alert(Alert.AlertType.INFORMATION);
            case "warning" -> alert = new Alert(Alert.AlertType.WARNING);
            case "error" -> alert = new Alert(Alert.AlertType.ERROR);
            case "confirmation" -> alert = new Alert(Alert.AlertType.CONFIRMATION);
            default -> {
                alert = new Alert(Alert.AlertType.NONE);
                alert.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            }
        }
        alert.setTitle(this.title);
        alert.setHeaderText(this.header);
        alert.setContentText(this.message);
        Optional<ButtonType> result = alert.showAndWait();
        try {
            if (result.get() == ButtonType.OK) {
                return 1;
            }
        } catch (Exception e) {
            return 0;
        }
        return 0;
    }


    public static int showAlert(String status, String information) {
        FXMLLoader loader = new FXMLLoader();
        Pane alert = null;
        try {
            loader.setLocation(new File("src/main/java/App/View/Alert2.fxml").toURI().toURL());
            alert = loader.load();
            Alert2Controller controller = loader.getController();
            controller.RenderAlert(status, information);
        } catch (IOException e) {
            System.out.println("Loi ham showAlert roi ne!!!!");
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane((DialogPane) alert);
        Optional<ButtonType> clickedButton = dialog.showAndWait();
        if (clickedButton.get() == ButtonType.OK) {
            return 1;
        }else
            return 0;
    }

}
