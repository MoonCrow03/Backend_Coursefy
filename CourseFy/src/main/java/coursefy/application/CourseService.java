package coursefy.application;

import coursefy.application.dto.CourseDTO;
import coursefy.application.dto.CourseSearchDTO;
import coursefy.application.exception.*;
import edu.tecnocampus.labinternet.coursefy.application.exception.*;
import coursefy.domain.Course;
import coursefy.persistence.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {
    private CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository){
        this.courseRepository = courseRepository;
    }

    public CourseDTO createCourse (CourseDTO courseDTO) throws RuntimeException{

        if (courseRepository.findCourseByTitle(courseDTO.getTitle()).isPresent()) {
            throw new CourseNameDuplicateException("Course already exists");
        }

        Course course = new Course(courseDTO);

        if (!course.isTitleCapitalized(course.getTitle())) {
            throw new TitleNotCapitalizedException("Titles must begin with capital letter");
        }

        courseRepository.save(course);
        return new CourseDTO(course);
    }

    @Transactional
    public CourseDTO updateCourse(String courseId, CourseDTO courseDTO) throws NotModifiableCourseException, TitleNotCapitalizedException{
        Course course = courseRepository.findCourseByTitle(courseId).orElseThrow(() -> new CourseNotFoundException(courseId));

        if(course.isAvailable()){
            throw new NotModifiableCourseException("Course is not modifiable");
        }

        if (!course.isTitleCapitalized(course.getTitle())) {
            throw new TitleNotCapitalizedException("Titles must begin with capital letter");
        }

        course.updateCourse(courseDTO);
        return new CourseDTO(course);
    }

    @Transactional
    public CourseDTO updateAvailability(String courseId) {
        Course course = courseRepository.findCourseByTitle(courseId).orElseThrow(() -> new CourseNotFoundException(courseId));

        if(course.getLessonList().isEmpty()){
            throw new NotAvailableCourseException("Course can't be made available");
        }

        course.updateAvailability();
        return new CourseDTO(course);
    }

    @Transactional
    public CourseDTO updatePrice(String courseId, float price) throws PriceCourseNegativeException, NotModifiableCourseException {
        Course course = courseRepository.findCourseByTitle(courseId).orElseThrow(() -> new CourseNotFoundException(courseId));

        if(course.isAvailable()){
            throw new NotModifiableCourseException("Course is not modifiable");
        }

        if (price < 0) {
            throw new PriceCourseNegativeException("No negative price value");
        }

        course.updatePrice(price);
        return new CourseDTO(course);
    }

    public CourseDTO getCourse(String courseId) {
        Course course = courseRepository.findCourseByTitle(courseId).orElseThrow(() -> new CourseNotFoundException(courseId));
        return new CourseDTO(course);
    }

    public List<CourseDTO> getAllCourses(){
        return courseRepository.findAll().stream().map(x -> new CourseDTO(x)).collect(Collectors.toList());
    }

    public List<CourseDTO> listAvailableCourses(){
        return courseRepository.findByAvailableAndOrderByTitleAsc();
    }

    public List<CourseSearchDTO> listCoursesWithTitleOrDescription(String q){
        return courseRepository.findByTitleOrDescription(q);
    }

    public void removeAllCourses() {
        courseRepository.deleteAll();
    }

    public void removeCourse(String id) {
        courseRepository.deleteById(id);
    }
}