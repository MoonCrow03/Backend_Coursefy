package coursefy.application.exception;

public class StillLessonsLeftToDoException extends RuntimeException {
    public StillLessonsLeftToDoException(String message) {
        super (message);
    }
}
