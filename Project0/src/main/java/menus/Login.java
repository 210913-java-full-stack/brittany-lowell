package menus;

import accounts.CreateAccount;
import accounts.DisplayAccounts;
import exceptions.InvalidConsoleResponse;
import utils.Password;
import utils.Username;

import java.sql.Connection;
import java.util.Scanner;
/**
This class contains the methods for successfully logging in and then displays the next menu.
The method runLogin calls all the methods in this class to encapsulate the login process.
 */
public class Login {
    Scanner input;
    Connection conn;
    private int userID;
    MainMenu mainMenu = new MainMenu();


    //Constructor for the Login class
    public Login() {
        this.input = mainMenu.getScanner();
        this.conn = mainMenu.getConn();
    }
    /**
    *Method that calls all Login methods.
     */
    public void runLogin(){
        //Username
        System.out.println("Please enter your username:");
        String usernameInput = this.input.nextLine();
        Username username = new Username(usernameInput);
        userID = username.getUserID();
        if(userID == -1){
            return;
        }

        //Password
        System.out.println("Please enter your password.");
        String passwordInput = this.input.nextLine();
        Password password = new Password(passwordInput,userID);
        boolean match = password.verifyPassword();
        if(match) {
            //only prints the next menu if login is successful
            postLoginMenu();
        } else {
            System.out.println("The inputted username and password do not match!\nPlease chose the Login option " +
                    "if you wish to reenter your username and password.\nIf you don't have an account, then please" +
                    "register first and then login. Thank you!");
        }
    }


    /**
    *This method displays the second menu so that it can only be accessed if the user
     * logs in successfully.
     */
    private void postLoginMenu(){
        //This while loop allows the app to stay in this menu until the user wants to go back to the main menu.
        boolean innerMenu = true;
        while(innerMenu) {

            System.out.println("Account Menu\nPlease choose an option below:\n" +
                    "1) Display Accounts\n2) Create a new account\n3) Move Funds\n4) Share An Account\n5) Logout");
            String userInput = this.input.nextLine();

            if (userInput.equals("1") || userInput.equals("2") || userInput.equals("3") || userInput.equals("4") || userInput.equals("5")) {
                switch (userInput) {
                    case "1":
                        //call methods from DisplayAccounts
                        DisplayAccounts displayAccounts = new DisplayAccounts(userID);
                        displayAccounts.display();
                        break;
                    case "2":
                        //call methods from CreateAccount
                        CreateAccount createAccount = new CreateAccount(userID);
                        createAccount.newAccount();
                        break;
                    case "3":
                        //call methods from MoveFunds
                        MoveFunds moveFunds = new MoveFunds(userID);
                        moveFunds.moveFunds();
                        break;
                    case "4":
                        //call methods from ShareAccount
                        break;
                    case "5":
                        innerMenu = false; //Exits out to the main menu
                        break;
                }
            } else { //This statement gives the user an error and then sends them back to the "Account menu"
                try {
                    throw new InvalidConsoleResponse("This menu only accepts the responses: 1, 2, 3, or 4.");
                } catch (InvalidConsoleResponse invalidConsoleResponse) {
                    invalidConsoleResponse.printStackTrace();
                    System.out.println("Please type 1, 2, 3, or 4 as an integer.\n");
                }
            }
        }
    }
}
