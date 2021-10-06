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
    Connection conn = Connections.getConnection(); //Created the connection in this class instead of the Driver class.
    Scanner input = new Scanner(System.in); //Scanner object bound to System.in, the console input
    public MainMenu() {
        running = true;
    }

    public Connection getConn(){
        return conn;
    }

    public Scanner getScanner(){
        return input;
    }

    /*
    I did not want to make another class, so I am calling methods from the other classes based on their responses in
    this switch statement. This was how I chose to avoid getting stuck in an infinite loop.
     */
    public void viewMainMenu() {
        System.out.println("Homepage\nPlease choose an option below:\n" +
                "1) Login\n2) Register\n3) Exit App");
        //Takes the user input on the next line and stores it in inNum.
        String inNum = input.nextLine();
        //This if, then, else statement stops the user from inputting invalid values.
        if (inNum.equals("1") || inNum.equals("2") || inNum.equals("3")) {

            switch (inNum) {
                case "1":
                    //The login class contains a "logout" option that returns the user to the main menu.
                    Login login = new Login();
                    login.runLogin(); //Abstracts the inner workings of the Login class
                    return;
                case "2":
                    //Go to the register screen
                    Register register = new Register();
                    register.runRegister(); //Abstracts the inner workings of the Register class
                    return;
                case "3":
                    //Exiting the app
                    setRunning(false);
                    try {
                        conn.close();
                    } catch (SQLException throwable) {
                        throwable.printStackTrace();
                    }
                    break;
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

    //Getters and Setters
    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
