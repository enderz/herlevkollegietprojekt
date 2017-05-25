package Controller;

import Model.Beboer;
import View.PopUps;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

import java.sql.Connection;

/**
 * Created by Ender on 25-05-2017.
 */
public class TestController {

    PopUps popUps = new PopUps();
    Connection conn;
    TableView<Beboer> beboerListe;
    ObservableList<Beboer> beboerData;

    public void opretBeboerButtonClick()
    {
        Button opretNyBeboerButton = new Button("Opret ny\nbeboer");
        //opretNyBeboerButton.getStyleClass().add("button-opdater-medlem");
        opretNyBeboerButton.setMinWidth(150);
        opretNyBeboerButton.setOnAction(event -> {popUps.opretBeboer(conn, beboerListe, beboerData);
        });
    }
}
