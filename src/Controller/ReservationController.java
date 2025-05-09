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

public class ReservationController extends Controller {

    private final Connection conn;
    private final ReservationModel model;
    private LoginView loginView;

    public ReservationController(ReservationView reservationView, Connection conn, LoginView loginView) {
        super(reservationView);
        this.conn = conn;
        this.model = new ReservationModel();
        this.setUser(user);
        this.loginView = loginView;  // ✅ You must pass it from outside



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
                int customerID = loginView.getUserID();

                boolean success = model.makeReservation(customerID, slotID, conn);
                if (success) {
                    view.updateView("Reservation successful!");
                    //rv.setVisible(false);
                } else {
                    view.updateView("Reservation failed. Slot may not be available.");
                }
            }
        });
    }

    @Override
    public void handleLogin(String fullName, String password, String role) {
        // Not needed for ReservationController
    }


}