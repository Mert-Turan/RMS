package Model;

public class Order {
    private int orderID;
    private int reservationID;
    private int menuID;
    private String status;

    public Order(int orderID, int reservationID, int menuID, String status) {
        this.orderID = orderID;
        this.reservationID = reservationID;
        this.menuID = menuID;
        this.status = status;
    }


    public int getOrderID() { return orderID; }
    public int getReservationID() { return reservationID; }
    public int getMenuID() { return menuID; }
    public String getStatus() { return status; }
}
