package coursefy.application;

import coursefy.application.dto.CourseDTO;
import coursefy.application.dto.CourseFromUserDTO;
import coursefy.application.dto.TeacherDTO;
import coursefy.application.dto.UserDTO;
import coursefy.application.exception.CourseNotFoundException;
import coursefy.application.exception.EnrollmentNotFoundException;
import coursefy.application.exception.UserNameDuplicateException;
import coursefy.domain.Course;
import coursefy.domain.Enrollment;
import coursefy.domain.User;
import coursefy.persistence.CourseRepository;
import coursefy.persistence.EnrollmentRepository;
import coursefy.persistence.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepository userRepository;
    private CourseRepository courseRepository;

    private EnrollmentRepository enrollmentRepository;

    public UserService(UserRepository userRepository, CourseRepository courseRepository, EnrollmentRepository enrollmentRepository){
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public UserDTO createUser (UserDTO userDTO) throws RuntimeException{
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new UserNameDuplicateException("User already exists");
        }

        User user = new User(userDTO);

        userRepository.save(user);
        return new UserDTO(user);
    }

    public UserDTO getUser(String id){
        return new UserDTO(userRepository.findByUsername(id).get());
    }

    public List<UserDTO> getAllUsers(){
        return userRepository.findAll().stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
    }

    public List<CourseDTO> listCoursesByCategoriesAndOrLanguages(Optional<List<Long>> ctgId, Optional<List<Long>> lanId){

        List<Long> ctgIdLong;
        if (ctgId.isEmpty())
            ctgIdLong = null;
        else
            ctgIdLong = ctgId.get();

        List<Long> lanIdLong;
        if (lanId.isEmpty())
            lanIdLong = null;
        else
            lanIdLong = lanId.get();

        return courseRepository.findAllByCategoryOrLanguage(ctgIdLong, lanIdLong);
    }

    public List<CourseDTO> listCoursesByUpdateDate(String username){
        return courseRepository.findAllByUserOrderByDateLastUpdate(username);
    }

    public CourseFromUserDTO getTakenCourse(String userId, String courseId) {
        Course course = courseRepository.findCourseByTitle(courseId).orElseThrow(() -> new CourseNotFoundException(courseId));

        Enrollment enrollment = enrollmentRepository.findEnrollmentByCourseAndUser(courseId, userId)
                .orElseThrow(() -> new EnrollmentNotFoundException("Enrollment not found"));

        return new CourseFromUserDTO(course, enrollment);
    }

    public List<CourseFromUserDTO> listTakenCourses(String userId) {
        User user = userRepository.findByUsername(userId).orElseThrow(() -> new UsernameNotFoundException(userId));
        List<Enrollment> enrollmentList = enrollmentRepository.findEnrollmentsByUser(user);

        return enrollmentList.stream()
                .map(enrollment -> new CourseFromUserDTO(enrollment.getCourse(), enrollment))
                .collect(Collectors.toList());
    }

    public List<CourseFromUserDTO> listFinishedCourses(String userId) {
        User user = userRepository.findByUsername(userId).orElseThrow(() -> new UsernameNotFoundException(userId));
        List<Enrollment> enrollmentList = enrollmentRepository.findEnrollmentsByUser(user).stream().filter(x -> x.allLessonsDone()).collect(Collectors.toList());

        return enrollmentList.stream()
                .map(enrollment -> new CourseFromUserDTO(enrollment.getCourse(), enrollment))
                .collect(Collectors.toList());
    }

    public List<UserDTO> listUsersEnrolledInCourse(String courseId){
        LocalDate twoMonthsAgo = LocalDate.now().minusMonths(2);
        return enrollmentRepository.findEnrollmentsByCourse(courseId, twoMonthsAgo);
    }

    public List<TeacherDTO> listBestTeachers(int topX, int year){
        Pageable pageable = PageRequest.of(0, topX);

        return courseRepository.findTopTeacherByYearOrderByAvgReview(year, pageable);
    }

    public List<UserDTO> listTopStudents(int topX){
        Pageable pageable = PageRequest.of(0, topX);

        return enrollmentRepository.findTopStudentsFinishedCourses(pageable);
    }

}
