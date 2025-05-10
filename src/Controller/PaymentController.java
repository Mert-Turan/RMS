package Controller;

import Model.Payment;
import Model.Order;
import Model.Reservation;
import View.PaymentView;
import java.sql.Connection;

public class PaymentController extends Controller implements ControllerInterface {
    private final Payment model;
    private final PaymentView paymentView;
    private final Connection conn;

    public PaymentController(PaymentView view, Payment model, Connection conn) {
        super(view);
        this.model = model;
        this.paymentView = view;
        this.conn = conn;

        view.setController(this);
        initListeners();
        loadData();
    }

    private void initListeners() {
        paymentView.getTakePaymentButton().addActionListener(e -> {
            Order selected = paymentView.getSelectedOrder();
            if (selected != null && selected.getStatus().equalsIgnoreCase("delivered")) {
                if (model.takePayment(selected.getReservationID(), conn)) {
                    view.updateView("Payment successful!");
                    loadData();
                } else {
                    view.updateView("Payment failed!");
                }
            } else {
                view.updateView("Reservation already paid or not selected!");
            }
        });

        paymentView.getCloseReservationButton().addActionListener(e -> {
            Reservation selected = paymentView.getSelectedReservation();
            if (selected != null) {
                if (model.isReservationPaid(selected.getReservationID(), conn)) {
                    model.closeReservation(selected.getReservationID(), conn);
                    view.updateView("Reservation closed!");
                    loadData();
                } else {
                    view.updateView("Cannot close unpaid or active orders.");
                }
            } else {
                view.updateView("No reservation selected.");
            }
        });
    }

    private void loadData() {
        var orders = model.fetchOrders(conn);
        var reservations = model.fetchReservations(conn);
        paymentView.setOrders(orders);
        paymentView.setReservations(reservations);
    }

    @Override
    public void handleLogin(String fullName, String password, String role) {
        // Not used here
    }
}
