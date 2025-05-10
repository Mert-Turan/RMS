package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderModel {

    public List<String[]> getAllOrders(Connection conn) {
        List<String[]> orders = new ArrayList<>();
        String query = """
            SELECT o.orderID,
                   (SELECT name FROM Menu WHERE menuID = o.menuID) AS menuName,
                   o.status,
                   (SELECT tableName FROM Tables WHERE tableID = (
                       SELECT tableID FROM TableSlots WHERE slotID = (
                           SELECT slotID FROM Reservation WHERE reservationID = o.reservationID
                       )
                   )) AS tableName
            FROM Orders o
        """;

        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String[] row = new String[4];
                row[0] = String.valueOf(rs.getInt("orderID"));
                row[1] = rs.getString("menuName");
                row[2] = rs.getString("status");
                row[3] = rs.getString("tableName");
                orders.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public boolean updateOrderStatus(int orderID, String newStatus, Connection conn) {
        String query = "UPDATE Orders SET status = ? WHERE orderID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, orderID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
