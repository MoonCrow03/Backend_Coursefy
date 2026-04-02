package coursefy.application.exception;

public class UserNameDuplicateException extends RuntimeException{
    public UserNameDuplicateException(String message) {
        super(message);
    }
}
