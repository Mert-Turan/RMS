package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationModel {


    //view table information
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





    public boolean makeReservation(int customerID, int slotID, Connection conn) {
            String sql = "INSERT INTO Reservation (customerID, slotID) VALUES (?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerID);
            stmt.setInt(2, slotID);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            if (e.getMessage().contains("This time slot is not available")) {
                System.out.println("error: slot not available");
            } else {
                System.out.println("error: " + e.getMessage());
            }
            return false;
        }
    }









}




