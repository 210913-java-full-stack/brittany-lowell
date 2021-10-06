package accounts;

import daos.AccountsDAO;
import daos.UserAccountDAO;
import models.Accounts;
import models.UserAccounts;
import project0list.BLArrayList;

public class DisplayAccounts {
    int userId;

    Accounts accounts = new Accounts();
    StringBuffer formattedBalance = new StringBuffer();
    UserAccountDAO userAccountDAO = new UserAccountDAO();
    AccountsDAO accountsDAO = new AccountsDAO();

    /**
     * Constructor for the DisplayAccounts class.
     * @param userID Requires the current user's userID.
     */
    public DisplayAccounts(int userID) {
        this.userId = userID;
    }

    /**
     * This method displays the accounts and account balances for the user.
     */
    public void display(){
        //Retrieve account information
        BLArrayList<UserAccounts> accountIds = userAccountDAO.getItem(this.userId);

        System.out.println("\n============Accounts============");
        System.out.println("User ID: " + this.userId);
        //Print out all the items in the list
        for(int i = 0; i < accountIds.len; i++){
            int accountId = accountIds.get(i).getAccount_id();

            accounts = accountsDAO.getItem(accountId);
            //If the database is empty or the user registered and has not made a checking or savings account yet.
            if(accounts == null){
                System.out.println("You do not currently have any Checking or Savings accounts.\n" +
                        "Please create a new account first.");
                break;
            }
            //Gets the account type and balance from the database
            String accountType = accounts.getAccountType();
            double balance = accounts.getBalance();
            //Formats the balance
            formattedBalance = accounts.formatBalance(balance);

            //Print out the lines based on account type such that the balances and account IDs are level
            if(accountType.equals("Checking")){
                System.out.println("Checking account " + accountId + " balance:  " + formattedBalance);
            }
            else if(accountType.equals("Savings")) {
                System.out.println("Savings account  " + accountId + " balance:  " + formattedBalance);
            }
        }
        System.out.println("================================\n");

    }
}
