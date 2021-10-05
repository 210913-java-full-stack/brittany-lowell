package utils;

import daos.UserAccountDAO;
import daos.UsersDAO;
import exceptions.UsernameIsNotValid;
import menus.MainMenu;
import menus.MoveFunds;
import models.UserAccounts;
import models.Users;
import project0list.BLArrayList;

import java.util.Scanner;

public class VerifyOtherUsers {
    private Scanner input;
    int inputtedUserID;


    Users users = new Users();
    MainMenu mainMenu = new MainMenu();
    MoveFunds moveFunds = new MoveFunds();
    UsersDAO usersDAO = new UsersDAO();
    UserAccountDAO userAccountDAO = new UserAccountDAO();
    BLArrayList<UserAccounts> userAccountsBLArrayList = new BLArrayList<>();
    BLArrayList<String> newUserInfo = new BLArrayList<>();

    public VerifyOtherUsers() {
        this.input = mainMenu.getScanner();
    }

    public boolean verifyOtherUser(UserAccounts newUserAccount) {
        getUserInfo(newUserAccount);
        inputtedUserID = Integer.parseInt(newUserInfo.get(2));
       /*
        Checking if the first name, last name, and user ID combination match in the database
        If the first and last name provided do not match the first and last name that are associated with the
        provided user ID, then the user will be alerted that they need to reenter the other user's information.
         */
        users = usersDAO.getItem(inputtedUserID);
        String firstNameInDatabase = users.getFirstName();
        String lastNameInDatabase = users.getLastName();

        boolean firstNameMatch = firstNameInDatabase.equals(newUserInfo.get(0));
        boolean lastNameMatch = lastNameInDatabase.equals(newUserInfo.get(1));

        if (!firstNameMatch || !lastNameMatch) {
            System.out.println("The first and last name do not match the names associated with the provided " +
                    "userId.\nPlease reenter the information for the person you would like to share" +
                    "this account with.");
            return false;
        }
        return true;
    }


    public void getUserInfo(UserAccounts newUserAccount){
        //Get the other user's first and last name to validate that the correct userID was entered
        //Getting the first name
        System.out.println("Please enter the first name of the person you would like to add to this account.");
        String firstName = this.input.nextLine();
        //Checking if the first name is a valid name
        boolean thisIsAValidName;
        try {
            thisIsAValidName = users.checkName(firstName, "name");
        } catch (UsernameIsNotValid usernameIsNotValid) {
            usernameIsNotValid.printStackTrace();
            return;
        }
        if (!thisIsAValidName) {
            return;
        }

        //Getting the last name
        System.out.println("Please enter the last name of the person who you would like to share this account with.");
        String lastName = this.input.nextLine();
        //Checking if the last name is a valid name
        try {
            thisIsAValidName = users.checkName(lastName, "name");
        } catch (UsernameIsNotValid usernameIsNotValid) {
            usernameIsNotValid.printStackTrace();
            return;
        }
        if (!thisIsAValidName) {
            return;
        }

        //Getting the other user's user ID
        System.out.println("Please enter their user ID.");
        int newUserID = this.input.nextInt();
        this.input.nextLine();

        boolean noNegativeNumbers = moveFunds.noNegativeNums(newUserID);
        if (!noNegativeNumbers) {
            return;
        }

        int maxUserId = usersDAO.getUserId();
        if (inputtedUserID > maxUserId) {
            System.out.println("That userID does not exist! Please try again!");
            return;
        }
        newUserAccount.setUser_id(newUserID);
        String newStringUserID = Integer.toString(newUserID);
        newUserInfo.add(firstName);
        newUserInfo.add(lastName);
        newUserInfo.add(newStringUserID);
    }

    public boolean verifyAccountIdMatchesCurrentUser(int userID, int accountID) {
        userAccountsBLArrayList = userAccountDAO.getItem(userID);

        boolean check = false;
        for (int i = 0; i < userAccountsBLArrayList.len; i++) {
            int checkAccountId = userAccountsBLArrayList.get(i).getAccount_id();
            if (checkAccountId == accountID) {
                check = true;
                break;
            }
        }
        return check;
    }
}
