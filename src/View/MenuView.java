package View;

import Model.User;

import javax.swing.*;
import java.awt.*;

public class MenuView extends View implements ViewInterface {
    private final JFrame frame;
    private final JComboBox<String> menuBox;
    private final JButton selectButton;

    public MenuView() {
        super("Select a Menu");

        frame = new JFrame(viewTitle);
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(2, 1));
        menuBox = new JComboBox<>();
        selectButton = new JButton("Select Menu");

        panel.add(new JLabel("Select a Menu:"));
        panel.add(menuBox);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(selectButton, BorderLayout.SOUTH);
    }

    // === Getters for Controller ===

    public JComboBox<String> getMenuBox() {
        return menuBox;
    }

    public JButton getSelectButton() {
        return selectButton;
    }

    public String getSelectedMenuID() {
        String selected = (String) menuBox.getSelectedItem();
        if (selected != null && selected.contains(" - ")) {
            return selected.split(" - ")[0]; // Assuming format: "menuID - Menu Name"
        }
        return null;
    }

    public void addMenuItem(String item) {
        menuBox.addItem(item);
    }


    // === ViewInterface Methods ===

    @Override
    public void show() {
        frame.setVisible(true);
    }

    @Override
    public void updateView(Object data) {
        if (data instanceof String) {
            JOptionPane.showMessageDialog(frame, (String) data);
        }
    }

    @Override
    public void reset() {
        menuBox.removeAllItems();
    }

    @Override
    public String getViewName() {
        return "MenuView";
    }

    @Override
    public void setUser(User user) {
        // You can store it if needed: this.user = user;
    }

    @Override
    public void close() {
        frame.setVisible(false);
    }
}