package exceptions;

public class UserNameNotFound extends Exception{
    public UserNameNotFound() {
        super("Username does not exist. Please register for an account.");
    }
}
