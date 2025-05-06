package Controller;

import Model.User;
import View.LoginView;

public class LoginController extends Controller {

    public LoginController(LoginView view) {
        super(view);
    }

    public void handleLogin(String fullName, String password, String role) {
        if (fullName.isEmpty() || password.isEmpty()) {
            view.updateView("You have to write a username and password!");
            return;
        }

        User user = User.authenticate(fullName, password, role);
        if (user != null) {
            setUser(user);
            view.updateView("Login successful!");
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
