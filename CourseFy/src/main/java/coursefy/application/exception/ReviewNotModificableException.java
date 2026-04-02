package coursefy.application.exception;

public class ReviewNotModificableException extends RuntimeException {
    public ReviewNotModificableException(String message) {
        super (message);
    }
}