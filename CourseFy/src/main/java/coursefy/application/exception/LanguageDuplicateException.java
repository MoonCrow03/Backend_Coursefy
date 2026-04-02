package coursefy.application.exception;

public class LanguageDuplicateException extends RuntimeException{
    public LanguageDuplicateException(String message) {
        super("Language already exist");
    }
}
