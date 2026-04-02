package coursefy.application.exception;

public class NotAvailableCourseException extends RuntimeException{
    public NotAvailableCourseException(String message) {
        super(message);
    }
}
