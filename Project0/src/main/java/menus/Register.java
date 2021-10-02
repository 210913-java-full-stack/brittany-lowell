package menus;

/**
 * This class takes the user's name (first and last), username, and password. It then saves this info to the database
 * and then calls the runLogin method to start the login process.
 */
/*
Exiting from login may send the user back to this class. If that happens, then remove the call to runLogin and
make the user chose login manually.
 */
public class Register {

    public Register() {
    }

    public void runRegister(){
        //Username
        System.out.println("Please enter a username.\nNOTE: Usernames cannot contain any special characters " +
                                "and must be 5 to 20 characters long.");

        //Password
        System.out.println("Please enter a password.\nNOTE: Passwords must have one capital letter, one lowercase" +
                "letter, and one number.\n Passwords must also be ");
    }

}
