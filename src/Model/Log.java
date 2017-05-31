package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by Herlev Kollegiet Udvikler on 29-05-2017.
 *
 * Ender Zorsøker.
 *
 */
public class Log {

    static PreparedStatement preparedStatement;
    static Connection conn;
    static ResultSet resultSet;
    static DBConnection dbConnection = new DBConnection();

    public Log(){}

    public static void insertIntoLog(String aktivitet)
    {
        conn = dbConnection.getDBConnection();
        try{
            String insertLog = "INSERT INTO Log (Aktivitet,Tid) VALUES (?,now())";

            preparedStatement = conn.prepareStatement(insertLog);
            preparedStatement.setString(1, aktivitet );
            preparedStatement.execute();
            preparedStatement.close();

        }catch(SQLException exc){
            exc.printStackTrace();
        }
    }
}
