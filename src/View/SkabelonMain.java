package View;

import Model.Skabelonudfyldning;
import Controller.SkabelonController;
import com.sun.xml.internal.bind.AnyTypeAdapter;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.*;
import org.w3c.dom.ranges.Range;

import java.awt.*;
import java.awt.font.NumericShaper;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;


/**
 * Created by Daniel on 08-05-2017.
 */

public class SkabelonMain extends Application
{

    private SkabelonController controller = new SkabelonController();

    public static void main(String[] args)
    {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception
    {
        GridPane root = new GridPane();

        Scene scene = new Scene(root,300,300);

        Button indledningButton = new Button("Indledning");
        Button file1Button = new Button("File 1");
        Button file2Button = new Button("File 2");
        Button writeToFileButton = new Button("Write to File");
        Button skabelonButton = new Button("skabelon");

        skabelonButton.setTranslateX(5);
        skabelonButton.setTranslateY(140);
        indledningButton.setTranslateX(5);
        indledningButton.setTranslateY(15);
        file1Button.setTranslateX(5);
        file1Button.setTranslateY(50);
        file2Button.setTranslateX(5);
        file2Button.setTranslateY(85);
        writeToFileButton.setTranslateX(5);
        writeToFileButton.setTranslateY(115);

        root.getChildren().addAll(indledningButton,file1Button,file2Button, writeToFileButton,skabelonButton);

        // skabelonudfyldning array i controlleren, bliver fyldt med elements fra GUI
        skabelonButton.setOnAction(event -> {
            controller.skabelonUdfyldning = controller.fillSkabelonArray(Skabelon.skabelonText());

        });

        writeToFileButton.setOnAction(event ->
        {
            controller.fillDocument();

        });
        primaryStage.setTitle("Title");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}