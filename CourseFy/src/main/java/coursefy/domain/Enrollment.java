package coursefy.domain;

import coursefy.application.dto.CourseDTO;
import coursefy.application.dto.EnrollmentDTO;
import coursefy.application.dto.UserDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.*;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "enrollments")
@Table(name = "enrollments")
public class Enrollment {

    @EmbeddedId
    private EnrolmentId id;

    private LocalDate dateEnrollment;

    private LocalDate dateUpdate;
    private float price;

    @ManyToOne
    @JoinColumn(name = "course_id")
    @MapsId("courseId")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @MapsId("userId")
    private User user;

    @ManyToMany
    private Set<Lesson> completedLessons = new HashSet<>();

    public Enrollment(EnrollmentDTO enrollmentDTO, CourseDTO courseDTO, UserDTO userDTO){
        id = new EnrolmentId(courseDTO.getTitle(), userDTO.getUsername());
        price = enrollmentDTO.getPrice();

        dateEnrollment = LocalDate.now();
        dateUpdate = null;

        course = new Course(courseDTO);
        user = new User(userDTO);

        for (Lesson lesson: course.getLessonList()) {
            lesson.addEnrollment(this);
        }
    }

    public boolean canMakeReview(){
        return completedLessons.size() > 0 && getCompletedLessons().size() >= course.getLessonList().size()/2;
    }

    public boolean allLessonsDone(){
        return completedLessons.size() == course.getLessonList().size();
    }

    public boolean checkLessonToMark(Lesson lesson){
        if(nextLessonToDo() != null){
            return nextLessonToDo().equals(lesson);
        }

        return false;
    }

    public void markLessonDone(Lesson lesson){
        dateUpdate = LocalDate.now();

        Lesson expectedLesson = nextLessonToDo();

        if(!allLessonsDone() && expectedLesson != null && lesson.equals(expectedLesson)){
            completedLessons.add(lesson);
        }
    }

    private Lesson nextLessonToDo(){
        return course.getLessonList().stream().filter(x -> !completedLessons.contains(x)).findFirst().orElse(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Enrollment that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(course, that.course) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, course, user);
    }
}
