package menus;

import exceptions.InvalidConsoleResponse;

import java.sql.Connection;
import java.util.Scanner;
/**
This class contains the methods for successfully logging in and then displays the next menu.
The method runLogin calls all the methods in this class to encapsulate the login process.
 */
public class Login {
    Scanner input;
    Connection conn;

    MainMenu mainMenu = new MainMenu();

    //Constructor for the Login class
    protected Login() {
        this.input = mainMenu.getInput();
        this.conn = mainMenu.getConn();
    }
    /**
    *Method that calls all Login methods.
     */
    public void runLogin(){
        //get username from database

        //only prints the next menu if login is successful
        loginMenu();
    }

    /**
    *This method displays the second menu so that it can only be accessed if the user
     * logs in successfully.
     */
    private void loginMenu(){
        //This while loop allows the app to stay in this menu until the user wants to go back to the main menu.
        boolean innerMenu = true;
        while(innerMenu) {

            System.out.println("Account Menu\nPlease choose an option below:\n" +
                    "1) Display Accounts\n2) Share An Account\n3) Move Funds\n4) Logout");
            String inNum = this.input.nextLine(); //inNum is used to t

            if (inNum.equals("1") || inNum.equals("2") || inNum.equals("3") || inNum.equals("4")) {
                switch (inNum) {
                    case "1":
                        //call methods from DisplayAccounts
                        break;
                    case "2":
                        //call methods from ShareAccount
                        break;
                    case "3":
                        //call methods from MoveFunds
                        break;
                    case "4":
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
