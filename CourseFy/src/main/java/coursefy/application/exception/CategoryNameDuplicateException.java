package coursefy.application.exception;

public class CategoryNameDuplicateException extends RuntimeException {
    public CategoryNameDuplicateException(String message) {
        super("Category already exist");
    }
}
