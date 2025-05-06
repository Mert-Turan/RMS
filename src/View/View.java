package View;

import Model.User;
import Controller.Controller;

public abstract class View implements ViewInterface {
    protected String viewTitle;
    protected User currentUser;
    protected Controller controller;

    public View() {
        this.viewTitle = "";
    }

    public View(String viewTitle) {
        this.viewTitle = viewTitle;
    }

    @Override
    public void setUser(User user) {
        this.currentUser = user;
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void renderHeader() {
        System.out.println("=== " + viewTitle + " ===");
    }
}
