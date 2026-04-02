package coursefy.api;

import coursefy.application.CourseService;
import coursefy.application.dto.CourseDTO;
import coursefy.application.dto.CourseSearchDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseRestController {
    private CourseService courseService;
    public CourseRestController (CourseService courseService){this.courseService = courseService;}

    @PostMapping("/courses")
    @ResponseStatus(HttpStatus.CREATED)
    public CourseDTO createCourse (@RequestBody @Valid CourseDTO courseDTO) {
        return courseService.createCourse(courseDTO);
    }

    @PutMapping("/courses/{id}/price")
    public CourseDTO updatePrice (@PathVariable String id,@RequestParam(name="q") float price) {
        return courseService.updatePrice(id, price);
    }

    @PutMapping("/courses/{id}/availability")
    public CourseDTO updateAvailability(@PathVariable String id) {
        return courseService.updateAvailability(id);
    }

    @PutMapping("/courses/{id}")
    public CourseDTO updateCourse (@PathVariable String id, @RequestBody CourseDTO courseDTO){
        return courseService.updateCourse(id, courseDTO);
    }

    @GetMapping("/courses/{id}")
    public CourseDTO getCourse(@PathVariable String id) {
        return courseService.getCourse(id);
    }

    @GetMapping("/courses")
    public List<CourseDTO> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/courses/available")
    public List<CourseDTO> listAvailableCourses() {
        return courseService.listAvailableCourses();
    }

    @GetMapping("/courses/search")
    public List<CourseSearchDTO> listCoursesByTitleOrDescription(@RequestParam(name="q") String q) { return courseService.listCoursesWithTitleOrDescription(q);}

    @DeleteMapping("/courses")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllCourses() {
        courseService.removeAllCourses();
    }

    @DeleteMapping("/courses/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourse(@PathVariable String id){
        courseService.removeCourse(id);}
}
