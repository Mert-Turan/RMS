package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuModel {

    public List<String[]> getMenus(Connection conn) {
        List<String[]> menus = new ArrayList<>();
        String sql = "SELECT menuID, name, price FROM Menu";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                menus.add(new String[]{
                        String.valueOf(rs.getInt("menuID")),
                        rs.getString("name"),
                        rs.getString("price")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return menus;
    }

    public int findUserIDByPassword(String password, Connection conn) {
        String query = "SELECT userID FROM Users WHERE password = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("userID");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getLatestReservationIDByCustomerID(int customerID, Connection conn) {
        String query = "SELECT reservationID FROM Reservation WHERE customerID = ? ORDER BY reservationID DESC LIMIT 1";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, customerID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("reservationID");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean updateReservationMenu(int reservationID, int menuID, Connection conn) {
        String sql = "UPDATE Reservation SET menuID = ? WHERE reservationID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, menuID);
            stmt.setInt(2, reservationID);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Failed to update reservation: " + e.getMessage());
            return false;
        }
    }
}