package coursefy.persistence;

import coursefy.application.dto.UserDTO;
import coursefy.domain.Enrollment;
import coursefy.domain.EnrolmentId;
import coursefy.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, String> {
    Optional<Enrollment> findEnrollmentById(EnrolmentId id);

    @Query("select e " +
            "from enrollments e " +
            "where e.id.courseId like :title and e.id.userId like :username")
    Optional<Enrollment> findEnrollmentByCourseAndUser(@Param("title") String courseId, @Param("username") String userId);
    List<Enrollment> findEnrollmentsByUser(User user);

    @Query("select new edu.tecnocampus.labinternet.coursefy.application.dto.UserDTO(e.user) " +
            " from enrollments e "+
            "where e.id.courseId like :title and e.dateUpdate >= :startDate")
    List<UserDTO> findEnrollmentsByCourse(@Param("title") String courseId, @Param("startDate") LocalDate date);

    @Query( "select new edu.tecnocampus.labinternet.coursefy.application.dto.UserDTO(u) " +
            "from enrollments e join e.user u join e.course c " +
            "where SIZE(e.completedLessons) = SIZE(c.lessonList) " +
            "group by u " +
            "order by COUNT(e) ")
    List<UserDTO> findTopStudentsFinishedCourses(Pageable pageable);
}
