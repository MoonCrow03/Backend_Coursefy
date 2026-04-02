package coursefy.application;

import coursefy.application.dto.LessonDTO;
import coursefy.application.exception.CourseNotFoundException;
import coursefy.application.exception.LessonNameDuplicateException;
import coursefy.application.exception.LessonNotFoundException;
import coursefy.domain.Course;
import coursefy.domain.Lesson;
import coursefy.persistence.CourseRepository;
import coursefy.persistence.LessonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LessonService {
    private LessonRepository lessonRepository;
    private CourseRepository courseRepository;

    public LessonService(LessonRepository lessonRepository, CourseRepository courseRepository) {
        this.lessonRepository = lessonRepository;
        this.courseRepository = courseRepository;
    }

    public LessonDTO createLesson(LessonDTO lessonDTO, String courseId){

        if (lessonRepository.findLessonByName(lessonDTO.getName()).isPresent()) {
            throw new LessonNameDuplicateException("Lesson already exists");
        }

        Lesson lesson = new Lesson(lessonDTO);

        lessonRepository.save(lesson);
        Course course = courseRepository.findCourseByTitle(courseId).orElseThrow(() -> new CourseNotFoundException(courseId));
        course.addLesson(lesson);

        return new LessonDTO(lesson);
    }

    @Transactional
    public LessonDTO updateLesson(LessonDTO lessonDTO, String lessonName) {
        Lesson lesson = lessonRepository.findLessonByName(lessonName).orElseThrow(() -> new LessonNotFoundException("Lesson not found"));
        lesson.updateLesson(lessonDTO);
        return new LessonDTO(lesson);
    }
}
