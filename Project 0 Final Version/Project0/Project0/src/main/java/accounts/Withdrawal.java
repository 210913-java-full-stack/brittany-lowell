package accounts;

import daos.AccountsDAO;
import menus.MainMenu;
import menus.MoveFunds;
import models.Accounts;
import utils.VerifyOtherUsers;

import java.util.Scanner;

public class Withdrawal {
    private Scanner input;
    private int userId;
    String accountType;
    double newBalance;
    int accountID;
    StringBuffer formattedWithdrawalAmount;

    MainMenu mainMenu = new MainMenu();
    Accounts accounts = new Accounts();
    AccountsDAO accountsDAO = new AccountsDAO();
    VerifyOtherUsers verifyOtherUsers = new VerifyOtherUsers();
    MoveFunds moveFunds = new MoveFunds();

    public Withdrawal(int userId) {
        this.userId = userId;
        this.input = mainMenu.getScanner();
    }

    public void withdrawalFunds() {

        System.out.println("Which account would you like to withdraw from?");
        DisplayAccounts displayAccounts = new DisplayAccounts(this.userId);
        displayAccounts.display();
        System.out.println("Please type in the account number next to the account you would like to make " +
                "the withdraw from.");
        accountID = this.input.nextInt();
        input.nextLine();

        boolean noNegativeNumbers = moveFunds.noNegativeNums(accountID);
        if(!noNegativeNumbers){
            return;
        }

        int currentMaxAccountId = accountsDAO.getAccountId(accounts);
        if(currentMaxAccountId < accountID) {
            System.out.println("That account does not exist! Please review your accounts to obtain the desired" +
                    "account number.");
            return;
        }

        boolean accountsMatch = verifyOtherUsers.verifyAccountIdMatchesCurrentUser(this.userId, accountID);

        if(accountsMatch){
            accounts = accountsDAO.getItem(accountID);
            accounts.setId(accountID);
        } else {
            System.out.println("The chosen account number does not match any of your accounts.\n" +
                    "Please review your accounts for the correct account number(s).");
            return;
        }

        System.out.println("How much money would you like to withdrawal?");
        double withdrawalAmount = this.input.nextDouble();
        input.nextLine();

        noNegativeNumbers = moveFunds.noNegativeNums(withdrawalAmount);
        if(!noNegativeNumbers) {
            return;
        }

        formattedWithdrawalAmount = accounts.formatBalance(withdrawalAmount);
        newBalance = accounts.getBalance() - withdrawalAmount;

        if(Double.toString(newBalance).length() > 11){
            System.out.println("The withdrawal amount is too large.\nYou cannot withdrawal any amount that has more " +
                    "than 8 digits left of the decimal point and more than two digits to the right of the " +
                    "decimal point.\n");
            return;
        }

        StringBuffer formattedNewBalance = accounts.formatBalance(accounts.getBalance());
        boolean doIHaveTheFunds = moveFunds.enoughFunds(newBalance,accountID,formattedNewBalance);
        if(!doIHaveTheFunds) {
            return;
        }
        accounts.setBalance(newBalance);
        accountsDAO.save(accounts);
        accountType = accounts.getAccountType();
        System.out.println("You have successfully withdrawn " + formattedWithdrawalAmount + " from " +
                accountType + " Account: " + accountID);
        displayAccounts.display();

    }

}
