package utils;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
The Connections class is designed as a singleton that gets a connection to the database and
 saves that connection in the Connection called conn.
 */
public class Connections {
    private static Connection conn;

    private Connections() {

    }

    public static Connection getConnection() {
        //This method will throw two exceptions, so I put this in a try catch block to deal with them here.
        if (conn == null) {
            try {
                //Creating a Properties object
                Properties props = new Properties();
                //Creates an object that reads the files that the connection properties are stored on
                FileReader connectionProperties = new FileReader("src/main/resources/Connection.properties");
                //Retrieves the key value pairs for our connection
                props.load(connectionProperties);

                //"jdbc:mariadb://hostname:port/databaseName?user=username&password=password"
                //This gets and concats the key value pairs into the above format ^
                String connString = "jdbc:mariadb://" +
                        props.getProperty("hostname") + ":" +
                        props.getProperty("port") + "/" +
                        props.getProperty("databaseName") + "?user=" +
                        props.getProperty("user") + "&password=" +
                        props.getProperty("password");
                //Saving connection info
                conn = DriverManager.getConnection(connString);
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }
}
