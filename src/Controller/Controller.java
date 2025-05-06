package Controller;

import Model.User;
import View.View;

public abstract class Controller implements ControllerInterface {
    protected View view;
    protected User user;

    public Controller(View view) {
        this.view = view;
        this.view.setController(this);
    }

    public void setUser(User user) {
        this.user = user;
        this.view.setUser(user);
    }
}
