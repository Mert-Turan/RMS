package View;

public class FakeLoginView implements ILoginView {
    public void updateView(String message) {
        System.out.println("TEST MESSAGE: " + message);
    }
}
