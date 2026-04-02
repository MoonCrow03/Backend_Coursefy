package coursefy.application.exception;

public class CourseNameDuplicateException extends RuntimeException {
    public CourseNameDuplicateException(String message) {
        super("Course already exist");
    }
}
