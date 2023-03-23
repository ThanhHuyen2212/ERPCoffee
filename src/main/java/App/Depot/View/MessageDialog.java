package App.Depot.View;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.util.Pair;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
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
}
