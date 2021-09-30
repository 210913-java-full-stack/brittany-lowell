package screens;

import exceptions.InvalidConsoleResponse;

import java.util.Scanner;
/*
This class contains the methods for successfully logging in and then displays the next menu.
The only classes that are public are the constructor and the runLogin method.
The method runLogin calls all the methods in this class to encapsulate the login process.
Also, it helps decrease the size of the Driver class
 */
public class Login {
    Scanner input;

    /*
    Constructor takes in the Scanner object input and sets it equal to the input variable in this class.
    I didn't want to keep creating a new Scanner object for the menus, so I am having the constructor take in
    this variable so that I can use it in this class.
     */
    //Constructor for the Login class
    public Login(Scanner input) {
        this.input = input;
    }
    //Method that calls all Login methods
    public void runLogin(){
        //get username from database

        //only prints the next menu if login is successful
        printMenu(b);
    }

    //This method displays the second menu so that it can only be accessed if the user logs in successfully.
    private void printMenu(boolean b){

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
                        innerMenu = false;
                        break;
                }
            } else {
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
