// This class is my Driver class.

import exceptions.InvalidConsoleResponse;
import project0list.BLArrayList;
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
        StringBuffer input = new StringBuffer();
        ... Other code ...
        System.out.println(stuff);
        input.append(userInput.nextLine()); <= appends the console input to a StringBuffer
         */

        boolean running = true;
        boolean innerMenu = true;
        while(running) {
            System.out.println("Homepage\nPlease choose an option below:\n" +
                    "1) Login\n2) Register\n3) Exit App");
            String inNum = input.nextLine(); //inNum is used to t
            if (inNum.equals("1") || inNum.equals("2") || inNum.equals("3")) {

                switch (inNum) {
                    case "1":
                        //go to the login screen
                        boolean login = true;
                        break;
                    case "2":
                        //go to the register screen
                        continue;
                    case "3":
                        running = false;
                        innerMenu = false;
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

            while(innerMenu) {

                System.out.println("Account Menu\nPlease choose an option below:\n" +
                        "1) Display Accounts\n2) Share An Account\n3) Move Funds\n4)Logout");
                inNum = input.nextLine(); //inNum is used to t

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

}

