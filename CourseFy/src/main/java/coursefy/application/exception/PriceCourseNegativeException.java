package coursefy.application.exception;

public class PriceCourseNegativeException extends RuntimeException {
    public PriceCourseNegativeException(String message) {
        super(message);
    }
}
