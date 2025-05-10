package View;

import Controller.Controller;
import Controller.LoginController;
import Model.User;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class LoginView extends View implements ViewInterface {
    private String userId;
    private String password;
    private Controller controller;
    private final JFrame frame;
    private final JPanel mainPanel;
    private final CardLayout cardLayout;
    private final java.util.Map<String, JLabel> statusLabels = new java.util.HashMap<>();
    private String currentRoleKey = ""; // stores "customer", "waiter", "supervisor"

    public LoginView() {
        frame = new JFrame("Login");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        setupInitialScreen();
        createLoginScreen("customer", "customer");
        createLoginScreen("waiter", "waiter");
        createLoginScreen("supervisor", "supervisor");

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private void setupInitialScreen() {
        try {
            Image backgroundImage = new ImageIcon("src/images/restaurant-interior.jpg").getImage();

            BackgroundPanel panel = new BackgroundPanel(backgroundImage);
            panel.setLayout(new BorderLayout());

            JLabel title = new JLabel("Welcome to Restaurant Management System, please select the login type", SwingConstants.CENTER);
            title.setFont(new Font("Arial", Font.BOLD, 30));
            title.setForeground(Color.WHITE);
            panel.add(title, BorderLayout.NORTH);

            JButton customerButton = new JButton("Login as Customer");
            JButton waiterButton = new JButton("Login as Waiter");
            JButton supervisorButton = new JButton("Login as Supervisor");

            JPanel buttonPanel = new JPanel();
            buttonPanel.setOpaque(false);
            buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

            buttonPanel.add(customerButton);
            buttonPanel.add(waiterButton);
            buttonPanel.add(supervisorButton);

            JPanel centerPanel = new JPanel(new GridBagLayout());
            centerPanel.setOpaque(false);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            centerPanel.add(buttonPanel, gbc);

            panel.add(centerPanel, BorderLayout.CENTER);

            mainPanel.add(panel, "initial");

            customerButton.addActionListener(e -> {
                currentRoleKey = "customer";
                cardLayout.show(mainPanel, "customer");
            });

            supervisorButton.addActionListener(e -> {
                currentRoleKey = "supervisor";
                cardLayout.show(mainPanel, "supervisor");
            });

            waiterButton.addActionListener(e -> {
                currentRoleKey = "waiter";
                cardLayout.show(mainPanel, "waiter");
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createLoginScreen(String roleKey, String roleDisplayName) {
        try {
            Image image = new ImageIcon("src/images/restaurant-interior.jpg").getImage();
            BackgroundPanel panel = new BackgroundPanel(image);

            panel.setLayout(new GridBagLayout()); // for flexible layout

            JLabel title = new JLabel("Welcome to the Restaurant Management System, please login as " + roleDisplayName, SwingConstants.CENTER);
            title.setFont(new Font("Arial", Font.BOLD, 16));
            title.setForeground(Color.WHITE);

            JPanel formPanel = new JPanel(new GridBagLayout());
            formPanel.setOpaque(false);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.anchor = GridBagConstraints.WEST;

            JLabel nameLabel = new JLabel("Full Name:");
            nameLabel.setForeground(Color.WHITE);
            JTextField nameField = new JTextField(15);

            JLabel passwordLabel = new JLabel("Password:");
            passwordLabel.setForeground(Color.WHITE);
            JPasswordField passwordField = new JPasswordField(15);

            JButton loginButton = new JButton("Login");
            JButton backButton = new JButton("⇐"); // Unicode arrow (or use "<=")
            JButton signUpButton = new JButton("Sign Up");

            // Layout components
            gbc.gridx = 0; gbc.gridy = 0; formPanel.add(nameLabel, gbc);
            gbc.gridx = 1; formPanel.add(nameField, gbc);
            gbc.gridx = 0; gbc.gridy = 1; formPanel.add(passwordLabel, gbc);
            gbc.gridx = 1; formPanel.add(passwordField, gbc);

            // Button row
            JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
            buttonRow.setOpaque(false);
            buttonRow.add(backButton);
            buttonRow.add(loginButton);
            buttonRow.add(signUpButton);
            gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; // span both columns
            gbc.anchor = GridBagConstraints.CENTER;
            formPanel.add(buttonRow, gbc);

            // Status message
            JLabel statusLabel = new JLabel("");
            statusLabel.setForeground(Color.YELLOW);
            statusLabel.setPreferredSize(new Dimension(260, 20));
            statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
            statusLabel.setVisible(true);

            JPanel statusPanel = new JPanel();
            statusPanel.setOpaque(false);
            statusPanel.add(statusLabel);

            statusLabels.put(roleKey, statusLabel);
            gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            formPanel.add(statusPanel, gbc);

            // Add the title
            GridBagConstraints titleConstraints = new GridBagConstraints();
            titleConstraints.gridx = 0; titleConstraints.gridy = 0; titleConstraints.gridwidth = 2;
            titleConstraints.insets = new Insets(0, 0, 20, 0);
            titleConstraints.anchor = GridBagConstraints.NORTH;
            panel.add(title, titleConstraints);

            // Add the form
            GridBagConstraints formConstraints = new GridBagConstraints();
            formConstraints.gridx = 0; formConstraints.gridy = 1; formConstraints.anchor = GridBagConstraints.CENTER;
            panel.add(formPanel, formConstraints);

            mainPanel.add(panel, roleKey);

            backButton.addActionListener(e -> {
                currentRoleKey = "";
                cardLayout.show(mainPanel, "initial");
            });

            loginButton.addActionListener(e -> {
                userId = nameField.getText();
                password = new String(passwordField.getPassword());
                if (controller != null) {
                    try {
                        controller.handleLogin(userId, password, roleKey);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            signUpButton.addActionListener(e -> {
                userId = nameField.getText();
                password = new String(passwordField.getPassword());
                if (controller instanceof LoginController loginController) {
                    loginController.handleSignUp(userId, password, roleKey);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void show() {
        frame.setVisible(true);
    }

    @Override
    public void updateView(Object data) {
        if (data instanceof String && statusLabels.containsKey(currentRoleKey)) {
            JLabel label = statusLabels.get(currentRoleKey);
            label.setText((String) data);
            label.revalidate();
            label.repaint();
        }
    }

    @Override
    public void setUser(User user) {
        this.currentUser = user;
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void reset() {
        // Reset fields if needed
    }

    @Override
    public String getViewName() {
        return "LoginView";
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void close() {
        this.frame.dispose(); // or frame.setVisible(false);
    }
}
