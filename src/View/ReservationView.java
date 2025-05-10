package View;

import Model.User;

import javax.swing.*;
import java.awt.*;

public class ReservationView extends View implements ViewInterface {
    private final JFrame frame;
    private final JComboBox<String> tableSlotComboBox;
    private final JButton reserveButton;
    private final JButton viewReservationsButton;


    public ReservationView() {
        super("Make Reservation");

        frame = new JFrame(viewTitle);
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(2, 1));
        tableSlotComboBox = new JComboBox<>();
        reserveButton = new JButton("Reserve");
        viewReservationsButton = new JButton("View My Reservations");

        panel.add(new JLabel("Select Table & Time Slot:"));
        panel.add(tableSlotComboBox);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(reserveButton);
        buttonPanel.add(viewReservationsButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(panel, BorderLayout.CENTER);

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
        if (data instanceof String) {
            JOptionPane.showMessageDialog(frame, (String) data);
        }
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

    @Override
    public void close() {
        frame.setVisible(false);
    }

    public JButton getViewReservationsButton() {
        return viewReservationsButton;
    }


}
