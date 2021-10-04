package accounts;

import daos.AccountsDAO;
import daos.UserAccountDAO;
import menus.Login;
import menus.MainMenu;
import models.Accounts;
import models.UserAccounts;
import project0list.BLArrayList;

public class DisplayAccounts {
    private int userId;
    Accounts accounts = new Accounts();
    Login login = new Login();
    StringBuffer formattedBalance = new StringBuffer();
    UserAccountDAO userAccountDAO = new UserAccountDAO();
    AccountsDAO accountsDAO = new AccountsDAO();


    public DisplayAccounts(int userID) {
        this.userId = userID;
    }

    /**
     * This method displays the account balances for the user.
     */
    public void display(){
        //Retrieve account information
        BLArrayList<UserAccounts> accountIds = userAccountDAO.getItem(this.userId);

        System.out.println("\n============Accounts============");
        //Print out all the items in the list
        for(int i = 0; i < accountIds.len; i++){
            int accountId = accountIds.get(i).getAccount_id();

            accounts = accountsDAO.getItem(accountId);
            if(accounts == null){
                System.out.println("You do not currently have any Checking or Savings accounts.\n" +
                        "Please create a new account first.");
                break;
            }
            String accountType = accounts.getAccountType();
            double balance = accounts.getBalance();
            formattedBalance = accounts.formatBalance(balance);

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
