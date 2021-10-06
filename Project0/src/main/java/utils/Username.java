package utils;

import daos.UsersDAO;
import exceptions.UserNameNotFound;
import exceptions.UsernameIsNotValid;
import models.Users;
import project0list.BLArrayList;



/**
 * This class contains all methods pertaining to obtaining and verifying a username from the console.
 */
public class Username {
    String username;
    Users user = new Users();


    public Username(String usernameInput) {
        this.username = usernameInput;
    }

    /**
     * This method verifies the username and gets the user_id.
     * @return Returns user_id stored in the database.
     */
    public int getUserID() {
        //Checks if inputted username is a valid username
        //Set userId equal to -1 so that I will know if something went wrong this the code in the try block.
        int userId = -1;
        try {
            user.checkName(this.username, "username");
            userId = usernameInDatabase();
        } catch (UsernameIsNotValid userNameIsNotValid) {
            userNameIsNotValid.printStackTrace();
        }
        return userId;
    }

    /**
     * This method checks if the username is in the database.
     * @return Returns the index associated with the username
     */
    public int usernameInDatabase() {
        BLArrayList<Users> userArray;
        UsersDAO usersDAO = new UsersDAO();
        userArray = usersDAO.getAllItems();
        int id;
        for (int i = 0; i < userArray.len; i++) {
            //Gets the username column
            String databaseUsername = userArray.get(i).getUsername();
            if (databaseUsername.equals(this.username)) {
                //If id = anything other than -1, then the username is in the database.
                id = userArray.get(i).getId();
                return id;
            }
        }
        id = -1;
        try{
            throw new UserNameNotFound();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }
}
