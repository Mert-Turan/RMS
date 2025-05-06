package Controller;

import Model.MenuModel;
import Model.Order;
import View.MenuView;

import javax.swing.*;
import java.sql.Connection;
import java.util.List;

public class MenuController extends Controller {
    private final MenuModel model;
    private final Connection connection;

    public MenuController(MenuView view, MenuModel model, Connection connection) {
        super(view); // Controller sınıfının constructor'ına view nesnesini gönderiyoruz
        this.model = model;
        this.connection = connection;
    }

    @Override
    public void handleLogin(String username, String password, String role) {
        throw new UnsupportedOperationException("handleLogin is not supported in MenuController");
    }

    public void loadMenus() {
        List<MenuModel> menus = model.getMenus(connection);
        ((MenuView) view).updateView(menus); // view nesnesini MenuView'e cast ederek kullanıyoruz
    }

    public void placeOrder(int menuIndex) {
        List<MenuModel> menus = model.getMenus(connection);
        if (menuIndex >= 0 && menuIndex < menus.size()) {
            MenuModel selectedMenu = menus.get(menuIndex);

            // Yeni bir Order nesnesi oluşturuluyor
            Order order = new Order(0, 0, selectedMenu.getMenuID(), "Pending");

            // Veritabanında booking bilgilerini güncelle
            boolean isUpdated = model.updateMenuForBooking(order.getBookingID(), order.getMenuID(), connection);

            if (isUpdated) {
                // Sipariş bilgilerini konsola yazdır
                System.out.println("Order created: Menu ID = " + order.getMenuID() + ", Status = " + order.getStatus());

                // Kullanıcıya siparişin başarıyla oluşturulduğunu bildir
                JOptionPane.showMessageDialog(((MenuView) view).getFrame(),
                        "Order placed for: " + selectedMenu.getName(),
                        "Order Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(((MenuView) view).getFrame(),
                        "Failed to update booking information.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(((MenuView) view).getFrame(),
                    "Invalid menu selection.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}