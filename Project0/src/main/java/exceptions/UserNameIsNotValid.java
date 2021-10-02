package exceptions;

public class UserNameIsNotValid extends Exception{
    public UserNameIsNotValid(int length){
        super("INVALID USERNAME\nPlease try again.");
        if(length > 20 || length < 5){
            System.out.println("Please enter a username that is between 5 and 20 characters!");
        }
    }

    public UserNameIsNotValid(boolean specialCharactersPresent){
        super("INVALID USERNAME\nPlease try again.");
        if(specialCharactersPresent){
            System.out.println("Usernames cannot contain any special characters!\n Please enter a " +
                    "username that does not contain any special characters!");
        }
    }
}
