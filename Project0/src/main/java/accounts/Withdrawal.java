package accounts;

import daos.AccountsDAO;
import daos.UserAccountDAO;
import menus.MainMenu;
import models.Accounts;
import models.UserAccounts;
import project0list.BLArrayList;

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
    UserAccountDAO userAccountDAO = new UserAccountDAO();

    public Withdrawal(int userId) {
        this.userId = userId;
        this.input = mainMenu.getScanner();
    }

    public void withdrawalFunds() {

        System.out.println("Which account would you like to withdraw from?\nPlease type in the account number " +
                "next to the account you would like to make the withdraw from.");
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
            System.out.println("The chosen account number does not match any of your accounts.\n" +
                    "Please review your accounts for the correct account number(s).");
            return;
        }

        System.out.println("How much money would you like to withdrawal?");
        double withdrawalAmount = this.input.nextDouble();
        input.nextLine();
        formattedWithdrawalAmount = accounts.formatBalance(withdrawalAmount);

        newBalance = accounts.getBalance() - withdrawalAmount;
        accounts.setBalance(newBalance);
        accountsDAO.save(accounts);
        accountType = accounts.getAccountType();
        System.out.println("You have successfully withdrawn " + formattedWithdrawalAmount + " from " +
                accountType + " Account: " + accountID);
        displayAccounts.display();

    }
}
