package menus;

import accounts.Deposit;
import accounts.Transfer;
import accounts.Withdrawal;
import daos.AccountsDAO;
import exceptions.InvalidConsoleResponse;
import models.Accounts;

import java.util.Scanner;

public class MoveFunds {
    private Scanner input;
    private int userId;
    private boolean moveFundsMenu = true;
    MainMenu mainMenu = new MainMenu();
    Accounts accounts = new Accounts();
    AccountsDAO accountsDAO = new AccountsDAO();

    public MoveFunds() {
    }

    public MoveFunds(int userID) {
        this.input = mainMenu.getScanner();
        this.userId = userID;
    }


    /**
     * This method runs the menu for withdrawing, depositing, and transferring funds.
     */
    public void fundMenu() {
        accounts = accountsDAO.getItem(1);
        if(accounts == null){
            System.out.println("You do not currently have any Checking or Savings accounts.\n" +
                    "Please create a new account first.");
            return;
        }
        accounts = null;

        System.out.println("Withdrawals, Deposits, and Transfers.\nPlease choose an option below:\n1) Make a Withdrawal\n2) Make a Deposit\n" +
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

    public boolean noNegativeNums(double amountOfMoney){
        if(amountOfMoney < 0){
            try{
                throw new InvalidConsoleResponse("Please enter a number greater than zero.");
            } catch (InvalidConsoleResponse invalidConsoleResponse) {
                invalidConsoleResponse.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public boolean noNegativeNums(int id){
        if(id < 0){
            try{
                throw new InvalidConsoleResponse("Please enter a number greater than zero.");
            } catch (InvalidConsoleResponse invalidConsoleResponse) {
                invalidConsoleResponse.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public boolean enoughFunds(double newBalance, int accountID, StringBuffer formattedCurrentBalance){
        if(newBalance < 0){
            System.out.println("Whoops! You tried to withdraw too much money from account number " + accountID + "!\n" +
                    "Account " + accountID + " currently has a balance of: " + formattedCurrentBalance  +
                    "\nPlease input a new amount to withdraw from this account or withdraw funds from another " +
                    "account.");
            return false;
        }

        return true;
    }

}