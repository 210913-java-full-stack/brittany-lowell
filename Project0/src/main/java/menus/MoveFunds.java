package menus;

import accounts.Deposit;
import accounts.Transfer;
import accounts.Withdrawal;
import exceptions.InvalidConsoleResponse;
import java.util.Scanner;

public class MoveFunds {
    private Scanner input;
    private int userId;
    private boolean moveFundsMenu = true;
    MainMenu mainMenu = new MainMenu();

    public MoveFunds(int userID) {
        this.input = mainMenu.getScanner();
        this.userId = userID;
    }


    /**
     * This method runs the menu for withdrawing, depositing, and transferring funds.
     */
    public void moveFunds() {
        System.out.println("Withdrawals, Deposits, and Transfers\nDo you have an existing Checking or Savings account?\n" +
                "Yes or No:");
        String accountCheck = this.input.nextLine();
        if (accountCheck.equals("Yes") || accountCheck.equals("Y") || accountCheck.equals("y") ||
                accountCheck.equals("No") || accountCheck.equals("N") || accountCheck.equals("n")) {
            switch (accountCheck) {
                case "Yes":
                case "Y":
                case "y":
                    while (moveFundsMenu) {
                        fundMenu();
                    }
                    break;
                case "No":
                case "N":
                case "n":
                    System.out.println("Please make a banking account first!");
                    break;
            }
        } else {
            try {
                throw new InvalidConsoleResponse("This menu only accepts the responses: Yes, Y, y, No, N, or n.");
            } catch (InvalidConsoleResponse invalidConsoleResponse) {
                invalidConsoleResponse.printStackTrace();
                System.out.println("Please type Yes, Y, y, No, N, or n!\n");
            }
        }
    }

    private void fundMenu() {
        System.out.println("Please choose an option below:\n1) Make a Withdrawal\n2) Make a Deposit\n" +
                "3) Transfer Funds\n4) Return to the previous screen");
        String userInput = this.input.nextLine();
        if (userInput.equals("1") || userInput.equals("2") || userInput.equals("3") || userInput.equals("4")) {
            switch (userInput) {
                case "1":
                    //Call withdrawal method
                    Withdrawal withdrawal = new Withdrawal(this.userId);
                    withdrawal.withdrawalFunds();
                    break;
                case "2":
                    //Call deposit method
                    Deposit deposit = new Deposit(this.userId);
                    deposit.depositFunds();
                    break;
                case "3":
                    //Call transfer method
                    Transfer transfer = new Transfer(this.userId);
                    transfer.transferFunds();
                    break;
                case "4":
                    moveFundsMenu = false; //Exits back to the post login menu
                    break;
            }
        }else { //This statement gives the user an error and then sends them back to the "Account menu"
            try {
                throw new InvalidConsoleResponse("This menu only accepts the responses: 1, 2, 3, or 4.");
            } catch (InvalidConsoleResponse invalidConsoleResponse) {
                invalidConsoleResponse.printStackTrace();
                System.out.println("Please type 1, 2, 3, or 4 as an integer.\n");
            }
        }
    }
}