package coursefy.application.exception;

public class AllLessonsDoneException extends RuntimeException {
    public AllLessonsDoneException(String message) {
        super (message);
    }
}