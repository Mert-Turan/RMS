package View;

import Controller.Controller;

public interface MenuViewInterface {
    void show();
    void updateView(Object data);
    void setController(Controller controller);
    void reset();
    String getViewName();
}