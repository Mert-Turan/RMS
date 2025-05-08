package View;

import Controller.Controller;
import Controller.MenuController;
import Model.MenuModel;
import Model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MenuView extends View implements MenuViewInterface {
    private Controller controller;
    private final JFrame frame;
    private final JPanel mainPanel;
    private final CardLayout cardLayout;
    private final DefaultListModel<String> menuListModel;
    private JList<String> menuList;

    public MenuView() {
        frame = new JFrame("Menu Management");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        menuListModel = new DefaultListModel<>();
        setupMenuScreen();

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private void setupMenuScreen() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("Menu List", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(title, BorderLayout.NORTH);

        menuList = new JList<>(menuListModel);
        JScrollPane scrollPane = new JScrollPane(menuList);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton orderButton = new JButton("Order");
        JButton backButton = new JButton("Back");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.add(orderButton);
        buttonPanel.add(backButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(panel, "menu");

        orderButton.addActionListener(e -> {
            int selectedIndex = menuList.getSelectedIndex();
            if (selectedIndex != -1 && controller instanceof MenuController menuController) {
                menuController.placeOrder(selectedIndex);
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a menu item to order.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "initial"));
    }

    @Override
    public void show() {
        frame.setVisible(true);
    }

    @Override
    public void updateView(Object data) {
        if (data instanceof List<?> menus) {
            menuListModel.clear();
            for (Object menu : menus) {
                if (menu instanceof MenuModel menuModel) {
                    menuListModel.addElement(menuModel.getName() + " - $" + menuModel.getPrice());
                }
            }
        }
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void reset() {
        menuListModel.clear();
    }

    @Override
    public String getViewName() {
        return "MenuView";
    }

    public JFrame getFrame() {
        return frame;
    }

    @Override
    public void setUser(User user) {
        // If you're not using user data, this can stay empty
        // Or you can store it like:
        // this.user = user;
    }

}