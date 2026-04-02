package coursefy.api;

import coursefy.application.LessonService;
import coursefy.application.dto.LessonDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/courses/{courseId}")
@RestController
public class LessonRestController {
    private LessonService lessonService;

    public LessonRestController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @PostMapping("/lessons")
    @ResponseStatus(HttpStatus.CREATED)
    public LessonDTO createLesson(@RequestBody @Valid LessonDTO lessonDTO, @PathVariable String courseId){
        return lessonService.createLesson(lessonDTO, courseId);
    }

    @PutMapping("/lessons/{lessonName}")
    public LessonDTO updateLesson(@RequestBody @Valid LessonDTO lessonDTO, @PathVariable String lessonName){
        return lessonService.updateLesson(lessonDTO, lessonName);
    }
}
