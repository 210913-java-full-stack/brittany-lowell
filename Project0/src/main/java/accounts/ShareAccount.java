package accounts;

import daos.UserAccountDAO;
import menus.MainMenu;
import models.UserAccounts;
import utils.VerifyOtherUsers;

import java.util.Scanner;

public class ShareAccount {
    Scanner input;
    int userId;
    int accountID;


    UserAccounts userAccounts = new UserAccounts();
    MainMenu mainMenu = new MainMenu();
    VerifyOtherUsers verifyOtherUsers = new VerifyOtherUsers();
    UserAccountDAO userAccountDAO = new UserAccountDAO();

    /**
     * Constructor for the ShareAccounts class
     * @param userID Requires the current user's user ID.
     */
    public ShareAccount(int userID) {
        this.input = mainMenu.getScanner();
        this.userId = userID;
    }

    /**
     * Create a joined bank account between two users.
     */
    public void jointAccount() {
        //Get the account number
        System.out.println("Which account would you like to share with another user?");
        DisplayAccounts displayAccounts = new DisplayAccounts(this.userId);
        displayAccounts.display();
        System.out.println("Please type in the account number next to the account you would like to turn into " +
                "a joint account.");
        accountID = this.input.nextInt();

        //Verifies that the second user matches the info the first user provided.
        boolean match = verifyOtherUsers.verifyOtherUser(userAccounts);

        //If not alert the user and return to the previous menu.
        if(!match){
            System.out.println("The given information does not match the information associated with the" +
                    "provided user ID.");
            return;
        }
        //Adding the new user to the user_accounts table
        userAccounts.setAccount_id(accountID);
        userAccountDAO.save(userAccounts);
    }
}
