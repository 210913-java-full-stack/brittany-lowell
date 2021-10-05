package accounts;

import daos.AccountsDAO;
import daos.UserAccountDAO;
import menus.MainMenu;
import menus.MoveFunds;
import models.Accounts;
import utils.VerifyOtherUsers;

import java.util.Scanner;

public class Deposit {
    private Scanner input;
    private int userId;
    private boolean noNegativeNumbers = true;
    String accountType;
    double newBalance;
    int accountID;
    StringBuffer formattedDepositAmount;

    MainMenu mainMenu = new MainMenu();
    Accounts accounts = new Accounts();
    AccountsDAO accountsDAO = new AccountsDAO();
    VerifyOtherUsers verifyOtherUsers = new VerifyOtherUsers();
    MoveFunds moveFunds = new MoveFunds();

    public Deposit(int userID) {
        this.userId = userID;
        this.input = mainMenu.getScanner();
    }

    public void depositFunds() {

        System.out.println("Which account would you like to deposit your funds?");
        DisplayAccounts displayAccounts = new DisplayAccounts(this.userId);
        displayAccounts.display();
        System.out.println("Please type in the account number next to the account you would like to " +
                "deposit your funds into.");
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


        System.out.println("How much money would you like to deposit?");
        double depositAmount = this.input.nextDouble();
        input.nextLine();

        noNegativeNumbers = moveFunds.noNegativeNums(depositAmount);
        if(!noNegativeNumbers) {
            return;
        }

        formattedDepositAmount = accounts.formatBalance(depositAmount);


        newBalance = accounts.getBalance() + depositAmount;
        accounts.setBalance(newBalance);
        accountsDAO.save(accounts);
        accountType = accounts.getAccountType();
        System.out.println("You have successfully deposited " + formattedDepositAmount + " from " +
                accountType + " Account: " + accountID);
        displayAccounts.display();
    }
}
