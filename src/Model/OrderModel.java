package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderModel {
    public boolean addOrder(Order order, Connection connection) {
        String query = "INSERT INTO Orders (reservationID, menuID, status) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, order.getReservationID());
            stmt.setInt(2, order.getMenuID());
            stmt.setString(3, order.getStatus());
            return stmt.executeUpdate() > 0;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Order> getOrdersByUser(int userID, Connection connection) {
        List<Order> orders = new ArrayList<>();
        String sql = """
            SELECT o.orderID, o.reservationID, o.menuID, o.status
            FROM Orders o
            JOIN Reservation r ON o.reservationID = r.reservationID
            WHERE r.customerID = ?
        """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                orders.add(new Order(
                        rs.getInt("orderID"),
                        rs.getInt("reservationID"),
                        rs.getInt("menuID"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public List<Order> getAllOrders(Connection connection) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM Orders";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                orders.add(new Order(
                        rs.getInt("orderID"),
                        rs.getInt("reservationID"),
                        rs.getInt("menuID"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public boolean updateOrderStatus(int orderID, String newStatus, Connection connection) {
        String sql = "UPDATE Orders SET status = ? WHERE orderID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, orderID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

