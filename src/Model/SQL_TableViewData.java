package Model;

import View.TableViews;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static Model.Log.dbConnection;

/**
 * Created by Ender on 30-05-2017.
 */
public class SQL_TableViewData
{
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    TableView<Beboer> beboerListe;
    TableViews tableViews = new TableViews();
    DBConnection dbConnection = new DBConnection();

    public TableView visStudieKontrolBeboer(Connection conn)
    {
        beboerListe = new TableView<>();
        final ObservableList<Beboer> studieKontrolData = FXCollections.observableArrayList();

        studieKontrolData.clear();
        tableViews.visStudieKontrolTableView(beboerListe);

        try{
            String sql = "SELECT VaerelseNr, Navn, Indflytningsdato, Uddannelsested, Uddanelsesstart, Uddannelseafsluttes, Uddannelseretning, KontrolStatus \n" +
                    "FROM Beboer WHERE KontrolStatus IS NULL OR KontrolStatus=''";

            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                studieKontrolData.add(new Beboer(
                        resultSet.getInt("VaerelseNr"),
                        resultSet.getString("Navn"),
                        resultSet.getDate("Indflytningsdato"),
                        resultSet.getString("Uddannelsested"),
                        resultSet.getDate("Uddanelsesstart"),
                        resultSet.getDate("Uddannelseafsluttes"),
                        resultSet.getString("Uddannelseretning"),
                        resultSet.getString("KontrolStatus")
                ));
                beboerListe.setItems(studieKontrolData);
            }
            preparedStatement.close();
            resultSet.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return beboerListe;

    }
    public TableView visStudieKontrolMaaned(Connection conn, String status, String maaned)
    {
        beboerListe = new TableView<>();
        final ObservableList<Beboer> studieKontrolData = FXCollections.observableArrayList();

        studieKontrolData.clear();
        tableViews.visStudieKontrolTableView(beboerListe);

        try{
            String sql = "SELECT VaerelseNr, Navn, Indflytningsdato, Uddannelsested, Uddanelsesstart, Uddannelseafsluttes, Uddannelseretning, KontrolStatus \n" +
                    "FROM Beboer WHERE KontrolStatus = '"+status+"' AND SlutStudieMaaned = '"+maaned+"'";

            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                studieKontrolData.add(new Beboer(
                        resultSet.getInt("VaerelseNr"),
                        resultSet.getString("Navn"),
                        resultSet.getDate("Indflytningsdato"),
                        resultSet.getString("Uddannelsested"),
                        resultSet.getDate("Uddanelsesstart"),
                        resultSet.getDate("Uddannelseafsluttes"),
                        resultSet.getString("Uddannelseretning"),
                        resultSet.getString("KontrolStatus")
                ));
                beboerListe.setItems(studieKontrolData);
            }
            preparedStatement.close();
            resultSet.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return beboerListe;
    }
    public TableView visAlleBeboer(Connection conn)
    {
        beboerListe = new TableView<>();
        final ObservableList<Beboer> beboerData = FXCollections.observableArrayList();

        beboerData.clear();
        tableViews.visBeboerTableView(beboerListe);

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
                        resultSet.getString("Email"),
                        resultSet.getString("KontrolStatus"),
                        resultSet.getString("SlutStudieMaaned"),
                        resultSet.getString("IndflytningsMaaned")
                ));
                beboerListe.setItems(beboerData);
            }
            preparedStatement.close();
            resultSet.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return beboerListe;
    }

    /**
     * @return
     */

    public TableView visSal(Connection conn, int startSal, int slutSal)
    {
        TableView<Beboer> beboerListe = new TableView<>();
        final ObservableList<Beboer> beboerData = FXCollections.observableArrayList();

        tableViews.visBeboerTableView(beboerListe);

        beboerData.clear();
        try{
            String sql = "SELECT * FROM Beboer WHERE VaerelseNR BETWEEN "+startSal+" AND "+slutSal;
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
                        resultSet.getString("Email"),
                        resultSet.getString("KontrolStatus"),
                        resultSet.getString("SlutStudieMaaned"),
                        resultSet.getString("IndflytningsMaaned")
                ));
                beboerListe.setItems(beboerData);
            }
            preparedStatement.close();
            resultSet.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return beboerListe;

    }
}

