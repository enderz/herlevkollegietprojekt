package Controller;

import Model.SQLStatements;
import javafx.scene.control.TextField;

import java.sql.*;
/**
 * Created by Ender on 15-05-2017.
 */
public class LoginFunktion {

    ResultSet resultSet;
    SQLStatements sqlStmt = new SQLStatements();
    PreparedStatement preparedStatement;

    public boolean login(Connection conn, TextField nameInput, TextField passwordInput){
        boolean fundet = false;
        try{
            String sql = "SELECT * FROM Bruger WHERE Brugernavn=? AND Password=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,nameInput.getText());
            preparedStatement.setString(2,passwordInput.getText());
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
            {
                System.out.println("Bruger fundet!");
                passwordInput.clear();
                sqlStmt.printBrugerData(resultSet);
                fundet = true;
                return fundet;
            }else{
                System.out.println("Bruger findes ikke!");
                passwordInput.clear();
                return fundet;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return fundet;
    }
}
