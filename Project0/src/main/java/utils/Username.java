package utils;

import daos.UsersDAO;
import exceptions.UserNameIsNotValid;
import exceptions.UserNameNotFound;
import jdk.jfr.internal.tool.Main;
import menus.MainMenu;
import menus.Register;
import models.Users;
import project0list.BLArrayList;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.util.Scanner;

/*
        To take user input in as a StringBuffer, use the following lines of code.
        StringBuffer s = new StringBuffer();
        s.append(this.input.nextLine()); <= appends the console input to a StringBuffer
         */

/**
 * This class contains all methods pertaining to obtaining and verifying a username from the console.
 */
public class Username {
    private Connection conn;
    private int id;
    StringBuffer username = new StringBuffer();
    Users user = new Users();
    Register register = new Register();
    MainMenu menu = new MainMenu();


    public Username() {
        this.conn = menu.getConn();
    }

    public int getUserID(String string){
        usernameValid(string);
        return usernameInDatabase();
    }

    /**
     * This method gets the user input and checks if: The username is between 5 and 20 characters long; and if the
     * username does not contain any special characters.
     * @return If the username is valid, then this method will return a StringBuffer containing the user input.
     */
    private void usernameValid(String inputString){

        //Checks if inputted username is a valid username
            try {
                boolean check = checkUsername(inputString);
            } catch (UserNameIsNotValid userNameIsNotValid) {
                MainMenu mainMenu = new MainMenu();
                mainMenu.viewMainMenu();
                //userNameIsNotValid.printStackTrace();
        }
        username.append(inputString);
    }

    /**
     * This method checks if the username is in the database.
     * @return Returns the index associated with the username
     */
    private int usernameInDatabase() {
        String usernameExist = username.toString();
        BLArrayList<Users> userArray;
        UsersDAO usersDAO = new UsersDAO();
        userArray = usersDAO.getAllItems();
        for (int i = 0; i < userArray.len; i++) {
            String string = userArray.get(i).getUsername();//gets the username column
            if (string.equals(usernameExist)) {
                id = userArray.get(i).getId();//If id = anything other than -1 or -2, then the username is in the database.
                return id;
            }
            id = -1;
        }
        try {
            if (id == -1) {
                throw new UserNameNotFound();
            }
        } catch (UserNameNotFound userNameNotFound) {
            register.runRegister();
        }
        return id;
    }


        /**
     * This method checks if the username contains any special characters and if it is between 5 and 20 characters long.
     * @return Returns true if the username matches the requirements (5-20 characters and no special characters).
     */
    private boolean checkUsername(String inputString) throws UserNameIsNotValid {

        if(inputString.length() < 5 || inputString.length() > 20 ) {
            throw new UserNameIsNotValid(inputString.length());
        } else {
            byte[] bytes = inputString.getBytes(StandardCharsets.US_ASCII);
            for(int i = 0; i < bytes.length; i++){
                if(bytes[i] >= 32 && bytes[i] <= 47){
                    throw new UserNameIsNotValid(true);
                }
            }
            return true;
        }

    }


}
