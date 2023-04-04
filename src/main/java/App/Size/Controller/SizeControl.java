package App.Size.Controller;

import App.ModuleManager.AppControl;
import App.Size.Model.SizeModel;
import Entity.Size;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class SizeControl implements Initializable {
    @FXML
    private TableView<Size> sizeTable;
    @FXML
    private TableColumn<Size,String> sizeCol;
    @FXML
    private TableColumn<Size,String> desCol;
    @FXML
    private Button addBtn;
    @FXML
    private Button editBtn;
    private SizeModel sizeModel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sizeCol.setCellValueFactory(new PropertyValueFactory<>("sign"));
        desCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        sizeModel = new SizeModel();

        sizeTable.setItems(FXCollections.observableList(sizeModel.getSizes()));
        addBtn.setOnAction(e->{
            Size s = createNewSize();
            if(s != null){
                sizeModel.insertSize(s);
                sizeTable.refresh();
                AppControl.showAlert("success","Success");
            }else{
                AppControl.showAlert("error","Not valid Sign Data");
            }
        });
        editBtn.setOnMouseClicked(e->{
            if(sizeTable.getSelectionModel().getSelectedItem()!=null){
                Size oldsze = sizeTable.getSelectionModel().getSelectedItem();
                Size s = editSize(sizeTable.getSelectionModel().getSelectedItem());
                if(!oldsze.getSign().equalsIgnoreCase(s.getSign())
                        || !oldsze.getDescription().equalsIgnoreCase(s.getDescription()))
                {
                    if(s.getSign().strip().equals("")){
                        System.out.println("false");
                        AppControl.showAlert("error","Sign cant be empty");
                    }else{
                        System.out.println("true");
                        sizeModel.editSize(oldsze,s);
                        sizeTable.refresh();
                    }

                }
            }else{
                AppControl.showAlert("error","Not valid Data");
            }
        });
    }
    public Size createNewSize(){
        Size size;
        AtomicBoolean legal = new AtomicBoolean(false);
        Dialog<Size> dialog = new Dialog<>();
        DialogPane pane = new DialogPane();
        Label sizeLbl = new Label("Sign");
        Label desLbl = new Label("Description");
        TextField sizeTxt = new TextField();
        TextField desTxt = new TextField();
        VBox box = new VBox(
                    new FlowPane(sizeLbl,sizeTxt),
                    new FlowPane(desLbl,desTxt)
        );
        try {
            pane.getStylesheets().add(
                    new File("src/main/java/App/View/css/Hau_Style.css")
                            .toURI().toURL().toExternalForm());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        pane.getStyleClass().add("hau-container-color");
        sizeLbl.getStyleClass().add("hau-menu-label");
        desLbl.getStyleClass().add("hau-menu-label");
        pane.setContent(box);
        dialog.setDialogPane(pane);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.APPLY);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        dialog.setResultConverter(e->{
            if(isValidSign(sizeTxt.getText())){
                return new Size(sizeTxt.getText(), desTxt.getText());
            }else{
                return new Size();
            }
        });
        size = dialog.showAndWait().get();
        if(size.getSign()!=null)
            return size;
        return null;
    }

    private Size editSize(Size size){
        Dialog<Size> dialog = new Dialog<>();
        DialogPane pane = new DialogPane();
        Label sizeLbl = new Label("Sign");
        Label desLbl = new Label("Description");
        TextField sizeTxt = new TextField(size.getSign());
        TextField desTxt = new TextField(size.getDescription());
        VBox box = new VBox(
                new FlowPane(sizeLbl,sizeTxt),
                new FlowPane(desLbl,desTxt)
        );
        try {
            pane.getStylesheets().add(
                    new File("src/main/java/App/View/css/Hau_Style.css")
                            .toURI().toURL().toExternalForm());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        pane.getStyleClass().add("hau-container-color");
        sizeLbl.getStyleClass().add("hau-menu-label");
        desLbl.getStyleClass().add("hau-menu-label");
        pane.setContent(box);
        dialog.setDialogPane(pane);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.APPLY);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        dialog.setResultConverter(e-> new Size(sizeTxt.getText(), desTxt.getText()));


        Size s = dialog.showAndWait().get();
        if(size.getSign().equalsIgnoreCase(s.getSign())
                && size.getDescription().equalsIgnoreCase(s.getDescription())){
            return size;
        }else{
            return s;
        }
    }

    private boolean isValidSign(String sign){
        for(Size s : sizeModel.getSizes()){
            if(sign.strip().equalsIgnoreCase("")|| s.getSign().equalsIgnoreCase(sign)) return false;
        }
        return true;
    }
    private boolean isValidChange(Size s, String nwS, String nwDs){
        return !s.getSign().equalsIgnoreCase(nwS) && !s.getDescription().equalsIgnoreCase(nwDs);
    }
}
