package View;

import javax.swing.*;
import java.awt.*;

public class OrderView extends View implements ViewInterface {
    private final JFrame frame;
    private final DefaultListModel<String> listModel;
    private final JList<String> orderList;
    private final JButton updateButton;
    private final JComboBox<String> statusBox;

    public OrderView() {
        frame = new JFrame("Orders");
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        listModel = new DefaultListModel<>();
        orderList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(orderList);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        statusBox = new JComboBox<>(new String[]{"preparing", "ready", "delivered"});
        updateButton = new JButton("Update Status");
        bottomPanel.add(statusBox);
        bottomPanel.add(updateButton);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
    }

    @Override
    public void close() {
        //
    }

    public void show() {
        frame.setVisible(true);
    }

    public void setOrders(java.util.List<String[]> orders) {
        listModel.clear();
        for (String[] order : orders) {
            listModel.addElement("ID: " + order[0] + " | Menu: " + order[1] + " | Status: " + order[2] + " | Table: " + order[3]);
        }
    }

    public JList<String> getOrderList() {
        return orderList;
    }

    public JComboBox<String> getStatusBox() {
        return statusBox;
    }

    public JButton getUpdateButton() {
        return updateButton;
    }

    public JFrame getFrame() {
        return frame;
    }


    @Override public void updateView(Object data) {}
    @Override public void reset() {}
    @Override public String getViewName() { return "OrderView"; }
}
