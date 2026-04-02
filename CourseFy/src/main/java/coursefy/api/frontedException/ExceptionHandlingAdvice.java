package coursefy.api.frontedException;

import coursefy.application.exception.*;
import edu.tecnocampus.labinternet.coursefy.application.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlingAdvice {

    @ResponseBody
    @ExceptionHandler(CourseNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND) //Error 404
    String objectNotFoundHandler(Exception ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(org.springframework.dao.DuplicateKeyException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.CONFLICT) //Error 409
    String objectAlreadyExists(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(CourseNameDuplicateException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.CONFLICT) //Error 409
    String handleNameDuplicateException(CourseNameDuplicateException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(PriceCourseNegativeException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST) //Error 400
    String handleNegativePriceException(PriceCourseNegativeException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(TitleNotCapitalizedException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST) //Error 400
    String handleTitleNotCapitalizedException(TitleNotCapitalizedException exception) {
        System.out.println("No capital letter");
        return exception.getMessage();
    }

    @ExceptionHandler(CategoryNameDuplicateException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.CONFLICT) //Error 409
    String CategoryNameDuplicateException(CategoryNameDuplicateException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(LanguageDuplicateException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.CONFLICT) //Error 409
    String LanguageDuplicateException(LessonNameDuplicateException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(UserNameDuplicateException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.CONFLICT) //Error 409
    String UserNameDuplicateException(UserNameDuplicateException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(LessonNameDuplicateException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.CONFLICT) //Error 409
    String LessonNameDuplicateException(LessonNameDuplicateException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(EnrollmentIdDuplicateException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.CONFLICT) //Error 409
    String EnrollmentIdDuplicateException(EnrollmentIdDuplicateException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(EnrollmentNotFoundException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.NOT_FOUND) //Error 409
    String EnrollmentNotFoundException(EnrollmentNotFoundException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(NotModifiableCourseException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.CONFLICT) //Error 409
    String NotModifiableCourseException(NotModifiableCourseException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(NotAvailableCourseException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.CONFLICT) //Error 409
    String NotAvailableCourseException(NotAvailableCourseException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(AllLessonsDoneException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.CONFLICT) //Error 409
    String AllLessonsDoneException(AllLessonsDoneException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(LessonNotFoundException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.NOT_FOUND) //Error 409
    String LessonNotFoundException(LessonNotFoundException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(StillLessonsLeftToDoException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.CONFLICT) //Error 409
    String StillLessonsLeftToDoException(StillLessonsLeftToDoException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(CantMakeReview.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.CONFLICT) //Error 409
    String CantMakeReviewException(CantMakeReview exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(ReviewAlreadyExistException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.CONFLICT) //Error 409
    String ReviewAlreadyExistException(ReviewAlreadyExistException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.NOT_FOUND) //Error 404
    String ReviewNotFoundHandler(Exception ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(ReviewNotModificableException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.CONFLICT) //Error 409
    String ReviewNotModificableException(Exception ex) {
        return ex.getMessage();
    }

}
