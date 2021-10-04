package utils;

import daos.UsersDAO;
import exceptions.PasswordIsNotValid;
import models.Users;

import java.nio.charset.StandardCharsets;

/**
 * This class contains all methods for obtaining and verifying passwords from the user console.
 * */
public class Password {
    private int userId;
    private String password;
    Users users = new Users();

    public Password() {
    }

    public Password(String password, int userId) {
        this.password = password;
        this.userId = userId;
    }

    /**
     * This method verifies the password.
     */
    public boolean verifyPassword(){

        boolean passwordMatch = passwordInDatabase();

        if(!passwordMatch){
            System.out.println("This password is incorrect.");
            return false;
        } else{
            return true;
        }
    }

    private boolean passwordInDatabase() {
        String passwordMatches = password;
        UsersDAO usersDAO = new UsersDAO();
        users = usersDAO.getItem(userId);
        String password = users.getPassword();
        return password.equals(passwordMatches);
    }
    /**
     * This method checks if the password contains: any special characters, uppercase letters, lowercase letters, and
     * numbers. If the password contains special characters, then this method will throw a PasswordIsNotValid exception.
     * This method will also throw a PasswordIsNotValid exception if there are no uppercase letters, lowercase letters,
     * and/or numbers.
     * Finally, this method checks if the password is between 8 and 20 characters long.
     */
    public boolean checkPassword(String inputString) throws PasswordIsNotValid {
        boolean check;
        if (inputString.length() < 8 || inputString.length() > 20) {
            throw new PasswordIsNotValid(inputString.length());
        }
        int c = 0;
        int lc = 0;
        int num = 0;

        byte[] bytes = inputString.getBytes(StandardCharsets.US_ASCII);

        for (int i = 0; i < bytes.length; i++) {
            //Checking for special characters.
            if (bytes[i] >= 32 && bytes[i] <= 47) {
                throw new PasswordIsNotValid(true);
            }

            //Checking for capital letters.
            if (bytes[i] >= 65 && bytes[i] <= 90) {
                c++;
            }
            //Checking for lowercase letters.
            if (bytes[i] >= 97 && bytes[i] <= 122) {
                lc++;
            }
            //Checking for numbers.
            else if (bytes[i] >= 48 && bytes[i] <= 57) {
                num++;
            }
        }
        check = c != 0 && lc != 0 && num != 0;
        return check;
    }
}
