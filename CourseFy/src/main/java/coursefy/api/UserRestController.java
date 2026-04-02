package coursefy.api;

import coursefy.application.UserService;
import coursefy.application.dto.CourseDTO;
import coursefy.application.dto.CourseFromUserDTO;
import coursefy.application.dto.TeacherDTO;
import coursefy.application.dto.UserDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserRestController {
    private UserService userService;

    public UserRestController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO createUser (@RequestBody @Valid UserDTO userDTO){
        return userService.createUser(userDTO);
    }

    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    // This method allows to view the lessons listed in order by the teacher and the completed lessons simultaneously
    @GetMapping("/users/{userId}/courses/{courseId}")
    public CourseFromUserDTO getTakenCourse(@PathVariable String userId, @PathVariable String courseId) { return userService.getTakenCourse(userId, courseId);}

    @GetMapping("/users/searchByCtgOrLan") // /users/search?ctgId=id1,id2 & ctgId=id3,id4
    public List<CourseDTO> listCoursesByCategoriesAndOrLanguages(@RequestParam(name="ctgId") Optional<List<Long>> ctgId, @RequestParam(name="lanId")Optional<List<Long>> lanId){
        return userService.listCoursesByCategoriesAndOrLanguages(ctgId, lanId);
    }

    @GetMapping("/users/searchByUpd")
    public List<CourseDTO> listCoursesByLastUpdate(@RequestParam(name="username") String username){
        return userService.listCoursesByUpdateDate(username);
    }

    @GetMapping("/users/{userId}/courses")
    public List<CourseFromUserDTO> listTakenCourses(@PathVariable String userId){
        return userService.listTakenCourses(userId);
    }

    @GetMapping("/users/{userId}/courses/finished")
    public List<CourseFromUserDTO> listFinishedCourses(@PathVariable String userId){
        return userService.listFinishedCourses(userId);
    }

    @GetMapping("/users/courses/{courseId}/enrolled")
    public List<UserDTO> listUsersEnrolledInCourse(@PathVariable String courseId){
        return userService.listUsersEnrolledInCourse(courseId);
    }

    @GetMapping("/users/bestTeachers")
    public List<TeacherDTO> listBestTeachers(@RequestParam(name="n") int topX, @RequestParam(name="y") int year){
        return userService.listBestTeachers(topX, year);
    }

    @GetMapping("/users/topStudents")
    public List<UserDTO> listBestTeachers(@RequestParam(name="n") int topX){
        return userService.listTopStudents(topX);
    }
}
