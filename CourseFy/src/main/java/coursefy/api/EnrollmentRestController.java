package coursefy.api;

import coursefy.application.EnrollmentService;
import coursefy.application.dto.EnrollmentDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/users/{userId}/courses/{courseId}")
@RestController
public class EnrollmentRestController {
    private EnrollmentService enrollmentService;
    public EnrollmentRestController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping("/enrollments")
    @ResponseStatus(HttpStatus.CREATED)
    public EnrollmentDTO createEnrollment(@RequestBody @Valid EnrollmentDTO enrollmentDTO, @PathVariable String courseId, @PathVariable String userId){
        return enrollmentService.createEnrollment(enrollmentDTO, courseId, userId);
    }

    @PutMapping("/enrollments/markLesson")
    public EnrollmentDTO markLessonDone(@PathVariable String courseId, @PathVariable String userId, @RequestParam(name = "q") String lessonName){
        return enrollmentService.markLessonDone(userId, courseId, lessonName);
    }
}
