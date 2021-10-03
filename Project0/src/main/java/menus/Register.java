package menus;

import daos.UserAccountDAO;
import daos.UsersDAO;
import exceptions.PasswordIsNotValid;
import exceptions.UsernameIsNotValid;
import models.UserAccounts;
import models.Users;
import utils.Password;
import utils.Username;
import java.util.Scanner;

/**
 * This class takes the user's name (first and last), username, and password. It then saves this info to the database
 * and then calls the runLogin method to start the login process.
 */
public class Register {
    private Scanner input;
    Password password = new Password();
    MainMenu mainMenu = new MainMenu();
    UsersDAO usersDAO = new UsersDAO();
    UserAccountDAO userAccountDAO = new UserAccountDAO();
    UserAccounts userAccounts = new UserAccounts();
    Users user = new Users();


    public Register() {
        this.input = mainMenu.getScanner();
    }

    /**
     * This method gets the first name, last name, username, and password from the user and saves it to the database.
     */
    public void runRegister() {
        boolean run = true;
        boolean check;
        String inputFirstName = "";
        //First name
        while (run) {
            System.out.println("Please enter your first name. The first letter of your first name must be capitalized.");
            inputFirstName = this.input.nextLine();
            try {
                check = user.checkName(inputFirstName, "name");
                if(check){
                    run = false;
                } else {
                    System.out.println("The first letter of your first name must be capitalized.\n" +
                            "Please reenter your first name.");
                }
            } catch (UsernameIsNotValid usernameIsNotValid) {
                usernameIsNotValid.printStackTrace();
            }
        }
        user.setFirstName(inputFirstName);

        //Last name
        String inputLastName = "";
        run = true;
        while (run) {
            System.out.println("Please enter your last name. The first letter of your last name must be capitalized.");
            inputLastName = this.input.nextLine();
            try {
                check = user.checkName(inputLastName, "name");
                if(check){
                    run = false;
                } else {
                    System.out.println("The first letter of your last name must be capitalized.\n" +
                            "Please reenter your last name.");
                }
            } catch (UsernameIsNotValid usernameIsNotValid) {
                usernameIsNotValid.printStackTrace();
            }
        }
        user.setLastName(inputLastName);

        //Username
        String inputUsername = "";
        run = true;
        while (run) {
            System.out.println("Please enter a username.\nNOTE: Usernames cannot contain any special characters " +
                    "and must be 5 to 20 characters long.");
            inputUsername = this.input.nextLine();
            Username username = new Username(inputUsername);
            try {
                check = user.checkName(inputUsername, "username");
                if (check) {
                    int id = username.usernameInDatabase();
                    if (id == -1) {
                        run = false;
                    } else {
                        System.out.println("That username has been taken. Please choose a different username.");
                    }
                }
            } catch (UsernameIsNotValid usernameIsNotValid) {
                usernameIsNotValid.printStackTrace();
            }
        }
        user.setUsername(inputUsername);

        //Password
        String inputPassword = "";
        run = true;
        while(run) {
            System.out.println("Please enter a password.\nNOTE: Passwords MUST have one capital letter, one lowercase" +
                    "letter, and one number.\n Passwords CANNOT contain any special characters.");
            inputPassword = this.input.nextLine();
            try {
                check = password.checkPassword(inputPassword);
                if (check) {
                    run = false;
                } else {
                    throw new PasswordIsNotValid();
                }
            } catch (PasswordIsNotValid e) {
                e.printStackTrace();
            }
        }
        user.setPassword(inputPassword);
        //getting and setting user_id
        int userId = (usersDAO.getUserId()) + 1;
        user.setId(userId);

        //Save everything to the database
        userAccounts.setUser_id(userId);
        userAccountDAO.save(userAccounts);
        usersDAO.save(user);

    }
}
