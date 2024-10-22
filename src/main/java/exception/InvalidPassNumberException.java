package exception;

public class InvalidPassNumberException extends Exception {
    public InvalidPassNumberException(String message) {
        super(message);
    }
}