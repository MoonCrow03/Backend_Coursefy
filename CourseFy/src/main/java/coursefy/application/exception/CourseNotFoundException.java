package coursefy.application.exception;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(String message) {
        super("Course does not exist");
    }
}