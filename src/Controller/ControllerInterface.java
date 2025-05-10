package Controller;

import java.sql.SQLException;

public interface ControllerInterface {
    void handleLogin(String fullName, String password, String role) throws SQLException;
}
