package App.Controller;

import App.Model.OrderGUI;
import Entity.Order;
import Logic.OrderManagement;
import Util.FileTool;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfPage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderGUIController implements Initializable {
    OrderManagement orderManagement = new OrderManagement();
    ArrayList<Order> orders = new ArrayList<>();
    @FXML
    private TableView<OrderGUI> OrderTable;

    @FXML
    private Button btnSearch;

    @FXML
    private TableColumn<OrderGUI, String> customerName;

    @FXML
    private TableColumn<OrderGUI, Date> orderDate;

    @FXML
    private TableColumn<OrderGUI, Integer> orderID;

    @FXML
    private TableColumn<OrderGUI, Integer> totalPrice;
    @FXML
    private TableColumn<OrderGUI, Void> receipt;
    @FXML
    private TableColumn<OrderGUI, Void> printOrder;

    @FXML
    private TextField txtSearchOrder;
    ObservableList<OrderGUI> orderTableObservableList;


    public ArrayList<OrderGUI> changeToOrderGUI(ArrayList<Order> orders){
        ArrayList<OrderGUI> orderGUIS = new ArrayList<>();
        for(Order o :orders){
             orderGUIS.add(new OrderGUI(o));
         }
        return orderGUIS;
    }
    public void init(){
        orders=orderManagement.getOrderList();;
        orderTableObservableList= FXCollections.observableArrayList(changeToOrderGUI(orders));
        orderID.setCellValueFactory(new PropertyValueFactory<OrderGUI, Integer>("orderID"));
        customerName.setCellValueFactory(new PropertyValueFactory<OrderGUI, String>("customerName"));
        orderDate.setCellValueFactory(new PropertyValueFactory<OrderGUI,Date>("date"));
        totalPrice.setCellValueFactory(new PropertyValueFactory<OrderGUI, Integer>("totalPrice"));;
        OrderTable.setItems(orderTableObservableList);
        Callback<TableColumn<OrderGUI, Void>, TableCell<OrderGUI, Void>> cellFactory = new Callback<TableColumn<OrderGUI, Void>, TableCell<OrderGUI, Void>>() {
            @Override
            public TableCell<OrderGUI, Void> call(final TableColumn<OrderGUI, Void> param) {
                final TableCell<OrderGUI, Void> cell = new TableCell<OrderGUI, Void>() {

                    private final Button btn = new Button("Receipt");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            OrderGUI data = getTableView().getItems().get(getIndex());
                            FXMLLoader loader = new FXMLLoader();
                            String orderFIle = "src/main/java/App/View/Order.fxml";
                            try {
                                loader.setLocation(new File(orderFIle).toURI().toURL());
                            } catch (MalformedURLException e) {
                                throw new RuntimeException(e);
                            }
                            Pane order = null;
                            try {
                                order = loader.load();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            Dialog<ButtonType> dialog = new Dialog<>();
                            OrderController orderController = loader.getController();
                            dialog.setDialogPane((DialogPane) order);
                            orderController.RenderOrder(data.getOrder());
                            Optional<ButtonType> clickedButton = dialog.showAndWait();
                            if (clickedButton.get() == ButtonType.NO) {
                                dialog.close();
                            } else if (clickedButton.get() == ButtonType.YES) {
                                //code
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        receipt.setCellFactory(cellFactory);
        Callback<TableColumn<OrderGUI, Void>, TableCell<OrderGUI, Void>> cellFactory1 = new Callback<TableColumn<OrderGUI, Void>, TableCell<OrderGUI, Void>>() {
            @Override
            public TableCell<OrderGUI, Void> call(final TableColumn<OrderGUI, Void> param) {
                final TableCell<OrderGUI, Void> cell = new TableCell<OrderGUI, Void>() {

                    private final Button btn = new Button("Print Receipt");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            OrderGUI data = getTableView().getItems().get(getIndex());
                            FXMLLoader loader = new FXMLLoader();
                            String orderFIle = "src/main/java/App/View/Order.fxml";
                            try {
                                loader.setLocation(new File(orderFIle).toURI().toURL());
                            } catch (MalformedURLException e) {
                                throw new RuntimeException(e);
                            }
                            Pane order = null;
                            try {
                                order = loader.load();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            Dialog<ButtonType> dialog = new Dialog<>();
                            OrderController orderController = loader.getController();
                            dialog.setDialogPane((DialogPane) order);
                            orderController.RenderOrder(data.getOrder());;
                            Optional<ButtonType> clickedButton = dialog.showAndWait();
                            if (clickedButton.get() == ButtonType.NO) {
                                dialog.close();
                            } else if (clickedButton.get() == ButtonType.YES) {
                                WritableImage image = order.snapshot(new SnapshotParameters(), null);
                                File file = new File("D:\\"+data.getOrder().getOrderId()+".png");
                                try {
                                    ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                PDDocument doc    = new PDDocument();
                                PDPage page = new PDPage();
                                PDImageXObject pdimage;
                                PDPageContentStream content;
                                try {
                                    int actualPDFWidth = 0;
                                    int actualPDFHeight = 0;
                                    actualPDFWidth = (int) PDRectangle.A4.getWidth();
                                    actualPDFHeight = (int) PDRectangle.A4.getHeight();
                                    pdimage = PDImageXObject.createFromFile("D:\\"+data.getOrder().getOrderId()+".png",doc);
                                    content = new PDPageContentStream(doc, page);
                                    content.drawImage(pdimage, 10,-40,actualPDFWidth,actualPDFHeight);
                                    content.close();
                                    doc.addPage(page);
                                    doc.save("D:\\"+data.getOrder().getOrderId()+String.valueOf(data.getDate())+".pdf");
                                    doc.close();
                                    file.delete();
                                } catch (IOException ex) {
                                    Logger.getLogger(OrderGUIController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }

                        });

                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        printOrder.setCellFactory(cellFactory1);
    }
    public void reload(Order order){
        orders.add(order);
        orderTableObservableList= FXCollections.observableArrayList(changeToOrderGUI(orders));

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init();
    }
}
