package Model;

import View.AlertBoxes;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 * Created by Herlev Kollegiet Udvikler on 30-05-2017.
 */
public class SQL_DML_Beboer
{
    AlertBoxes alertBoxes = new AlertBoxes();
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    public SQL_DML_Beboer(){}

    public void insertData_Beboer(Stage window, Connection conn, TableView<Beboer> beboerListe,
                                  ObservableList<Beboer> beboerData, TextField textVærelse,TextField textNavn,
                                  TextField textIndflytning,TextField textUddannelsesInstitution, TextField textUddannelsePåbegyndt,
                                  TextField textUddannelseForventesAfsluttet, TextField textUddannelsesRetning, TextField textEmail,
                                  TextField textStatus, TextField textSlutMaaned, TextField textIndflytMaaned )
    {
        try
        {
            String sqlInsert = "INSERT INTO Beboer (VaerelseNr, Navn, Indflytningsdato, Uddannelsested, Uddanelsesstart, Uddannelseafsluttes, Uddannelseretning, Email, KontrolStatus, SlutStudieMaaned, IndflytningsMaaned) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            preparedStatement = conn.prepareStatement(sqlInsert);
            preparedStatement.setString(1, textVærelse.getText());
            preparedStatement.setString(2, textNavn.getText());
            preparedStatement.setString(3, textIndflytning.getText());
            preparedStatement.setString(4, textUddannelsesInstitution.getText());
            preparedStatement.setString(5, textUddannelsePåbegyndt.getText());
            preparedStatement.setString(6, textUddannelseForventesAfsluttet.getText());
            preparedStatement.setString(7, textUddannelsesRetning.getText());
            preparedStatement.setString(8, textEmail.getText());
            preparedStatement.setString(9, textStatus.getText());
            preparedStatement.setString(10, textSlutMaaned.getText());
            preparedStatement.setString(11, textIndflytMaaned.getText());

            preparedStatement.execute();
            preparedStatement.close();

            try {
                java.util.Date dateindflyt = new SimpleDateFormat("yyyy-MM-dd").parse(textIndflytning.getText());
                java.util.Date uddanelsestart = new SimpleDateFormat("yyyy-MM-dd").parse(textUddannelsePåbegyndt.getText());
                java.util.Date uddannelseslut = new SimpleDateFormat("yyyy-MM-dd").parse(textUddannelseForventesAfsluttet.getText());

                beboerData.add(new Beboer(Integer.parseInt(textVærelse.getText()), textNavn.getText(), dateindflyt, textUddannelsesInstitution.getText(), uddanelsestart, uddannelseslut, textUddannelsesRetning.getText(), textEmail.getText(), textStatus.getText(), textSlutMaaned.getText(), textIndflytMaaned.getText()));
              } catch (Exception exc) {
                    exc.printStackTrace();
                    }
            alertBoxes.beboerOkAlert();
            window.close();

        }catch(SQLException ex)
            {
                 ex.printStackTrace();
            }
    }
    public void hentBeboer(Connection conn, TextField textFindVærelse, TextField textVærelse, TextField textNavn, TextField textIndflytning,
                           TextField textUddannelsesInstitution, TextField textUddannelsePåbegyndt, TextField textUddannelseForventesAfsluttet,
                           TextField textUddannelsesRetning, TextField textEmail)
    {
        try{
            String currentSql = "SELECT * FROM Beboer WHERE VaerelseNr ="+textFindVærelse.getText();
            preparedStatement = conn.prepareStatement(currentSql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                textVærelse.setText(resultSet.getString("VaerelseNr"));
                textNavn.setText(resultSet.getString("Navn"));
                textIndflytning.setText(resultSet.getString("Indflytningsdato"));
                textUddannelsesInstitution.setText(resultSet.getString("Uddannelsested"));
                textUddannelsePåbegyndt.setText(resultSet.getString("Uddanelsesstart"));
                textUddannelseForventesAfsluttet.setText(resultSet.getString("Uddannelseafsluttes"));
                textUddannelsesRetning.setText(resultSet.getString("Uddannelseretning"));
                textEmail.setText(resultSet.getString("Email"));
            }
            preparedStatement.close();
            resultSet.close();

        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    public void opdatereBeboer(Connection conn, Stage window, TextField textFindVærelse, TextField textVærelse,
                               TextField textNavn, TextField textIndflytning, TextField textUddannelsesInstitution,
                               TextField textUddannelsePåbegyndt, TextField textUddannelseForventesAfsluttet,
                               TextField textUddannelsesRetning, TextField textEmail)
    {
        try{
            String updateSql = "UPDATE Beboer SET VaerelseNr = ?, Navn = ?, Indflytningsdato = ?, Uddannelsested = ?, Uddanelsesstart = ?," +
                    "Uddannelseafsluttes = ?, Uddannelseretning = ?, Email = ? WHERE VaerelseNr="+textFindVærelse.getText();

            preparedStatement = conn.prepareStatement(updateSql);
            preparedStatement.setString(1, textVærelse.getText());
            preparedStatement.setString(2, textNavn.getText());
            preparedStatement.setString(3, textIndflytning.getText());
            preparedStatement.setString(4, textUddannelsesInstitution.getText());
            preparedStatement.setString(5, textUddannelsePåbegyndt.getText());
            preparedStatement.setString(6, textUddannelseForventesAfsluttet.getText());
            preparedStatement.setString(7, textUddannelsesRetning.getText());
            preparedStatement.setString(8, textEmail.getText());

            preparedStatement.executeUpdate();
            preparedStatement.close();

            alertBoxes.beboerOpdateretOKAlert();
            window.close();
        }catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }
    public void hentStudieKontrolInfo(Connection conn, TextField textFindVærelse, TextField textVærelse, TextField textNavn,
                                      TextField textIndflytning, TextField textUddannelsesInstitution, TextField textUddannelsePåbegyndt,
                                      TextField textUddannelseForventesAfsluttet, TextField textUddannelsesRetning, TextField textStatus,
                                      TextField textSlutMaaned, TextField textIndflytMaaned)
    {
        try{
            String currentSql = "SELECT VaerelseNr, Navn, Indflytningsdato, Uddannelsested, Uddanelsesstart, Uddannelseafsluttes, Uddannelseretning, KontrolStatus,SlutStudieMaaned, IndflytningsMaaned FROM Beboer WHERE VaerelseNr="+textFindVærelse.getText();
            preparedStatement = conn.prepareStatement(currentSql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                textVærelse.setText(resultSet.getString("VaerelseNr"));
                textNavn.setText(resultSet.getString("Navn"));
                textIndflytning.setText(resultSet.getString("Indflytningsdato"));
                textUddannelsesInstitution.setText(resultSet.getString("Uddannelsested"));
                textUddannelsePåbegyndt.setText(resultSet.getString("Uddanelsesstart"));
                textUddannelseForventesAfsluttet.setText(resultSet.getString("Uddannelseafsluttes"));
                textUddannelsesRetning.setText(resultSet.getString("Uddannelseretning"));
                textStatus.setText(resultSet.getString("KontrolStatus"));
                textSlutMaaned.setText(resultSet.getString("SlutStudieMaaned"));
                textIndflytMaaned.setText(resultSet.getString("IndflytningsMaaned"));
            }
            preparedStatement.close();
            resultSet.close();

        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    public void opdatereStudieKontrol(Connection conn, Stage window, TextField textFindVærelse, TextField textVærelse, TextField textNavn,
                                      TextField textIndflytning, TextField textUddannelsesInstitution, TextField textUddannelsePåbegyndt,
                                      TextField textUddannelseForventesAfsluttet, TextField textUddannelsesRetning, TextField textStatus,
                                      TextField textSlutMaaned, TextField textIndflytMaaned)
    {
        try{
            String updateSql1 = "UPDATE Beboer SET VaerelseNr = ?, Navn = ?, Indflytningsdato = ?, Uddannelsested = ?, Uddanelsesstart = ?," +
                    "Uddannelseafsluttes = ?, Uddannelseretning = ?, KontrolStatus = ?, SlutStudieMaaned = ?, IndflytningsMaaned=? WHERE VaerelseNr="+textFindVærelse.getText();

            preparedStatement = conn.prepareStatement(updateSql1);
            preparedStatement.setString(1, textVærelse.getText());
            preparedStatement.setString(2, textNavn.getText());
            preparedStatement.setString(3, textIndflytning.getText());
            preparedStatement.setString(4, textUddannelsesInstitution.getText());
            preparedStatement.setString(5, textUddannelsePåbegyndt.getText());
            preparedStatement.setString(6, textUddannelseForventesAfsluttet.getText());
            preparedStatement.setString(7, textUddannelsesRetning.getText());
            preparedStatement.setString(8, textStatus.getText());
            preparedStatement.setString(9, textSlutMaaned.getText());
            preparedStatement.setString(10,textIndflytMaaned.getText());

            preparedStatement.executeUpdate();
            preparedStatement.close();
            alertBoxes.beboerOpdateretOKAlert();

            window.close();
        }catch (SQLException sqle){
            sqle.printStackTrace();
        }

    }
    public void paaBegyndKontrol(Stage window, Connection conn, ChoiceBox maaned)
    {
        try{
            String updateKontrolSQL = "UPDATE Beboer SET KontrolStatus='' WHERE SlutStudieMaaned='"+maaned.getValue()+"'";

            System.out.println(updateKontrolSQL);
            preparedStatement = conn.prepareStatement(updateKontrolSQL);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            //alertBoxes.beboerOpdateretOKAlert();
            window.close();
        }catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }
    public void printBrugerData(ResultSet resultSet)
    {
        try
        {
            int brugerId = resultSet.getInt("bruger_id");
            String navn = resultSet.getString("Navn");
            String email = resultSet.getString("Emailadresse");
            String brugernavn = resultSet.getString("Brugernavn");
            String password = resultSet.getString("Password");
            String rolle = resultSet.getString("Rolle");

            System.out.println("Bruger Id: " + brugerId);
            System.out.println("Navn: " + navn);
            System.out.println("Email: " + email);
            System.out.println("Brugernavn: " + brugernavn);
            System.out.println("Password: " + password);
            System.out.println("Rolle: " + rolle);


        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

}




