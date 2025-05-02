package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuModel {
    private int menuID;
    private String name;
    private double price;

    public MenuModel(int menuID, String name, double price) {
        this.menuID = menuID;
        this.name = name;
        this.price = price;
    }



    public List<MenuModel> getMenus(Connection conn) {
        List<MenuModel> menus = new ArrayList<>();
        String sql = "SELECT menuID, name, price FROM Menu";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                menus.add(new MenuModel(
                        rs.getInt("menuID"),
                        rs.getString("name"),
                        rs.getDouble("price")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return menus;
    }



    //after customer choosing menu update booking infos
    public boolean updateMenuForBooking(int bookingID, int menuID, Connection conn) {
        String sql = """
        UPDATE Bookings
        SET menuID = ?, 
            paymentAmount = (SELECT price FROM Menu WHERE menuID = ?)
        WHERE bookingID = ?
    """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, menuID);
            stmt.setInt(2, menuID);
            stmt.setInt(3, bookingID);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("error updating menu: " + e.getMessage());
            return false;
        }
    }




    public int getMenuID() {
        return menuID;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }


}
