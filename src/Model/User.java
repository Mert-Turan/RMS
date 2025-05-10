package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public abstract class User {
    protected int userID;
    protected String password;
    protected String username;
    protected String role; // Is it a supervisor, waiter or a customer?

    public User(String password, String username) {
        this.password = password;
        this.username = username;
    }

    public String getUserPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public static User authenticate(String username, String password, String role) {
        try (Connection conn = DBConnection.getConnection()) {
            String table = switch (role.toLowerCase()) {
                case "customer" -> "Customers";
                case "waiter" -> "Waiters";
                case "supervisor" -> "Supervisors";
                default -> null;
            };

            if (table == null) return null;

            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT u.userID FROM Users u JOIN " + table + " t ON u.userID = t." + table.substring(0, table.length() - 1).toLowerCase() + "ID " +
                            "WHERE u.username = ? AND u.password = ?"
            );
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                switch (role.toLowerCase()) {
                    case "customer": return new Customer(password, username);
                    case "waiter": return new Waiter(password, username);
                    case "supervisor": return new Supervisor(password, username);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean usernameExists(String username) {
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT 1 FROM Users WHERE username = ?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public static boolean registerNewUser(String username, String password, String role) {
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            PreparedStatement insertUser = conn.prepareStatement(
                    "INSERT INTO Users (username, password) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            insertUser.setString(1, username);
            insertUser.setString(2, password);
            insertUser.executeUpdate();

            ResultSet generatedKeys = insertUser.getGeneratedKeys();
            if (generatedKeys.next()) {
                int userID = generatedKeys.getInt(1);
                PreparedStatement roleStmt = switch (role.toLowerCase()) {
                    case "customer" -> conn.prepareStatement("INSERT INTO Customers (customerID) VALUES (?)");
                    case "waiter" -> conn.prepareStatement("INSERT INTO Waiters (waiterID) VALUES (?)");
                    case "supervisor" -> conn.prepareStatement("INSERT INTO Supervisors (supervisorID) VALUES (?)");
                    default -> null;
                };

                if (roleStmt == null) return false;

                roleStmt.setInt(1, userID);
                roleStmt.executeUpdate();

                conn.commit();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
