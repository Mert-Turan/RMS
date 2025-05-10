package Model;

import java.math.BigDecimal;

public class Reservation {
    private int reservationID;
    private int customerID;
    private int slotID;
    private int menuID;
    private BigDecimal paymentAmount;
    private boolean isPaid;
    private String status;

    public Reservation(int reservationID, int customerID, int slotID, int menuID, BigDecimal paymentAmount, boolean isPaid, String status) {
        this.reservationID = reservationID;
        this.customerID = customerID;
        this.slotID = slotID;
        this.menuID = menuID;
        this.paymentAmount = paymentAmount;
        this.isPaid = isPaid;
        this.status = status;
    }

    public int getReservationID() {
        return reservationID;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public int getCustomerID() {
        return customerID;
    }

    public int getSlotID() {
        return slotID;
    }

    public int getMenuID() {
        return menuID;
    }

    public String getStatus() {
        return status;
    }

    public boolean isPaid() {
        return isPaid;
    }
}
