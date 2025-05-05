package Model;

public abstract class User {
    protected int userID; 
    protected String username;
    protected String password;
    protected String role; // Is it a supervisor, waiter or a customer?

    public User(int userID, String username) {
        this.userID = userID;
        this.username = username;
    }

    public int getUserID() { return userID; }
    public String getUsername() { return username; }
}
