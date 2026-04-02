package coursefy.application.exception;

public class LessonNameDuplicateException extends RuntimeException{
    public LessonNameDuplicateException(String message) {
        super("Lesson already exist");
    }
}
