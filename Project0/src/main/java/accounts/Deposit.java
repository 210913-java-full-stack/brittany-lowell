package accounts;

import daos.AccountsDAO;
import daos.UserAccountDAO;
import menus.MainMenu;
import models.Accounts;
import models.UserAccounts;
import project0list.BLArrayList;

import java.util.Scanner;

public class Deposit {
    private Scanner input;
    private int userId;
    String accountType;
    double newBalance;
    int accountID;
    StringBuffer formattedDepositAmount;

    MainMenu mainMenu = new MainMenu();
    Accounts accounts = new Accounts();
    AccountsDAO accountsDAO = new AccountsDAO();
    UserAccountDAO userAccountDAO = new UserAccountDAO();

    public Deposit(int userID) {
        this.userId = userID;
        this.input = mainMenu.getScanner();
    }

    public void depositFunds() {

        System.out.println("Which account would you like to deposit your funds?\nPlease type in the account number " +
                "next to the account you would like to deposit your funds into.");
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
        BLArrayList<UserAccounts> accountIDList = userAccountDAO.getItem(this.userId);
        int accountsAssociatedWithThisUserId;
        boolean accountsMatch = false;
        for(int i = 0; i < accountIDList.len; i++) {
            accountsAssociatedWithThisUserId = accountIDList.get(i).getAccount_id();
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


        System.out.println("How much money would you like to deposit?");
        double depositAmount = this.input.nextDouble();
        input.nextLine();
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
