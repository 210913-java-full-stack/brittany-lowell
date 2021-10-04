package accounts;

import daos.AccountsDAO;
import daos.UserAccountDAO;
import menus.MainMenu;
import models.Accounts;
import models.UserAccounts;
import project0list.BLArrayList;

import java.util.Scanner;

public class Transfer {
    private Scanner input;
    private int userId;
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
    UserAccountDAO userAccountDAO = new UserAccountDAO();

    public Transfer(int userID) {
        this.userId = userID;
        this.input = mainMenu.getScanner();
    }

    public void transferFunds() {

        //Get the account that the funds will be transferred from.
        System.out.println("Which account would you like to transfer your funds FROM?\nPlease type in the account" +
                "number next to that account.\nNOTE: You cannot transfer " +
                "funds from another user's account. They need to initiate the transfer from their end.");
        DisplayAccounts displayAccounts = new DisplayAccounts(this.userId);
        displayAccounts.display();
        accountID = this.input.nextInt();
        input.nextLine();

        int currentMaxAccountId = accountsDAO.getAccountId(accounts);
        if(currentMaxAccountId < accountID) {
            System.out.println("That account does not exist! Please review your accounts to obtain the desired" +
                    "account number.");
            return;
        }
        BLArrayList<UserAccounts> accountIds = userAccountDAO.getItem(this.userId);
        int accountsAssociatedWithThisUserId;
        boolean accountsMatch = false;
        for(int i = 0; i < accountIds.len; i++) {
            accountsAssociatedWithThisUserId = accountIds.get(i).getAccount_id();
            if(accountsAssociatedWithThisUserId == accountID){
                accountsMatch = true;
                break;
            }
        }
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
        formattedTransferAmount = accounts.formatBalance(transferAmount);

        newBalanceForFirstAccount = accounts.getBalance() - transferAmount;
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
