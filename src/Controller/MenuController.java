package Controller;

import Model.MenuModel;
import View.MenuView;

import javax.swing.*;
import java.sql.Connection;
import java.util.List;

public class MenuController extends Controller {
    private final MenuModel model;
    private final MenuView view;
    private final Connection conn;
    private final String userPassword;

    public MenuController(MenuView view, Connection conn, String userPassword) {
        super(view);
        this.model = new MenuModel();
        this.view = view;
        this.conn = conn;
        this.userPassword = userPassword;

        populateMenuList();
        addListeners();
        view.show();
    }

    private void populateMenuList() {
        List<String[]> menus = model.getMenus(conn);
        for (String[] row : menus) {
            view.addMenuItem(row[0] + " - " + row[1] + " ($" + row[2] + ")");
        }
    }

    private void addListeners() {
        view.getSelectButton().addActionListener(e -> {
            try {
                int menuID = Integer.parseInt(view.getSelectedMenuID());
                int userID = model.findUserIDByPassword(userPassword, conn);
                int reservationID = model.getLatestReservationIDByCustomerID(userID, conn);

                if (reservationID == -1) {
                    view.updateView("No reservation found to update.");
                    return;
                }

                boolean success = model.updateReservationMenu(reservationID, menuID, conn);
                view.updateView(success ? "✅ Menu selected and order created!" : "❌ Failed to select menu.");
            } catch (Exception ex) {
                view.updateView("Invalid selection or internal error.");
                ex.printStackTrace();
            }
        });
    }

    @Override
    public void handleLogin(String fullName, String password, String role){}
}