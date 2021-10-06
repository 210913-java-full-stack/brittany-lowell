package accounts;

import daos.AccountsDAO;
import menus.MainMenu;
import menus.MoveFunds;
import models.Accounts;
import utils.VerifyOtherUsers;

import java.util.Scanner;

public class Deposit {
    Scanner input;
    int userId;
    boolean noNegativeNumbers = true;
    String accountType;
    double newBalance;
    int accountID;
    StringBuffer formattedDepositAmount;

    MainMenu mainMenu = new MainMenu();
    Accounts accounts = new Accounts();
    AccountsDAO accountsDAO = new AccountsDAO();
    VerifyOtherUsers verifyOtherUsers = new VerifyOtherUsers();
    MoveFunds moveFunds = new MoveFunds();

    /**
     * Constructor for the Deposit class
     * @param userID Requires the user ID of the user currently logged in
     */
    public Deposit(int userID) {
        this.userId = userID;
        this.input = mainMenu.getScanner();
    }

    /**
     *This method prompts the user to enter how much money they would like to deposit and then adds that
     * amount to the balance in the database.
     */
    public void depositFunds() {
        //Get the account that the user would like to deposit their funds into.
        System.out.println("Which account would you like to deposit your funds?");
        DisplayAccounts displayAccounts = new DisplayAccounts(this.userId);
        //Displays their accounts to make it easier for them to pick the correct account id
        displayAccounts.display();
        System.out.println("Please type in the account number next to the account you would like to " +
                "deposit your funds into.");
        accountID = this.input.nextInt();
        input.nextLine();
        //Prevents the user from inputting a negative number
        noNegativeNumbers = moveFunds.noNegativeNums(accountID);
        if(!noNegativeNumbers) {
            return;
        }

        //Gets the largest account ID currently in the database and check to make sure the inputted account is
        //not larger than the largest account number.
        int currentMaxAccountId = accountsDAO.getAccountId();
        if(currentMaxAccountId < accountID) {
            System.out.println("That account does not exist! Please review your accounts to obtain the desired" +
                    "account number.");
            return;
        }

        //Takes the userID and makes sure that the account belongs to the current user
        boolean accountsMatch = verifyOtherUsers.verifyAccountIdMatchesCurrentUser(this.userId, accountID);

        //If the accounts match, then the account ID is set to the inputted account ID
        if(accountsMatch){
            accounts = accountsDAO.getItem(accountID);
            accounts.setId(accountID);
        } else {
            //Alerts the user that they chose and account that does not belong to then and returns to the previous menu.
            System.out.println("The chosen account number does not match any of you accounts.\n" +
                    "Please review your accounts for the correct account number(s).");
            return;
        }

        //Get the deposit amount.
        System.out.println("How much money would you like to deposit?");
        double depositAmount = this.input.nextDouble();
        input.nextLine();

        //Prevents the user from inputting negative numbers
        noNegativeNumbers = moveFunds.noNegativeNums(depositAmount);
        if(!noNegativeNumbers) {
            return;
        }

        //Format the balance so that it starts with a dollar sign and has commas in the appropriate places.
        formattedDepositAmount = accounts.formatBalance(depositAmount);


        newBalance = accounts.getBalance() + depositAmount;

        //Checks if the deposit amount is within the limits set in the database
        if(Double.toString(depositAmount).length() > 11){
            System.out.println("The deposit amount is too large.\nYou cannot deposit any amount that has more " +
                    "than 8 digits left of the decimal point and more than two digits to the right of the " +
                    "decimal point.\n");
            return;
        }
        accounts.setBalance(newBalance);
        accountsDAO.save(accounts);
        accountType = accounts.getAccountType();
        System.out.println("You have successfully deposited " + formattedDepositAmount + " from " +
                accountType + " Account: " + accountID);
        displayAccounts.display();
    }
}
