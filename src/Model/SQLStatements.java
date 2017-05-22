package Model;

import java.sql.*;
import java.util.Scanner;

/**
 * Created by Ender on 08-05-2017.
 */
public class SQLStatements
{
    public void seBeboerListe(Connection connection)
    {
        try
        {
            Statement statement = connection.createStatement();
            String sqlSelect = "SELECT * FROM Beboer";
            statement.execute(sqlSelect);
            System.out.println(sqlSelect);
            ResultSet resultSet = statement.executeQuery(sqlSelect);
            printData(resultSet);//metoden der printer alle data ud af tabellen fra databasen
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    public void hentBeboer(Connection connection)
    {
        try{
            Scanner input = new Scanner(System.in);
            System.out.println("Værelse Nummer: ");
            int roomNr = input.nextInt();
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM Beboer WHERE VaerelseNr ="+roomNr;
            System.out.println(sql);
            statement.execute(sql);
            ResultSet resultSet = statement.getResultSet();
            printData(resultSet);//metoden der printer alle data ud af tabellen fra databasen

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void visBrugerListe(Connection connection)
    {
        try
        {
            Statement statement = connection.createStatement();
            String sqlSelect = "SELECT * FROM Bruger";
            statement.execute(sqlSelect);
            System.out.println(sqlSelect);
            ResultSet resultSet = statement.executeQuery(sqlSelect);
            printBrugerData(resultSet);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    public void visBruger(Connection connection)
    {
        try
        {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM Bruger WHERE Brugernavn=? AND Password=?";
            statement.execute(sql);
            System.out.println(sql);
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next())
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
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    public void opdaterBeboerListe(Connection connection) throws SQLException
    {
        Scanner input = new Scanner(System.in);
        System.out.println("Værelse Nummer: ");
        int roomNr = input.nextInt();
        Statement statement = connection.createStatement();
        //statement.execute("BEGIN");
        String sqlselect = "";
        statement.execute(sqlselect);
        System.out.println(sqlselect);
        ResultSet resultSet = statement.getResultSet();
        resultSet.next();//there is only one result now
        //String sqlUpdate = "UPDATE Beboer SET amount = "+amount+" WHERE kontonr = 1";
        //System.out.println(sqlUpdate);
        //statement.execute(sqlUpdate);
        //hvis der sker noget, som gør I fortryder handlinger efter "commit"
        // så kald "rollback", som neutraliserer alle handlinger efter "commit"
        //statement.execute("COMMIT");
        //statement.execute("ROLLBACK");
        //closeDatabaseConnection(connection);
    }
    public void printData(ResultSet resultSet)
    {
        try
        {
            while (resultSet.next()) {
                int vaerelseNr        = resultSet.getInt("VaerelseNR");
                String navn           = resultSet.getString("Navn");
                Date indflyt          = resultSet.getDate("Indflytningsdato");
                String uddannelsested = resultSet.getString("Uddannelsested");
                Date uddannelsestart  = resultSet.getDate("Uddanelsesstart");
                Date uddannelseafslut = resultSet.getDate("Uddannelseafsluttes");
                String uddannelseret  = resultSet.getString("Uddannelseretning");
                String email          = resultSet.getString("Email");

                System.out.println("Værelse Nr: " + vaerelseNr);
                System.out.println("Navn : " + navn);
                System.out.println("Indflytningsdato: " + indflyt);
                System.out.println("Uddannelsested: " + uddannelsested);
                System.out.println("Uddannelsestart: " + uddannelsestart);
                System.out.println("Uddannelseafsluttes: " + uddannelseafslut);
                System.out.println("Uddannelseretning: " + uddannelseret);
                System.out.println("Email: " + email);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
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
