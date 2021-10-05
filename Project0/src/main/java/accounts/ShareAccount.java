package accounts;

import daos.UserAccountDAO;
import daos.UsersDAO;
import exceptions.UsernameIsNotValid;
import menus.MainMenu;
import menus.MoveFunds;
import models.Accounts;
import models.UserAccounts;
import models.Users;
import project0list.BLArrayList;
import utils.VerifyOtherUsers;

import java.util.Scanner;

public class ShareAccount {
    private Scanner input;
    private int userId;
    private boolean thisIsAValidName = true;
    private int accountID;


    UserAccounts userAccounts = new UserAccounts();
    MainMenu mainMenu = new MainMenu();
    VerifyOtherUsers verifyOtherUsers = new VerifyOtherUsers();
    UserAccountDAO userAccountDAO = new UserAccountDAO();

    public ShareAccount(int userID) {
        this.input = mainMenu.getScanner();
        this.userId = userID;
    }

    public void jointAccount() {
        //Get the account number
        System.out.println("Which account would you like to share with another user?");
        DisplayAccounts displayAccounts = new DisplayAccounts(this.userId);
        displayAccounts.display();
        System.out.println("Please type in the account number next to the account you would like to turn into " +
                "a joint account.");
        accountID = this.input.nextInt();

        boolean match = verifyOtherUsers.verifyOtherUser(userAccounts);

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
