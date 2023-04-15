package App.Depot.Controller;

import App.Depot.Model.POModel;
import Entity.PurchaseDetail;
import Entity.PurchaseOrder;
import Main.MainApp;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static Main.MainApp.APP;

public class POManagementController implements Initializable {
    @FXML
    private TableView<PurchaseOrder> poTable;
    @FXML
    private TableColumn<PurchaseOrder, String> buyerCol;
    @FXML
    private TableColumn<PurchaseOrder, ?> idCol;
    @FXML
    private TableColumn<PurchaseOrder, ?> orderDateCol;
    @FXML
    private TableColumn<PurchaseOrder, String> statusCol;

    @FXML
    private TableColumn<PurchaseOrder, ?> totalCol;

    @FXML
    private TableColumn<PurchaseOrder, ?> vendorCol;
    @FXML
    private Label searchBtn;
    @FXML
    private TextField searchTxf;
    @FXML
    private Button addBtn;
    private POModel model;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idCol.setCellValueFactory(new PropertyValueFactory<>("purchaseOrderId"));
        vendorCol.setCellValueFactory(new PropertyValueFactory<>("supplier"));
        orderDateCol.setCellValueFactory(new PropertyValueFactory<>("purchaseOrderDate"));
        buyerCol.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getEmployeeCreate().getName()
        ));
        totalCol.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        statusCol.setCellValueFactory(data -> new SimpleStringProperty(
                (data.getValue().getEmployeeConfirm() != null) ? "Done" : "Not Confirmed"
        ));

        init();

        APP.getPOSButton("purchase").setOnAction(e -> {
            poTable.setItems(model.getPurchaseOrderObservableList());
            poTable.refresh();
        });
    }

    public void init() {
        model = new POModel();
        model.init();
        poTable.setItems(model.getPurchaseOrderObservableList());

        handleActionOnRow();
        handleActionBtn();
    }

    public void handleActionOnRow() {
//        poTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
//            if (newSelection != null) {
//                String filepath = "src/main/java/App/Depot/View/DetailPOView.fxml";
//                FXMLLoader fxmlLoader;
//                Stage childStage = new Stage();
//                Parent root;
//                try {
//                    fxmlLoader = new FXMLLoader(new File(filepath).toURI().toURL());
//                    root = fxmlLoader.load();
//                    DetailPOController controller = fxmlLoader.getController();
//                    controller.init(model, newSelection);
//                    childStage.setScene(new Scene(root));
//                    childStage.showAndWait();
//                    poTable.refresh();
//                } catch (IOException e) {
//                    System.out.println("Khong load duoc child stage");
//                    throw new RuntimeException(e);
//                }
//            }
//        });

        poTable.setRowFactory(new Callback<TableView<PurchaseOrder>, TableRow<PurchaseOrder>>() {
            @Override
            public TableRow<PurchaseOrder> call(TableView<PurchaseOrder> tableView2) {
                final TableRow<PurchaseOrder> row = new TableRow<>();
                row.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        final int index = row.getIndex();
                        PurchaseOrder newSelection = poTable.getSelectionModel().getSelectedItem();
                        if (newSelection != null) {
                            String filepath = "src/main/java/App/Depot/View/DetailPOView.fxml";
                            FXMLLoader fxmlLoader;
                            Stage childStage = new Stage();
                            Parent root;
                            try {
                                fxmlLoader = new FXMLLoader(new File(filepath).toURI().toURL());
                                root = fxmlLoader.load();
                                DetailPOController controller = fxmlLoader.getController();
                                controller.init(model, newSelection);
                                childStage.setScene(new Scene(root));
                                childStage.showAndWait();
                                poTable.refresh();
                            } catch (IOException e) {
                                System.out.println("Khong load duoc child stage");
                                throw new RuntimeException(e);
                            }
                        }
                        if (index >= 0 && index < poTable.getItems().size() &&
                                poTable.getSelectionModel().isSelected(index)  ) {
                            poTable.getSelectionModel().clearSelection();
                            event.consume();
                        }
                    }
                });
                return row;
            }
        });
    }

    public void handleActionBtn() {
        EventHandler<ActionEvent> buttonAddHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String filepath = "src/main/java/App/Depot/View/POInput.fxml";
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(new File(filepath).toURI().toURL());
                    MainApp.show(fxmlLoader.load());
                    POInputController controller = fxmlLoader.getController();
                    controller.init(model);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        addBtn.setOnAction(buttonAddHandler);
    }
}
