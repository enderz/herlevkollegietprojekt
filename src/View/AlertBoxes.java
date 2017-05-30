package View;

import Model.Log;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

/**
 * Created by Herlev Kollegiet Udvikler on 30-05-2017.
 */
public class AlertBoxes
{
    public void loginAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setTitle("Login Status");
        alert.setContentText("Login Fejlet!\nBruger findes ikke. Prøv igen.");
        Log.insertIntoLog("Fejl i login.");
        alert.show();
    }
    public void beboerOkAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Beboer Info");
        alert.setContentText("Beboer oprettet korrekt.");
        Log.insertIntoLog("Beboer oprettet");
        alert.show();
    }
    public void beboerOpdateretOKAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Beboer Opdatering Info");
        alert.setContentText("Beboer er opdateret korrekt.");
        Log.insertIntoLog("Beboer Opdateret");
        alert.show();
    }
}
