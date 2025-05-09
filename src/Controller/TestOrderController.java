package Controller;

import Model.DBConnection;
import Model.Order;

import java.sql.Connection;
import java.util.List;

public class TestOrderController {
    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection()) {
            OrderController controller = new OrderController(conn);

            // Yeni sipariş ekle
            Order order = new Order(0, 1, 2, "Pending");
            controller.addOrder(order);

            // Tüm siparişleri print et
            List<Order> orders = controller.getAllOrders();
            for (Order o : orders) {
                System.out.println("Order ID: " + o.getOrderID() + " | Status: " + o.getStatus());
            }

            // Sipariş durumu güncelle (ID 1 örnek)
            controller.updateOrderStatus(1, "Done");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
