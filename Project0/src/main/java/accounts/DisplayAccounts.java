package accounts;

import daos.AccountsDAO;
import daos.UserAccountDAO;
import menus.MainMenu;
import models.Accounts;
import models.UserAccounts;
import project0list.BLArrayList;

import java.sql.Array;
import java.sql.Connection;

public class DisplayAccounts {
    private int userId;
    private Connection conn;
    UserAccounts userAccounts = new UserAccounts();
    Accounts accounts = new Accounts();
    MainMenu menu = new MainMenu();
    StringBuffer formatedBalance = new StringBuffer();

    public DisplayAccounts(int userId) {
        this.userId = userId;
        this.conn = menu.getConn();

    }

    /**
     * This method displays the account balances for the user.
     */
    public void display(){
        //Retrieve account information
        UserAccountDAO userAccountDAO = new UserAccountDAO();
        AccountsDAO accountsDAO = new AccountsDAO();
        Accounts accounts = new Accounts();
        BLArrayList<UserAccounts> accountIds = userAccountDAO.getItem(this.userId);

        System.out.println("\n============Accounts============");
        //Print out all of the items in the list
        for(int i = 0; i < accountIds.len; i++){
            int accountId = accountIds.get(i).getAccount_id();

            accounts = accountsDAO.getItem(accountId);
            String accountType = accounts.getAccountType();
            double balance = accounts.getBalance();
            formatedBalance = accounts.formatBalance(balance);

            if(accountType.equals("Checking")){
                System.out.println("Checking account " + accountId + " balance:  " + formatedBalance);
            }
            else if(accountType.equals("Savings")) {
                System.out.println("Savings account " + accountId + " balance:   " + formatedBalance);
            }
        }
        System.out.println("================================\n");

    }
}
