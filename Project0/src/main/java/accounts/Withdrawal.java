package accounts;

import daos.AccountsDAO;
import menus.MainMenu;
import menus.MoveFunds;
import models.Accounts;
import utils.VerifyOtherUsers;

import java.util.Scanner;

public class Withdrawal {
    Scanner input;
    int userId;
    String accountType;
    double newBalance;
    int accountID;
    StringBuffer formattedWithdrawalAmount;

    MainMenu mainMenu = new MainMenu();
    Accounts accounts = new Accounts();
    AccountsDAO accountsDAO = new AccountsDAO();
    VerifyOtherUsers verifyOtherUsers = new VerifyOtherUsers();
    MoveFunds moveFunds = new MoveFunds();

    /**
     * Constructor for the Withdrawal class.
     * @param userId Requires current user's user ID.
     */
    public Withdrawal(int userId) {
        this.userId = userId;
        this.input = mainMenu.getScanner();
    }

    /**
     * This method withdraws funds from a given account.
     */
    public void withdrawalFunds() {

        //Get the account to withdraw funds from.
        System.out.println("Which account would you like to withdraw from?");
        DisplayAccounts displayAccounts = new DisplayAccounts(this.userId);
        displayAccounts.display();
        System.out.println("Please type in the account number next to the account you would like to make " +
                "the withdraw from.");
        accountID = this.input.nextInt();
        input.nextLine();

        //Prevents negative numbers
        boolean noNegativeNumbers = moveFunds.noNegativeNums(accountID);
        if(!noNegativeNumbers){
            return;
        }

        //Prevents user from withdrawing funds from an account that does not exist
        int currentMaxAccountId = accountsDAO.getAccountId(accounts);
        if(currentMaxAccountId < accountID) {
            System.out.println("That account does not exist! Please review your accounts to obtain the desired" +
                    "account number.");
            return;
        }

        //Makes sure the provided account belongs to the current user
        boolean accountsMatch = verifyOtherUsers.verifyAccountIdMatchesCurrentUser(this.userId, accountID);

        if(accountsMatch){
            accounts = accountsDAO.getItem(accountID);
            accounts.setId(accountID);
        } else {
            System.out.println("The chosen account number does not match any of your accounts.\n" +
                    "Please review your accounts for the correct account number(s).");
            return;
        }

        //Gets the amount to withdrawal
        System.out.println("How much money would you like to withdrawal?");
        double withdrawalAmount = this.input.nextDouble();
        input.nextLine();

        //Prevents a negative withdrawal
        noNegativeNumbers = moveFunds.noNegativeNums(withdrawalAmount);
        if(!noNegativeNumbers) {
            return;
        }

        formattedWithdrawalAmount = accounts.formatBalance(withdrawalAmount);
        //Gets new balance
        newBalance = accounts.getBalance() - withdrawalAmount;

        //Makes sure the amount of money withdrawn is within the limits set by the database
        if(Double.toString(withdrawalAmount).length() > 11){
            System.out.println("The withdrawal amount is too large.\nYou cannot withdrawal any amount that has more " +
                    "than 8 digits left of the decimal point and more than two digits to the right of the " +
                    "decimal point.\n");
            return;
        }

        //Checks if the user has enough funds in their account
        StringBuffer formattedNewBalance = accounts.formatBalance(accounts.getBalance());
        boolean doIHaveTheFunds = moveFunds.enoughFunds(newBalance,accountID,formattedNewBalance);
        if(!doIHaveTheFunds) {
            return;
        }
        //Sets the new balance and saves the account ID and the new balance to the database
        accounts.setBalance(newBalance);
        accountsDAO.save(accounts);
        accountType = accounts.getAccountType();
        //Lets the user know that they were able to successfully withdrawal money.
        System.out.println("You have successfully withdrawn " + formattedWithdrawalAmount + " from " +
                accountType + " Account: " + accountID);
        displayAccounts.display();

    }

}
