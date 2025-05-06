import Controller.LoginController;
import View.LoginView;

public class Main {
    public static void main(String[] args) {
        LoginView loginView = new LoginView();
        LoginController controller = new LoginController(loginView);
        loginView.show();
    }
}
