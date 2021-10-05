package accounts;

import daos.AccountsDAO;

import menus.MainMenu;
import menus.MoveFunds;
import models.Accounts;
import models.Users;
import utils.VerifyOtherUsers;

import java.util.Scanner;

public class Transfer {
    private Scanner input;
    private int userId;
    private boolean noNegativeNumbers = true;
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

    public Transfer(int userID) {
        this.userId = userID;
        this.input = mainMenu.getScanner();
    }

    public void transferFunds() {

        //Get the account that the funds will be transferred from.
        System.out.println("Which account would you like to transfer your funds FROM?\nNOTE: You cannot transfer " +
                "funds from another user's account. They need to initiate the transfer from their end.");
        DisplayAccounts displayAccounts = new DisplayAccounts(this.userId);
        displayAccounts.display();
        System.out.println("Please type in the account number next to that account.");
        accountID = this.input.nextInt();
        input.nextLine();

        noNegativeNumbers = moveFunds.noNegativeNums(accountID);
        if(!noNegativeNumbers) {
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

        noNegativeNumbers = moveFunds.noNegativeNums(secondAccountID);
        if(!noNegativeNumbers) {
            return;
        }

        if(currentMaxAccountId < secondAccountID) {
            System.out.println("That account does not exist! Please review your accounts to obtain the desired" +
                    "account number.");
            return;
        }

        secondAccount = accountsDAO.getItem(secondAccountID);
        secondAccount.setId(secondAccountID);

        System.out.println("How much money would you like to transfer?");
        double transferAmount = this.input.nextDouble();
        input.nextLine();

        noNegativeNumbers = moveFunds.noNegativeNums(transferAmount);
        if(!noNegativeNumbers) {
            return;
        }

        formattedTransferAmount = accounts.formatBalance(transferAmount);
        newBalanceForFirstAccount = accounts.getBalance() - transferAmount;

        StringBuffer formattedNewBalance = accounts.formatBalance(accounts.getBalance());
        boolean doIHaveTheFunds = moveFunds.enoughFunds(newBalanceForFirstAccount,accountID,formattedNewBalance);
        if(!doIHaveTheFunds) {
            return;
        }
        accounts.setBalance(newBalanceForFirstAccount);
        accountsDAO.save(accounts);

        newBalanceForSecondAccount = secondAccount.getBalance() + transferAmount;
        secondAccount.setBalance(newBalanceForSecondAccount);
        accountsDAO.save(secondAccount);

        firstAccountType = accounts.getAccountType();
        secondAccountType = secondAccount.getAccountType();
        System.out.println("You have successfully transferred " + formattedTransferAmount + " from:\n " +
                firstAccountType + " Account: " + accountID + " to: " + secondAccountType +
                " Account: " + secondAccountID);
        displayAccounts.display();
    }
}
