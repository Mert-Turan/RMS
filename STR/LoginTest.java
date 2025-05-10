import Controller.LoginController;
import View.FakeLoginView;
import View.ILoginView;
import Controller.LoginController;


/**
 * test class to simulate the login process for STR testing,
 * using FakeLoginView to testin without GUI.
 * it aimed to make tests while the GUI codes are not correct or ready.
 */
public class LoginTest {
    public static void main(String[] args) throws Exception {
        ILoginView view = new FakeLoginView();
        LoginController controller = new LoginController(view);

        System.out.println("Step 1: Entering userID");
        String enteredUserID = "Elifnur123";
        System.out.println("Numbers are written: " + enteredUserID);

        System.out.println("\nStep 2: Entering existing ID and clicking enter");
        controller.handleLogin("Elifnur", "1234", "customer");

        System.out.println("\nStep 3: Entering nonexistent ID and clicking enter");
        controller.handleLogin("Yok", "abcd", "customer");
    }
}
