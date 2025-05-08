package View;

import Model.User;

import javax.swing.*;
import java.awt.*;

public class ReservationView extends View {
    private JFrame frame;
    private JComboBox<String> tableSlotComboBox;
    private JButton reserveButton;

    public ReservationView() {
        super("Make Reservation");

        frame = new JFrame(viewTitle);
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(2, 1));
        tableSlotComboBox = new JComboBox<>();
        reserveButton = new JButton("Reserve");

        panel.add(new JLabel("Select Table & Time Slot:"));
        panel.add(tableSlotComboBox);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(reserveButton, BorderLayout.SOUTH);
    }

    public JComboBox<String> getTableSlotComboBox() {
        return tableSlotComboBox;
    }

    public JButton getReserveButton() {
        return reserveButton;
    }

    // === ViewInterface Methods ===

    @Override
    public void show() {
        frame.setVisible(true);
    }

    @Override
    public void updateView(Object data) {
        // Optionally update view based on model data
    }

    @Override
    public void reset() {
        tableSlotComboBox.removeAllItems();
    }

    @Override
    public String getViewName() {
        return "ReservationView";
    }
    @Override
    public void setUser(User user) {
        // If you're not using user data, this can stay empty
        // Or you can store it like:
        // this.user = user;
    }

}