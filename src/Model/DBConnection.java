// This class is used to create the connection with the database created on MySQL server.
// Firstly, download and install MySQL, create the database in MySQL from the queries of database.sql file.
// Then, use the password you choose while installing MySQL.
package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/restaurant_management";
    private static final String USER = "root";          // Change if your username is different
    private static final String PASSWORD = "your_pass"; // Change this to your DB password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
