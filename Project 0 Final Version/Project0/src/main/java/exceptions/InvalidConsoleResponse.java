package exceptions;

public class InvalidConsoleResponse extends Exception{

    public InvalidConsoleResponse(String message) {
        super("That was not a valid console response.\n" + message);
    }
}
