package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationModel {



    public List<String[]> getTableDetails(Connection conn) {
        List<String[]> result = new ArrayList<>();

        String sql = """
        SELECT
            ts.slotID,
            (SELECT tableName FROM Tables WHERE tableID = ts.tableID) AS tableName,
            (SELECT capacity FROM Tables WHERE tableID = ts.tableID) AS capacity,
            (SELECT view FROM Tables WHERE tableID = ts.tableID) AS view,
            ts.slotType,
            ts.isAvailable
        FROM TableSlots ts
    """;

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String[] row = new String[6];
                row[0] = String.valueOf(rs.getInt("slotID"));
                row[1] = rs.getString("tableName");
                row[2] = String.valueOf(rs.getInt("capacity"));
                row[3] = rs.getString("view");
                row[4] = rs.getString("slotType");
                row[5] = rs.getBoolean("isAvailable") ? "true" : "false";
                result.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }


    public boolean makeReservation(String password, int slotID, Connection conn) {
        String sql = "INSERT INTO Reservation (customerID, slotID) VALUES (?, ?)";

        try {

            int userID = findUserIDByPassword(password, conn);
            if (userID == -1) {
                System.out.println("User not found for this password.");
                return false;
            }


            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userID);
                stmt.setInt(2, slotID);
                stmt.executeUpdate();
                return true;
            }

        } catch (SQLException e) {
            if (e.getMessage().contains("This time slot is not available")) {
                System.out.println("Error: Slot not available");
            } else {
                System.out.println("SQL Error: " + e.getMessage());
            }
            return false;
        }
    }

    private int findUserIDByPassword(String password, Connection conn) {
        String query = "SELECT userID FROM Users WHERE password = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, Integer.parseInt(password));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("userID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }


    public List<String[]> viewMyReservations(String password, Connection conn) {
        List<String[]> reservations = new ArrayList<>();


        int userID = findUserIDByPassword(password, conn);
        if (userID == -1) {
            System.out.println("User not found for this password.");
            return reservations; // Empty
        }


        String sql = """
        SELECT
            reservationID,
            (SELECT tableName FROM Tables WHERE tableID = (
                SELECT tableID FROM TableSlots WHERE slotID = b.slotID
            )) AS tableName,
            (SELECT slotType FROM TableSlots WHERE slotID = b.slotID) AS slotType,
            (SELECT name FROM Menu WHERE menuID = b.menuID) AS menuName,
            paymentAmount,
            isPaid,
            status
        FROM Reservation b
        WHERE customerID = ?
    """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String[] row = new String[7];
                row[0] = String.valueOf(rs.getInt("reservationID"));
                row[1] = rs.getString("tableName");
                row[2] = rs.getString("slotType");
                row[3] = rs.getString("menuName");
                row[4] = rs.getString("paymentAmount");
                row[5] = rs.getBoolean("isPaid") ? "Paid" : "Unpaid";
                row[6] = rs.getString("status");
                reservations.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservations;
    }

}










