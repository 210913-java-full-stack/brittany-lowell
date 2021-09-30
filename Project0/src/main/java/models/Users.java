package models;

/**
 * This model class allows the user to access the information stored in the users table.
 * The combination of the public methods and the private variables restricts the access the user has to these
 * variables. They have to go through the getter and setter methods to interact with these variables.
 */
public class Users {
    private int id;
    private String firstName;
    private String lastName;
    private StringBuffer username;
    private StringBuffer password;

    public Users() {
    }

    public Users(int id) {
        this.id = id;
    }

    public Users(int id, StringBuffer username, StringBuffer password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Users(int id, String firstName, String lastName, StringBuffer username, StringBuffer password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public StringBuffer getUsername() {
        return username;
    }

    public void setUsername(StringBuffer username) {
        this.username = username;
    }

    public StringBuffer getPassword() {
        return password;
    }

    public void setPassword(StringBuffer password) {
        this.password = password;
    }
}
