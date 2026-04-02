package coursefy.application.exception;

public class CantMakeReview extends RuntimeException {
    public CantMakeReview(String message) {
        super (message);
    }
}
