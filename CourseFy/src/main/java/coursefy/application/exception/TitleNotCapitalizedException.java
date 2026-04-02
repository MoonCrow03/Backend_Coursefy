package coursefy.application.exception;

public class TitleNotCapitalizedException extends RuntimeException {
    public TitleNotCapitalizedException(String message) {
        super(message);
    }
}