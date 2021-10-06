package accounts;

import daos.AccountsDAO;

import menus.MainMenu;
import menus.MoveFunds;
import models.Accounts;
import utils.VerifyOtherUsers;

import java.util.Scanner;

public class Transfer {
    Scanner input;
    int userId;
    boolean noNegativeNumbers = true;
    String firstAccountType;
    String secondAccountType;
    double newBalanceForFirstAccount;
    double newBalanceForSecondAccount;
    int accountID;
    StringBuffer formattedTransferAmount;

    MainMenu mainMenu = new MainMenu();
    Accounts accounts = new Accounts();
    Accounts secondAccount = new Accounts();
    AccountsDAO accountsDAO = new AccountsDAO();
    VerifyOtherUsers verifyOtherUsers = new VerifyOtherUsers();
    MoveFunds moveFunds = new MoveFunds();

    /**
     * Constructor for the Transfer class.
     * @param userID Requires current user's user ID.
     */
    public Transfer(int userID) {
        this.userId = userID;
        this.input = mainMenu.getScanner();
    }

    /**
     * This method transfers funds from one account into another account.
     */
    public void transferFunds() {

        //Get the account that the funds will be transferred from.
        System.out.println("Which account would you like to transfer your funds FROM?\nNOTE: You cannot transfer " +
                "funds from another user's account. They need to initiate the transfer from their end.");
        DisplayAccounts displayAccounts = new DisplayAccounts(this.userId);
        //Displays the user's accounts
        displayAccounts.display();
        System.out.println("Please type in the account number next to that account.");
        accountID = this.input.nextInt();
        input.nextLine();

        //Prevents the user from inputting negative numbers
        noNegativeNumbers = moveFunds.noNegativeNums(accountID);
        if(!noNegativeNumbers) {
            return;
        }

        //Prevents the user from inputting an account number that does not exist.
        int currentMaxAccountId = accountsDAO.getAccountId();
        if(currentMaxAccountId < accountID) {
            System.out.println("That account does not exist! Please review your accounts to obtain the desired" +
                    "account number.");
            return;
        }

        //Makes sure the provided account belongs to the current user.
        boolean accountsMatch = verifyOtherUsers.verifyAccountIdMatchesCurrentUser(this.userId, accountID);

        //Sets accountID for the account that the funds are being transferred from
        if(accountsMatch){
            accounts = accountsDAO.getItem(accountID);
            accounts.setId(accountID);
        } else {
            System.out.println("The chosen account number does not match any of you accounts.\n" +
                    "Please review your accounts for the correct account number(s).");
            return;
        }

        //Get the account that the funds will be transferred to.
        System.out.println("Which account would you like to transfer your funds TO?\nPlease type in the account " +
                "number next to that account.");
        System.out.println("If you would like to transfer funds to another user's account, " +
                "please enter the account number associated with their account.");
        int secondAccountID = this.input.nextInt();
        input.nextLine();

        //Prevents negative numbers
        noNegativeNumbers = moveFunds.noNegativeNums(secondAccountID);
        if(!noNegativeNumbers) {
            return;
        }

        //Prevents inputting an account that does not exist
        if(currentMaxAccountId < secondAccountID) {
            System.out.println("That account does not exist! Please review your accounts to obtain the desired" +
                    "account number.");
            return;
        }

        //Sets accountID for the account that the funds are being transferred to
        secondAccount = accountsDAO.getItem(secondAccountID);
        secondAccount.setId(secondAccountID);

        //Gets the amount of money to transfer
        System.out.println("How much money would you like to transfer?");
        double transferAmount = this.input.nextDouble();
        input.nextLine();

        //Prevents negative transfers
        noNegativeNumbers = moveFunds.noNegativeNums(transferAmount);
        if(!noNegativeNumbers) {
            return;
        }

        //Formats balance and gets new balance
        formattedTransferAmount = accounts.formatBalance(transferAmount);
        newBalanceForFirstAccount = accounts.getBalance() - transferAmount;

        //Checks if the transfer amount is within the limits set in the database
        if(Double.toString(transferAmount).length() > 11){
            System.out.println("The transfer amount is too large.\nYou cannot deposit any amount that has more " +
                    "than 8 digits left of the decimal point and more than two digits to the right of the " +
                    "decimal point.");
            return;
        }

        //Formats the balance for when it prints later
        StringBuffer formattedNewBalance = accounts.formatBalance(accounts.getBalance());

        //Checks if the account that funds are being transferred from has enough funds to transfer over
        boolean doIHaveTheFunds = moveFunds.enoughFunds(newBalanceForFirstAccount,accountID,formattedNewBalance);
        if(!doIHaveTheFunds) {
            return;
        }
        //Sets the balance for the new account and saves the balance and account ID to the account table
        accounts.setBalance(newBalanceForFirstAccount);
        accountsDAO.save(accounts);

        //Gets the balance for the second account
        newBalanceForSecondAccount = secondAccount.getBalance() + transferAmount;

        //Sets the balance for the second account and saves the balance and account ID to the account table
        secondAccount.setBalance(newBalanceForSecondAccount);
        accountsDAO.save(secondAccount);

        //Lets the user know that the transfer was successful and displays the accounts for the current user.
        firstAccountType = accounts.getAccountType();
        secondAccountType = secondAccount.getAccountType();
        System.out.println("You have successfully transferred " + formattedTransferAmount + " from:\n " +
                firstAccountType + " Account: " + accountID + " to: " + secondAccountType +
                " Account: " + secondAccountID);
        displayAccounts.display();
    }
}
