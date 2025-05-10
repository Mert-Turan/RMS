package Controller;

import Model.ReservationModel;
import Model.User;
import View.LoginView;
import View.ReservationView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.List;

public class ReservationController extends Controller implements ControllerInterface {

    private final Connection conn;
    private final ReservationModel model;
    private User loggedInUser;
    private LoginView loginView;
    private ReservationView reservationView;

    public ReservationController(ReservationView reservationView, Connection conn,  ReservationModel model) {
        super(reservationView);
        this.conn = conn;
        this.model = new ReservationModel();
        this.setUser(user);
        this.reservationView = reservationView;
        setupView();
        addListeners();
    }

    private void setupView() {
        ReservationView rv = (ReservationView) view;
        List<String[]> tableDetails = model.getTableDetails(conn);
        JComboBox<String> comboBox = rv.getTableSlotComboBox();

        for (String[] row : tableDetails) {
            String item = "SlotID: " + row[0] + " | Table: " + row[1] + " | Capacity: " + row[2] +
                    " | View: " + row[3] + " | Time: " + row[4] + " | Available: " + row[5];
            comboBox.addItem(item);
        }

        rv.show();

        System.out.println("Slot List:");
        for (String[] row : model.getTableDetails(conn)) {
            System.out.println(String.join(" | ", row));
        }

    }

    private void addListeners() {
        ((ReservationView) view).getReserveButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReservationView rv = (ReservationView) view;
                String selected = (String) rv.getTableSlotComboBox().getSelectedItem();
                if (selected == null || !selected.contains("SlotID:")) {
                    view.updateView("Please select a valid slot!");
                    return;
                }

                int slotID = Integer.parseInt(selected.split("SlotID: ")[1].split(" ")[0]);
                //String customerPassword= loginView.Password();
                String customerPassword = loggedInUser.getUserPassword();

                boolean success = model.makeReservation(customerPassword, slotID, conn);
                if (success) {
                    view.updateView("Reservation successful!");
                    //rv.setVisible(false);
                } else {
                    view.updateView("Reservation failed. Slot may not be available.");
                }
            }
        });



        ((ReservationView) view).getViewReservationsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //String customerPassword= loginView.Password(); after login connection this will be used to get password
                String password = loggedInUser.getUserPassword();

                List<String[]> reservations = model.viewMyReservations(password, conn);
                if (reservations.isEmpty()) {
                    view.updateView("No reservations found.");
                    return;
                }

                StringBuilder message = new StringBuilder("Your Reservations:\n\n");
                for (String[] row : reservations) {
                    message.append("Reservation ID: ").append(row[0])
                            .append("\nTable: ").append(row[1])
                            .append("\nTime: ").append(row[2])
                            .append("\nMenu: ").append(row[3])
                            .append("\nAmount: ").append(row[4])
                            .append("\nPaid: ").append(row[5])
                            .append("\nStatus: ").append(row[6])
                            .append("\n\n");
                }

                JOptionPane.showMessageDialog(null, message.toString(), "Your Reservations", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    @Override
    public void handleLogin(String fullName, String password, String role) {
        // Not needed for ReservationController
    }

    @Override
    public void setUser(User user) {
        this.loggedInUser = user;
    }
}














