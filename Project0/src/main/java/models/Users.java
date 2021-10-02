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
    private StringBuffer username = new StringBuffer();
    private StringBuffer password = new StringBuffer();

    public Users() {
    }

    public Users(int id) {
        this.id = id;
    }

    public Users(int id, String username, String password) {
        /*
        Convert a String to a StringBuffer using the append() method.
        You have to create a new StringBuffer object first before using the following line of code.
        ie: stringBuffer.append(string);
         */
        this.id = id;
        this.username.append(username);
        this.password.append(password);
    }

    public Users(int id, String firstName, String lastName, String username, String password) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username.append(username);
        this.password.append(password);
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

    public String getUsername() {
                /*
        Convert a StringBuffer to String using toString() method.
        You have to create a new StringBuffer object first before using the following line of code.
        String str = stringBuffer.toString();
         */
        return this.username.toString();
    }

    public void setUsername(StringBuffer username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password.toString();
    }

    public void setPassword(StringBuffer password) {
        this.password = password;
    }
}
