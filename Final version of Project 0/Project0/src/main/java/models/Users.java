package models;

import exceptions.UsernameIsNotValid;

import java.nio.charset.StandardCharsets;

/**
 * This model class allows the user to access the information stored in the users table.
 * The combination of the public methods and the private variables restricts the access the user has to these
 * variables. They have to go through the getter and setter methods to interact with these variables.
 */
public class Users {
    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;

    public Users() {
    }


    public Users(int id, String firstName, String lastName, String username, String password) {

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

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * This method checks if a name contains any special characters.
     * If this method is used to check a username, then it checks if the username is between 5 and 20 characters long.
     * If this method is used to check a first or last name, then it checks if that name is between 3 and 30 characters
     * long.
     * Finally, if the username, first name, or last name meets the size requirements, this method then checks if there
     * are any special characters in the username.
     * @return Returns true if the name matches the requirements
     */
    public boolean checkName(String inputString, String typeOfName) throws UsernameIsNotValid {
        //Turns the string into a byte array and replaces the characters with their associated ASCII values
        byte[] bytes = inputString.getBytes(StandardCharsets.US_ASCII);
        //Checks the length of the username
        if (typeOfName.equals("username")) {
            if (inputString.length() < 5 || inputString.length() > 20) {
                throw new UsernameIsNotValid(inputString.length());
            }
        } else if (typeOfName.equals("name")) {
            //Checks the length of the name
            if (inputString.length() < 3 || inputString.length() > 30) {
                System.out.println("Names must be between 3 and 30 characters long.");
                return false;
            } //Checks if first character in the name string is a capital letter
            if (bytes[0] <= 65 || bytes[0] >= 90) {
                System.out.println("Names must start with a capital letter.");
                return false;
            } else {
                return true;
            }
        }
        for (int i = 0; i < bytes.length; i++) {
            //Checking for special characters.
            if (bytes[i] >= 32 && bytes[i] <= 47) {
                if (typeOfName.equals("username")) {
                    throw new UsernameIsNotValid(true);
                } else {
                    System.out.println("Names cannot contain special characters.");
                }
            }
        }
        return true;
    }
}