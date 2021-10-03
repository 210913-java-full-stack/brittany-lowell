package exceptions;

public class PasswordNotFound extends Exception{
    public PasswordNotFound() {
        super("Password does not exist! Please try again.");
    }
}
