package jpchs.spring_app.exception;

public class InvalidSortException extends RuntimeException {
    public InvalidSortException(String message) {
        super(message);
    }
}
