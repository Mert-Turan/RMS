package View;

import Model.User;
import Controller.Controller;

public interface ViewInterface {
    void show();
    void updateView(Object data);
    void setUser(User client);
    void setController(Controller controller);
    void reset();
    String getViewName();
    void close();
}
