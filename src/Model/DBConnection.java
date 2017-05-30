package Model;

import java.sql.*;
/**
 * Created by Herlev Kollegiet Udvikler on 07-05-2017.
 */
public class DBConnection {

    Connection dbconn  = null;
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    public Connection getDBConnection() {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = null;
        String DB_Url = System.getProperty("JDBC_CONN_STRING");
        String DB_Password = "&password="+System.getProperty("JDBC_PASSWORD_Projekt");
        //passwrd is defined in another location
        try {
            connection = DriverManager.getConnection(DB_Url+DB_Password);
            System.out.println("Connecting to Database...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void closeDBConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
                System.out.println("Database Connection Closed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void checkDBConnection()
    {
        dbconn = getDBConnection();
        if(dbconn == null)
        {
            System.out.println("No Database Connection !!!");
            System.exit(1);
        }else {
            System.out.println("DB Connection OK. SUCCESFULL Connection...");
        }
    }
}
