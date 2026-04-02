package coursefy.application.exception;

public class EnrollmentIdDuplicateException extends RuntimeException{

    public EnrollmentIdDuplicateException(String message) {
        super("Enrollment is already done");
    }
}
