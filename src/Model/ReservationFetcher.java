// This class is added to fetch the reservations from the Reservation table
// to test the insertion/deletion functionalities of the code (both Java and SQL)
package Model;

import java.sql.*;

public class ReservationFetcher {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/restaurant_management";
        String user = "root";           // your DB user
        String password = "127856tkn";  // your DB password

        String query = "SELECT * FROM Reservation";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int reservationID = rs.getInt("reservationID");
                int customerID = rs.getInt("customerID");
                int slotID = rs.getInt("slotID");
                Integer menuID = rs.getObject("menuID") != null ? rs.getInt("menuID") : null;
                Double paymentAmount = rs.getObject("paymentAmount") != null ? rs.getDouble("paymentAmount") : null;
                boolean isPaid = rs.getBoolean("isPaid");
                String status = rs.getString("status");

                System.out.println("ReservationID: " + reservationID +
                        ", CustomerID: " + customerID +
                        ", SlotID: " + slotID +
                        ", MenuID: " + (menuID != null ? menuID : "N/A") +
                        ", Payment: " + (paymentAmount != null ? paymentAmount : "N/A") +
                        ", Paid: " + isPaid +
                        ", Status: " + status);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

