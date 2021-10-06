package accounts;

import daos.AccountsDAO;
import daos.UserAccountDAO;
import exceptions.InvalidConsoleResponse;
import menus.MainMenu;
import models.Accounts;
import models.UserAccounts;

import java.util.Scanner;

public class CreateAccount {
    Scanner input;
    int accountId;
    int userId;
    MainMenu menu = new MainMenu();
    Accounts accounts = new Accounts();
    AccountsDAO accountsDAO = new AccountsDAO();
    UserAccountDAO userAccountDAO = new UserAccountDAO();
    UserAccounts userAccounts = new UserAccounts();

    /**
     * This is the constructor for the CreateAccount class
     * @param userID This is the user ID for the person currently logged in.
     */
    public CreateAccount(int userID) {
        this.input = menu.getScanner();
        this.userId = userID;
    }

    /**
     * This method Creates either a checking or savings account, depending on the user input, and sets the
     * balance of that account to $0.00.
     */
    public void newAccount() {
        //Checks to see if there are any rows that have an account_id of 0, signifying that someone has registered but
        //has not created an account yet.
        boolean run = true;
        String inputAccountType;
        accountId = (accountsDAO.getAccountId() + 1);
        int junctionId = userAccountDAO.getJunctionIdAfterRegistering(this.userId);
        //If a row does need to be updated and the current user's ID matches the user ID in the row, then the
        //junction id will be changed so that row can be updated.
        if(junctionId != 0) {
            userAccounts.setId(junctionId);
        }
        //Sets the user ID and the account ID and saves that information to the database
        userAccounts.setUser_id(this.userId);
        userAccounts.setAccount_id(accountId);
        accounts.setId(accountId);
        userAccountDAO.save(userAccounts);

        while (run) {
            //Get the account type and set the balance equal to $0.00
            System.out.println("What kind of account would you like to make?\nPlease enter either 1 or 2." +
                    "\n1) Checking\n2) Savings");
            inputAccountType = this.input.nextLine();
            if (inputAccountType.equals("1") || inputAccountType.equals("2")) {
                switch(inputAccountType){
                    case "1":
                        accounts.setAccountType("Checking");
                        accounts.setBalance(0.00);
                        accountsDAO.save(accounts);
                        run = false;
                        break;
                    case "2":
                        accounts.setAccountType("Savings");
                        accounts.setBalance(0.00);
                        accountsDAO.save(accounts);
                        run = false;
                        break;
                }

            } else { //This statement gives the user an error and then sends them back to the Login menu
                try {
                    throw new InvalidConsoleResponse("This menu only accepts the responses: 1, 2, 3, or 4.");
                } catch (InvalidConsoleResponse invalidConsoleResponse) {
                    invalidConsoleResponse.printStackTrace();
                }
                System.out.println("Please type 1, 2, 3, or 4 as an integer.\n");
                return;
            }
        }
        //Lets the user know that they were able to make a new account
        System.out.println("Congratulations on making an account! If you wish to add funds to your new account," +
                "the please choose the \"Move Funds\" option in the next menu.");
    }


}