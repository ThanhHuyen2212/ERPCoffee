package Util;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileTool {
    final String imagePath = "src/main/resources/testimg.png";

    public void createPdf(String pdfPath, Node... nodes) throws IOException, DocumentException {
        Image img = Image.getInstance(imagePath);
        Document document = new Document(img);
        PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
        document.open();
        for(Node node : nodes){
            WritableImage izz = node.snapshot(new SnapshotParameters(),null);
            saveImage(izz);
            img = Image.getInstance(imagePath);
            document.setPageSize(img);
            document.newPage();
//            img.setScaleToFitHeight(true);
            img.setAbsolutePosition(0, 0);
            document.add(img);
        }
        document.close();
    }

    private void saveImage(WritableImage snapshot) {
        BufferedImage image;
        image = SwingFXUtils.fromFXImage(snapshot, new BufferedImage(550, 400, BufferedImage.TYPE_INT_ARGB));
        try {
            Graphics2D gd = (Graphics2D) image.getGraphics();
            ImageIO.write(image, "png", new File(imagePath));
        } catch (IOException ex) {
            ex.printStackTrace();
        };
    }
}
