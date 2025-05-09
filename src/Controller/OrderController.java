package Controller;

import Model.Order;
import Model.OrderModel;

import java.sql.Connection;
import java.util.List;

public class OrderController extends Controller {
    private final Connection connection;
    private final OrderModel orderModel;

    public OrderController(Connection connection) {
        super(null);
        this.connection = connection;
        this.orderModel = new OrderModel();
    }

    public boolean addOrder(Order order) {
        return orderModel.addOrder(order, connection);
    }

    public List<Order> getOrdersByUser(int userID) {
        return orderModel.getOrdersByUser(userID, connection);
    }

    public List<Order> getAllOrders() {
        return orderModel.getAllOrders(connection);
    }

    public boolean updateOrderStatus(int orderID, String newStatus) {
        return orderModel.updateOrderStatus(orderID, newStatus, connection);
    }

    @Override
    public void handleLogin(String username, String password, String role) {
        throw new UnsupportedOperationException("Login is not supported in OrderController");
    }
}
