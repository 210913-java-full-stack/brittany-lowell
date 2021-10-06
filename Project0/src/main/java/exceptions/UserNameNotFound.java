package exceptions;

public class UserNameNotFound extends Exception{
    public UserNameNotFound() {
        super("That username does not exist in the database.");
    }
}
