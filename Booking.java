package Model;

public class Booking {
    private int bookingID;
    private int customerID;
    private int slotID;
    private int menuID;
    private double paymentAmount;
    private boolean isPaid;
    private String status;

    public Booking(int bookingID, int customerID, int slotID, int menuID, double paymentAmount, boolean isPaid, String status) {
        this.bookingID = bookingID;
        this.customerID = customerID;
        this.slotID = slotID;
        this.menuID = menuID;
        this.paymentAmount = paymentAmount;
        this.isPaid = isPaid;
        this.status = status;
    }

    public int getBookingID() { return bookingID; }
    public int getCustomerID() { return customerID; }
    public int getSlotID() { return slotID; }
    public int getMenuID() { return menuID; }
    public double getPaymentAmount() { return paymentAmount; }
    public boolean isPaid() { return isPaid; }
    public String getStatus() { return status; }
}

