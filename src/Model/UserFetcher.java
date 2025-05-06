// This class is added to fetch the customers/supervisors/waiters from the Users/Supervisors/Customers/Waiters tables
// to test the insertion/deletion functionalities of the code (both java and sql)
package Model;

import java.sql.*;

public class UserFetcher {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/restaurant_management";
        String user = "root";         // your DB user
        String password = "your_pass"; // your DB password

        String query = "SELECT * FROM Users";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int userId = rs.getInt("userID");
                String username = rs.getString("username");
                String pwd = rs.getString("password");

                System.out.println("UserID: " + userId + ", Username: " + username + ", Password: " + pwd);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
