package coursefy.application.exception;

public class NotModifiableCourseException extends RuntimeException{
    public NotModifiableCourseException(String message) {
        super(message);
    }
}
