package View;

import Controller.Controller;
import Model.User;
import Model.Order;
import Model.Reservation;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PaymentView extends View implements ViewInterface {
    private final JFrame frame;
    private final JPanel mainPanel;
    private final JButton takePaymentButton;
    private final JButton closeReservationButton;
    private final JPanel ordersPanel;
    private final JPanel reservationsPanel;
    private JPanel selectedOrderPanel;
    private JPanel selectedReservationPanel;

    private List<Order> orders;
    private List<Reservation> reservations;
    private Order selectedOrder;
    private Reservation selectedReservation;

    public PaymentView() {
        super("Payment Management");

        frame = new JFrame(viewTitle);
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 1));

        ordersPanel = new JPanel();
        ordersPanel.setLayout(new BoxLayout(ordersPanel, BoxLayout.Y_AXIS));
        ordersPanel.setBorder(BorderFactory.createTitledBorder("Orders"));

        reservationsPanel = new JPanel();
        reservationsPanel.setLayout(new BoxLayout(reservationsPanel, BoxLayout.Y_AXIS));
        reservationsPanel.setBorder(BorderFactory.createTitledBorder("Reservations"));

        mainPanel.add(new JScrollPane(ordersPanel));
        mainPanel.add(new JScrollPane(reservationsPanel));

        takePaymentButton = new JButton("Take Payment");
        closeReservationButton = new JButton("Close Reservation");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.add(takePaymentButton);
        buttonPanel.add(closeReservationButton);

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void show() {
        frame.setVisible(true);
    }

    @Override
    public void updateView(Object data) {
        if (data instanceof List<?> list && !list.isEmpty()) {
            Object first = list.get(0);
            if (first instanceof Order) {
                setOrders((List<Order>) list);
            } else if (first instanceof Reservation) {
                setReservations((List<Reservation>) list);
            }
        }
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
        ordersPanel.removeAll();
        selectedOrder = null;
        selectedOrderPanel = null;

        for (Order order : orders) {
            JPanel itemPanel = new JPanel(new GridLayout(1, 1));
            itemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            itemPanel.setBackground("delivered".equalsIgnoreCase(order.getStatus()) ? Color.GREEN : Color.RED);

            JLabel label = new JLabel("OrderID: " + order.getOrderID() +
                    " | ReservationID: " + order.getReservationID() +
                    " | MenuID: " + order.getMenuID() +
                    " | Status: " + order.getStatus());
            itemPanel.add(label);

            itemPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    if (selectedOrderPanel != null) {
                        selectedOrderPanel.setBorder(null);
                    }
                    selectedOrder = order;
                    selectedOrderPanel = itemPanel;
                    itemPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
                }
            });

            ordersPanel.add(itemPanel);
        }

        ordersPanel.revalidate();
        ordersPanel.repaint();
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
        reservationsPanel.removeAll();
        selectedReservation = null;
        selectedReservationPanel = null;

        for (Reservation reservation : reservations) {
            JPanel itemPanel = new JPanel(new GridLayout(1, 1));
            itemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            itemPanel.setBackground(reservation.isPaid() ? Color.GREEN : Color.RED);

            JLabel label = new JLabel("ReservationID: " + reservation.getReservationID() +
                    " | CustomerID: " + reservation.getCustomerID() +
                    " | SlotID: " + reservation.getSlotID() +
                    " | MenuID: " + reservation.getMenuID() +
                    " | PaymentAmount: " + reservation.getPaymentAmount() +
                    " | Paid: " + reservation.isPaid() +
                    " | Status: " + reservation.getStatus());
            itemPanel.add(label);

            itemPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    if (selectedReservationPanel != null) {
                        selectedReservationPanel.setBorder(null);
                    }
                    selectedReservation = reservation;
                    selectedReservationPanel = itemPanel;
                    itemPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
                }
            });

            reservationsPanel.add(itemPanel);
        }

        reservationsPanel.revalidate();
        reservationsPanel.repaint();
    }


    public Order getSelectedOrder() {
        return selectedOrder;
    }

    public Reservation getSelectedReservation() {
        return selectedReservation;
    }

    @Override
    public void reset() {
        ordersPanel.removeAll();
        reservationsPanel.removeAll();
        ordersPanel.repaint();
        reservationsPanel.repaint();
    }

    @Override
    public String getViewName() {
        return "PaymentView";
    }

    @Override
    public void close() {
        frame.setVisible(false);
    }

    @Override
    public void setUser(User user) {
        this.currentUser = user;
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public JButton getTakePaymentButton() {
        return takePaymentButton;
    }

    public JButton getCloseReservationButton() {
        return closeReservationButton;
    }
}
