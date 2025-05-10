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
        view.setController(this); 
    }
     @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "order" -> handleOrderAction();
            case "back" -> handleBackAction();
        }
    }
     private void handleOrderAction() {
        MenuView menuView = (MenuView) view;
        int selectedIndex = menuView.menuList.getSelectedIndex();
        if (selectedIndex != -1 && selectedIndex < model.getMenus(connection).size()) {
            updateReservation(menuView.getReservationID(), selectedIndex); // Update reservation
        } else {
            JOptionPane.showMessageDialog(menuView.getFrame(),
                    "Please select a proper menu item to order.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    public void updateReservation(int reservationID, int menuID) {
        boolean isUpdated = model.updateMenuForReservation(reservationID, menuID, connection);

        if (isUpdated) {
            JOptionPane.showMessageDialog(((MenuView) view).getFrame(),
                    "Reservation updated successfully.",
                    "Update Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(((MenuView) view).getFrame(),
                    "Failed to update reservation.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    private void handleBackAction() {
        // Handle back action
        // For example, you can navigate to the previous view or close the current view
        JOptionPane.showMessageDialog(((MenuView) view).getFrame(),
                "Back button clicked.",
                "Back",
                JOptionPane.INFORMATION_MESSAGE);
    }
    @Override
    public void handleLogin(String username, String password, String role) {
        throw new UnsupportedOperationException("handleLogin is not supported in MenuController");
    }

    public void loadMenus() {
        List<MenuModel> menus = model.getMenus(connection);
        ((MenuView) view).updateView(menus); // view nesnesini MenuView'e cast ederek kullanıyoruz
    }

    public void updateReservation(int reservationID, int menuID) {
        // Call the model to update the reservation in the database
        boolean isUpdated = model.updateMenuForReservation(reservationID, menuID, connection);

        // Check is it updated successfully
        // and show a message to the user
        if (isUpdated) {
            JOptionPane.showMessageDialog(((MenuView) view).getFrame(),
                    "Reservation updated successfully.",
                    "Update Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(((MenuView) view).getFrame(),
                    "Failed to update reservation.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // public void placeOrder(int menuIndex) {
    //     List<MenuModel> menus = model.getMenus(connection);
    //     if (menuIndex >= 0 && menuIndex < menus.size()) {
    //         MenuModel selectedMenu = menus.get(menuIndex);

    //         // Yeni bir Order nesnesi oluşturuluyor
    //         Order order = new Order(0, 0, selectedMenu.getMenuID(), "ordered"); //ordered in database?
    //         //0 for orderID, 0 for reservationID, 
    //         //database will auto increment orderID, reservationID

    //         // OrderController kullanılarak sipariş veritabanına ekleniyor
    //         OrderController orderController = new OrderController(connection);
    //         boolean isAdded = orderController.addOrder(order,connection); //2 input
    //         //Check if the order was added successfully
    //         if (isAdded) {
    //             System.out.println("Order added to database: Menu ID = " + order.getMenuID() + ", Status = " + order.getStatus());
    //             JOptionPane.showMessageDialog(((MenuView) view).getFrame(),
    //                     "Order placed for: " + selectedMenu.getName(),
    //                     "Order Success",
    //                     JOptionPane.INFORMATION_MESSAGE);
    //         } else {
    //             JOptionPane.showMessageDialog(((MenuView) view).getFrame(),
    //                     "Failed to add order to database.",
    //                     "Error",
    //                     JOptionPane.ERROR_MESSAGE);
    //         }
    //     } else {
    //         JOptionPane.showMessageDialog(((MenuView) view).getFrame(),
    //                 "Invalid menu selection.",
    //                 "Error",
    //                 JOptionPane.ERROR_MESSAGE);
    //     }
    // }
}