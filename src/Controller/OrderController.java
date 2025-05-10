package Controller;

import Model.OrderModel;
import View.OrderView;

import javax.swing.*;
import java.sql.Connection;

public class OrderController extends Controller {
    private final OrderModel model;
    private final Connection conn;
    private final OrderView orderView;

    public OrderController(OrderView view, Connection conn) {
        super(view);
        this.conn = conn;
        this.model = new OrderModel();
        this.orderView = view;

        setupOrders();
        addListeners();
    }

    private void setupOrders() {
        OrderView ov = (OrderView) view;
        ov.setOrders(model.getAllOrders(conn));
        ov.show();
    }

    private void addListeners() {
        orderView.getUpdateButton().addActionListener(e -> {
            int selectedIndex = orderView.getOrderList().getSelectedIndex();
            if (selectedIndex == -1) {
                JOptionPane.showMessageDialog(orderView.getFrame(), "Please select an order to update.");
                return;
            }

            String selectedValue = orderView.getOrderList().getSelectedValue();
            int orderID = Integer.parseInt(selectedValue.split(" ")[1]);

            String newStatus = (String) orderView.getStatusBox().getSelectedItem();
            boolean success = model.updateOrderStatus(orderID, newStatus, conn);

            if (success) {
                JOptionPane.showMessageDialog(orderView.getFrame(), "✅ Order status updated successfully!");
                setupOrders(); // Refresh the list
            } else {
                JOptionPane.showMessageDialog(orderView.getFrame(), "❌ Failed to update order status.");
            }
        });
    }

    @Override
    public void handleLogin(String fullName, String password, String role) {
        // Not needed in OrderController
    }
}
