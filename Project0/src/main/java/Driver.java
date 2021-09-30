// This class is my Driver class.

import exceptions.InvalidConsoleResponse;
import project0list.BLArrayList;
import screens.Login;
import utils.Connections;

import java.sql.Connection;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args){

        Connection conn = Connections.getConnection();

        //Scanner object bound to System.in, the console input
        Scanner input = new Scanner(System.in);
        /*
        To take user input in as a StringBuffer, use the following lines of code.
        StringBuffer s = new StringBuffer();
        s.append(this.input.nextLine()); <= appends the console input to a StringBuffer
         */

        boolean running = true;
        while(running) {
            System.out.println("Homepage\nPlease choose an option below:\n" +
                    "1) Login\n2) Register\n3) Exit App");
            String inNum = input.nextLine(); //inNum is used to t
            if (inNum.equals("1") || inNum.equals("2") || inNum.equals("3")) {

                switch (inNum) {
                    case "1":
                        Login login = new Login(input);
                        login.runLogin(); //Abstracts the inner workings of the Login class
                        break;
                    case "2":
                        //go to the register screen
                        continue;
                    case "3":
                        running = false;
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
    }

}

