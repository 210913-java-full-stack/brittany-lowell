package accounts;

import daos.AccountsDAO;
import daos.UserAccountDAO;
import exceptions.InvalidConsoleResponse;
import menus.Login;
import menus.MainMenu;
import models.Accounts;
import models.UserAccounts;

import java.util.Scanner;

public class CreateAccount {
    private Scanner input;
    private int accountId;
    private int userId;
    MainMenu menu = new MainMenu();
    Login login = new Login();
    Accounts accounts = new Accounts();
    AccountsDAO accountsDAO = new AccountsDAO();
    UserAccountDAO userAccountDAO = new UserAccountDAO();
    UserAccounts userAccounts = new UserAccounts();

    public CreateAccount(int userID) {
        this.input = menu.getScanner();
        this.userId = userID;
    }

    /**
     * This method gets the account information from the user.
     */
    public void newAccount() {
        boolean run = true;
        String inputAccountType;
        accountId = (accountsDAO.getAccountId(accounts) + 1);
        int junctionId = userAccountDAO.getJunctionIdAfterRegistering(this.userId);
        if(junctionId != 0) {
            userAccounts.setId(junctionId);
        }
        userAccounts.setUser_id(this.userId);
        userAccounts.setAccount_id(accountId);
        accounts.setId(accountId);
        userAccountDAO.save(userAccounts);

        while (run) {
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

            } else { //This statement gives the user an error and then sends them back to the "Account menu"
                try {
                    throw new InvalidConsoleResponse("This menu only accepts the responses: 1, 2, 3, or 4.");
                } catch (InvalidConsoleResponse invalidConsoleResponse) {
                    invalidConsoleResponse.printStackTrace();
                    System.out.println("Please type 1, 2, 3, or 4 as an integer.\n");
                }
            }
        }
        System.out.println("Congratulations on making an account! If you wish to add funds to your new account," +
                "the please choose the \"Move Funds\" option in the next menu.");
    }


}