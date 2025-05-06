package Controller;

import Model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderController extends Controller {
    private final Connection connection;

    public OrderController(Connection connection) {
        super(null); // OrderController bir view ile ilişkilendirilmediği için null geçiyoruz
        this.connection = connection;
    }

    public boolean addOrder(Order order) {
        String query = "INSERT INTO orders (booking_id, menu_id, status) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, order.getBookingID());
            stmt.setInt(2, order.getMenuID());
            stmt.setString(3, order.getStatus());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void handleLogin(String username, String password, String role) {
        throw new UnsupportedOperationException("handleLogin is not supported in OrderController");
    }
}