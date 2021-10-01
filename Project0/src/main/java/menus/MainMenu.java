package menus;

import exceptions.InvalidConsoleResponse;
import utils.Connections;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * This class displays the main menu and sends the user to the login menu, register menu, or it ends the app depending
 * on which number the user enters.
 */
public class MainMenu {
    private boolean running;
    private Connection conn;
    private Scanner input;

    public MainMenu() {
        running = true;
        conn = Connections.getConnection(); //Created the connection in this class instead of the Driver class.
        input = new Scanner(System.in); //Scanner object bound to System.in, the console input
    }

    /*
    I did not want to make another class, so I am calling methods from the other classes based on their responses in
    this switch statement. This was how I chose to avoid getting stuck in an infinite loop.
     */
    public void viewMainMenu(){
        System.out.println("Homepage\nPlease choose an option below:\n" +
                "1) Login\n2) Register\n3) Exit App");
        String inNum = input.nextLine(); //Takes the user input on the next line and stores it in inNum.
        //This if, then, else statement stops the user from inputting invalid values.
        if (inNum.equals("1") || inNum.equals("2") || inNum.equals("3")) {

            switch (inNum) {
                case "1":
                    //The login class contains a "logout" option that returns the user to the main menu.
                    Login login = new Login();
                    login.runLogin(); //Abstracts the inner workings of the Login class
                    return;
                case "2":
                    //go to the register screen
                    return;
                case "3":
                    //Exiting the app
                    setRunning(false);
                    try {
                        conn.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    break; //Probably not necessary, but I don't feel comfortable leaving this open to fall through.
            }
        } else {
            try {
                throw new InvalidConsoleResponse("This menu only accepts the responses: 1, 2, or 3.");
            } catch (InvalidConsoleResponse invalidConsoleResponse) {
                invalidConsoleResponse.printStackTrace();
                System.out.println("Please type 1, 2, or 3 as an integer.\n");
            }
        }

    }
    //Boolean version of a getter
    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    //This method will allow the other menu classes to access the connection object.
    public Connection getConn() {
        return conn;
    }
    //This method will allow the other menu classes to access the scanner object.
    public Scanner getInput() {
        return input;
    }
}
