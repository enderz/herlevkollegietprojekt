package Controller;

import Model.SQL_DML_Beboer;
import javafx.scene.control.TextField;

import java.sql.*;
/**
 * Created by Herlev Kollegiet Udvikler on 15-05-2017.
 * Udviklet af Benjamin K. Pedersen & Ender Zorsøker
 */
public class LoginFunktion implements ILoginFunktion {

    ResultSet resultSet;
    SQL_DML_Beboer sql_dml_beboer = new SQL_DML_Beboer();
    PreparedStatement preparedStatement;

    /**
     * @param conn
     * @param nameInput
     * @param passwordInput
     * @return
     *
     * */
    @Override
    public boolean login(Connection conn, TextField nameInput, TextField passwordInput) {
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
                sql_dml_beboer.printBrugerData(resultSet);
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
