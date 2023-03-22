package App.Depot.Controller;

import App.Depot.Model.POModel;
import Entity.PurchaseDetail;
import Entity.PurchaseOrder;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DetailPOController implements Initializable {
    @FXML
    private TableView<PurchaseDetail> detailTable;
    @FXML
    private TableColumn<PurchaseDetail, Integer> qtyCol;
    @FXML
    private TableColumn<PurchaseDetail, Integer> receiveCol;
    @FXML
    private TableColumn<PurchaseDetail, String> idCol;
    @FXML
    private TableColumn<PurchaseDetail, String> ingredientNameCol;
    @FXML
    private TableColumn<PurchaseDetail, String> priceCol;
    @FXML
    private Label totalOrderLbl;
    @FXML
    private Label totalRevLbl;
    @FXML
    private Button cancelBtn;
    @FXML
    private Button confirmBtn;
    @FXML
    private Button printBtn;
    private POModel model;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new POModel();

        idCol.setCellValueFactory(data -> new SimpleStringProperty(
                String.valueOf(data.getValue().getIngredient().getIngredientId())
        ));
        ingredientNameCol.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getIngredient().getIngredientName()
        ));
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("orderQty"));
        receiveCol.setCellValueFactory(new PropertyValueFactory<>("receiveQty"));
        priceCol.setCellValueFactory(data -> new SimpleStringProperty(
                String.valueOf(data.getValue().getIngredient().getPrice())
        ));
        editableCols();
    }

    public void init(POModel modelOfSib, PurchaseOrder selected) {
        model = modelOfSib;
        model.setCurrent(selected);

        detailTable.setItems(model.getCurrentDetails());

        handleInitGUI();
    }

    public void handleInitGUI() {
        float sum = 0;
        for(PurchaseDetail pd : model.getCurrent().getDetails()) {
            sum += pd.getIngredient().getPrice() * pd.getOrderQty();
        }
        totalOrderLbl.setText(String.valueOf(sum));

        sum = 0;
        for(PurchaseDetail pd : model.getCurrent().getDetails()) {
            sum += pd.getIngredient().getPrice() * pd.getReceiveQty();
        }
        totalRevLbl.setText(String.valueOf(sum));

//        If PO is confirmed, the confirmation button is hidden
        if(model.isConfirm()) {
            confirmBtn.setVisible(false);
        }
    }

    public void editableCols() {
        receiveCol.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>() {
            @Override
            public String toString(Integer integer) {
                return String.valueOf(integer);
            }
            @Override
            public Integer fromString(String s) {
                int value = model.getCurrent().getDetails().get(detailTable.getSelectionModel().getSelectedIndex()).getReceiveQty();
                try {
                    value = Integer.parseInt(s);
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Enter number please");
                    alert.show();
                }
                return value;
            }
        }));

        receiveCol.setOnEditCommit(e -> {e.getTableView().getItems().get(
                e.getTablePosition().getRow()).setReceiveQty(e.getNewValue());
//            System.out.println(model.getCurrent().getDetails().get(
//                    detailTable.getSelectionModel().getSelectedIndex()).getReceiveQty());
        });

        detailTable.setEditable(true);
    }

    public void handleActionBtn() {
        EventHandler<ActionEvent> buttonAddHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Node source = (Node) event.getSource();
                Stage theCurrStage = (Stage) source.getScene().getWindow();

                String filepath = "src/main/java/App/Depot/View/POInput.fxml";
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(new File(filepath).toURI().toURL());
                    theCurrStage.setScene(new Scene(fxmlLoader.load()));

                    POInputController controller = fxmlLoader.getController();
                    controller.init(model);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        EventHandler<ActionEvent> buttonCancelHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Node source = (Node) event.getSource();
                Stage theCurrStage = (Stage) source.getScene().getWindow();

                String filepath = "src/main/java/App/Depot/View/POInput.fxml";
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(new File(filepath).toURI().toURL());
                    theCurrStage.setScene(new Scene(fxmlLoader.load()));

                    POInputController controller = fxmlLoader.getController();
                    controller.init(model);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        confirmBtn.setOnAction(buttonAddHandler);
        cancelBtn.setOnAction(buttonCancelHandler);
    }


}
