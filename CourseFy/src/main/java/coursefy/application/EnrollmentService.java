package coursefy.application;

import coursefy.application.dto.CourseDTO;
import coursefy.application.dto.EnrollmentDTO;
import coursefy.application.dto.UserDTO;
import coursefy.application.exception.*;
import coursefy.domain.Enrollment;
import coursefy.domain.Lesson;
import edu.tecnocampus.labinternet.coursefy.application.exception.*;
import edu.tecnocampus.labinternet.coursefy.domain.*;
import coursefy.persistence.CourseRepository;
import coursefy.persistence.EnrollmentRepository;
import coursefy.persistence.LessonRepository;
import coursefy.persistence.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EnrollmentService {
    private EnrollmentRepository enrollmentRepository;
    private CourseService courseService;
    private LessonRepository lessonRepository;
    private UserService userService;
    private CourseRepository courseRepository;
    private UserRepository userRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository, CourseService courseService, UserService userService, LessonRepository lessonRepository, CourseRepository courseRepository, UserRepository userRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.courseService = courseService;
        this.userService = userService;
        this.lessonRepository = lessonRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    public EnrollmentDTO createEnrollment(EnrollmentDTO enrollmentDTO, String courseId, String userId){
        CourseDTO courseDTO = courseService.getCourse(courseId);
        UserDTO userDTO = userService.getUser(userId);

        // If user has already bought the course
        if (enrollmentRepository.findEnrollmentByCourseAndUser(courseId, userId).isPresent()) {
            throw new EnrollmentIdDuplicateException("Enrollment already exists");
        }

        Enrollment enrollment = new Enrollment(enrollmentDTO, courseDTO, userDTO);

        enrollmentRepository.save(enrollment);
        return new EnrollmentDTO(enrollment);
    }

    @Transactional
    public EnrollmentDTO markLessonDone(String userId, String courseId, String lessonName) {
        Enrollment enrollment = enrollmentRepository.findEnrollmentByCourseAndUser(courseId, userId)
                .orElseThrow(() -> new EnrollmentNotFoundException("Enrollment not found"));
        Lesson lesson = lessonRepository.findLessonByName(lessonName)
                .orElseThrow(() -> new LessonNotFoundException("Lesson not found"));

        // If user has finished all lessons
        if (enrollment.allLessonsDone()) {
            throw new AllLessonsDoneException("All lessons are done");
        }

        // Check if user is purchasing lessons in order
        if (!enrollment.checkLessonToMark(lesson)) {
            throw new StillLessonsLeftToDoException("You still have lessons to do");
        }

        // Mark lessons as done
        enrollment.markLessonDone(lesson);

        return new EnrollmentDTO(enrollment);
    }
}
