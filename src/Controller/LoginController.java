package Controller;

import Model.DBConnection;
import Model.OrderModel;
import Model.ReservationModel;
import Model.User;
import View.LoginView;
import View.OrderView;
import View.ReservationView;

import java.sql.Connection;
import java.sql.SQLException;

public class LoginController extends Controller {

    public LoginController(LoginView view) {
        super(view);
        this.view = view;
    }

    public void handleLogin(String fullName, String password, String role) throws SQLException {
        if (fullName.isEmpty() || password.isEmpty()) {
            view.updateView("You have to write a username and password!");
            return;
        }

        User user = User.authenticate(fullName, password, role);
        if (user != null) {
            setUser(user);
            view.updateView("Login successful!");

            if ("customer".equalsIgnoreCase(role)) {
                ReservationView reservationView = new ReservationView();
                ReservationModel reservationModel = new ReservationModel();
                Connection conn = DBConnection.getConnection();

                ReservationController reservationController = new ReservationController(
                        reservationView, conn, reservationModel
                );
                reservationController.setUser(user); // propagate user if needed

                reservationView.show(); // open reservation screen
                view.close();           // hide login screen (optional)
            } else if ("waiter".equalsIgnoreCase(role)) {
                OrderView orderView = new OrderView();
                Connection conn = DBConnection.getConnection();

                OrderController orderController = new OrderController(orderView, conn);
                orderController.setUser(user);
                orderView.show();
                view.close();
            }

        } else {
            view.updateView("You have to sign up before login!");
        }
    }

    public void handleSignUp(String fullName, String password, String role) {
        if (fullName.isEmpty() || password.isEmpty()) {
            view.updateView("You have to write a username and password!");
            return;
        }

        if (User.usernameExists(fullName)) {
            view.updateView("This username is already taken!");
        } else {
            boolean success = User.registerNewUser(fullName, password, role);
            if (success) {
                view.updateView("You have signed up successfully!");
            } else {
                view.updateView("Sign up failed due to a system error.");
            }
        }
    }
}
