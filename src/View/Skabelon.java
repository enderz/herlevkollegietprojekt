package View;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Created by Daniel on 22-05-2017.
 */
public class Skabelon
{
    public static ArrayList<String> skabelonText()
    {
        Stage window = new Stage();
        window.setTitle("Skabelon");

        GridPane layout = new GridPane();

        Scene scene = new Scene(layout, 300, 300);

        Button button = new Button("Indsæt");

        TextArea textArea = new TextArea();
        textArea.setMaxSize(200,30);

        TextArea textArea2 = new TextArea();
        textArea2.setMaxSize(200,30);

        Label label = new Label("Ophørs dato");
        Label label2 = new Label("Dato for fristen");

        //layout.getChildren().addAll(textArea,button,textArea2,textArea3,textArea4);
        layout.add(textArea, 1,1);
        layout.add(textArea2, 1,3);
        layout.add(button, 2,3);
        layout.add(label, 0,1);
        layout.add(label2,0,3);

        ArrayList skabelonText = new ArrayList();
        button.setOnAction(event ->{

            skabelonText.add(textArea.getText());
            skabelonText.add(textArea2.getText());

            window.close();
        } );
        window.setMaxWidth(400);
        window.setMinWidth(400);
        window.setMaxHeight(180);
        window.setMinHeight(180);
        window.setScene(scene);
        window.show();

        return skabelonText;
    }
}