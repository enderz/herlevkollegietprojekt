package Controller;

import Model.Beboer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ender on 16-05-2017.
 */
public class HovedMenuFunktion {

    TableView<Beboer> beboerListe;
    //final ObservableList<Beboer> beboerData = FXCollections.observableArrayList();
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    Connection conn;

   public TableView opdatereBeboerListe(){

       beboerListe = new TableView<>();
       final ObservableList<Beboer> beboerData = FXCollections.observableArrayList();

       beboerData.clear();
       visTableView(beboerListe);
       try{
           String sql = "SELECT * FROM Beboer";
           preparedStatement = conn.prepareStatement(sql);
           resultSet = preparedStatement.executeQuery();

                while(resultSet.next())
                {
                    beboerData.add(new Beboer(
                            resultSet.getInt("VaerelseNr"),
                            resultSet.getString("Navn"),
                            resultSet.getDate("Indflytningsdato"),
                            resultSet.getString("Uddannelsested"),
                            resultSet.getDate("Uddanelsesstart"),
                            resultSet.getDate("Uddannelseafsluttes"),
                            resultSet.getString("Uddannelseretning"),
                            resultSet.getString("Email")
                    ));
                    beboerListe.setItems(beboerData);

                }
                //preparedStatement.close();
                //resultSet.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
            return beboerListe;
    }

    public ObservableList<Beboer> getBeboer() {
        ObservableList<Beboer> beboer = FXCollections.observableArrayList();
        return beboer;
    }
    public void visTableView(TableView beboerListe){

        TableColumn<Beboer, Integer> værelseBeboerListe = new TableColumn<>("Vaerelse");
        værelseBeboerListe.setMinWidth(100);
        værelseBeboerListe.setCellValueFactory(new PropertyValueFactory<>("vaerelseNr"));//Property need to match the class's field names

        TableColumn<Beboer, String> navnBeboerListe = new TableColumn<>("Navn");
        navnBeboerListe.setMinWidth(100);
        navnBeboerListe.setCellValueFactory(new PropertyValueFactory<>("navn"));//Property need to match the class's field names

        TableColumn<Beboer, Date> indflytningsdatoBeboerliste = new TableColumn<>("Indflytningsdato");
        indflytningsdatoBeboerliste.setMinWidth(100);
        indflytningsdatoBeboerliste.setCellValueFactory(new PropertyValueFactory<>("indflytdato"));//Property need to match the class's field names

        TableColumn<Beboer, String> institutionBeboerListe = new TableColumn<>("Uddannelses-\ninstitution");
        institutionBeboerListe.setMinWidth(100);
        institutionBeboerListe.setCellValueFactory(new PropertyValueFactory<>("uddannelsested"));//Property need to match the class's field names

        TableColumn<Beboer, Date> påbegyndtUddannelseBeboerListe = new TableColumn<>("Uddannelse\nPåbegyndt:");
        påbegyndtUddannelseBeboerListe.setMinWidth(100);
        påbegyndtUddannelseBeboerListe.setCellValueFactory(new PropertyValueFactory<>("uddannelsestart"));//Property need to match the class's field names

        TableColumn<Beboer, Date> uddannelseAfsluttesBeboerListe = new TableColumn<>("Uddannelse\nforventes\nafsluttet: ");
        uddannelseAfsluttesBeboerListe.setMinWidth(100);
        uddannelseAfsluttesBeboerListe.setCellValueFactory(new PropertyValueFactory<>("uddannelseafslut"));//Property need to match the class's field names

        TableColumn<Beboer, String> uddannelsesRetningBeboerListe = new TableColumn<>("Uddannelsesretning");
        uddannelsesRetningBeboerListe.setMinWidth(100);
        uddannelsesRetningBeboerListe.setCellValueFactory(new PropertyValueFactory<>("uddannelseretning"));//Property need to match the class's field names

        TableColumn<Beboer, String> emailBeboerListe = new TableColumn<>("Email");
        emailBeboerListe.setMinWidth(100);
        emailBeboerListe.setCellValueFactory(new PropertyValueFactory<>("email"));

        beboerListe.setItems(getBeboer());
        beboerListe.getColumns().addAll(værelseBeboerListe, navnBeboerListe, indflytningsdatoBeboerliste, institutionBeboerListe, påbegyndtUddannelseBeboerListe, uddannelseAfsluttesBeboerListe, uddannelsesRetningBeboerListe, emailBeboerListe);

    }
    public void beboerOkAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Beboer Info");
        alert.setContentText("Beboer oprettet korrekt.");
        alert.show();
    }


}

