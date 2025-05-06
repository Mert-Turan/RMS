package Model;

public class Order {
    private int orderID;
    private int bookingID;
    private int menuID;
    private String status;

    public Order(int orderID, int bookingID, int menuID, String status) {
        this.orderID = orderID;
        this.bookingID = bookingID;
        this.menuID = menuID;
        this.status = status;
    }


    public int getOrderID() { return orderID; }
    public int getBookingID() { return bookingID; }
    public int getMenuID() { return menuID; }
    public String getStatus() { return status; }
}
