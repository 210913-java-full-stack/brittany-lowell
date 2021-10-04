package utils;

import daos.UsersDAO;
import exceptions.UsernameIsNotValid;
import models.Users;
import project0list.BLArrayList;



/**
 * This class contains all methods pertaining to obtaining and verifying a username from the console.
 */
public class Username {
    private int id;
    private String username;
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
        int userId = -1;//Set userId equal to -1 so that I will know if something went wrong this the try below code.
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
        for (int i = 0; i < userArray.len; i++) {
            String databaseUsername = userArray.get(i).getUsername();//gets the username column
            if (databaseUsername.equals(this.username)) {
                id = userArray.get(i).getId();//If id = anything other than -1 or -2, then the username is in the database.
                return id;
            } else {
                id = -1;
            }
        }
        return id;
    }



}
