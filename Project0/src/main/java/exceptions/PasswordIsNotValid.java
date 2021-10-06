package exceptions;

public class PasswordIsNotValid extends Exception{

    public PasswordIsNotValid(int length){
        super("INVALID PASSWORD\nPlease try again.");
        if(length > 20 || length < 5){
            System.out.println("Please enter a password that is between 8 and 20 characters!");
        }
    }

    public PasswordIsNotValid(){
        super("INVALID PASSWORD\nPlease try again.");
        System.out.println("Passwords must have at least one capital letter, one lowercase letter, and one number.");
    }

    public PasswordIsNotValid(boolean specialCharactersPresent){
        super("INVALID PASSWORD\nPlease try again.");
        if(specialCharactersPresent){
            System.out.println("Passwords cannot contain any special characters!\n Please enter a " +
                    "password that does not contain any special characters!");
        }
    }
}
