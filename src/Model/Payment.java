package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Payment {
    public List<Order> fetchOrders(Connection conn) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM Orders";

        try (PreparedStatement stmt = conn.prepareStatement(query);
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

    public List<Reservation> fetchReservations(Connection conn) {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM Reservation";

        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                reservations.add(new Reservation(
                        rs.getInt("reservationID"),
                        rs.getInt("customerID"),
                        rs.getInt("slotID"),
                        rs.getInt("menuID"),
                        rs.getBigDecimal("paymentAmount"),
                        rs.getBoolean("isPaid"),
                        rs.getString("status")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservations;
    }

    public boolean takePayment(int reservationID, Connection conn) {
        String sql = "UPDATE Reservation SET isPaid = TRUE WHERE reservationID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, reservationID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void closeReservation(int reservationID, Connection conn) {
        String sql = "UPDATE Reservation SET status = 'closed' WHERE reservationID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, reservationID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isReservationPaid(int reservationID, Connection conn) {
        try {
            // Check if the reservation is paid
            String reservationQuery = "SELECT isPaid FROM Reservation WHERE reservationID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(reservationQuery)) {
                stmt.setInt(1, reservationID);
                ResultSet rs = stmt.executeQuery();
                if (!rs.next() || !rs.getBoolean("isPaid")) return false;
            }
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
