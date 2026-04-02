package coursefy.persistence;

import coursefy.application.dto.CourseDTO;
import coursefy.application.dto.CourseSearchDTO;
import coursefy.application.dto.TeacherDTO;
import coursefy.domain.Course;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
    Optional<Course> findCourseByTitle(String title);

    @Query("select new edu.tecnocampus.labinternet.coursefy.application.dto.CourseDTO(c) " +
            "from  courses c " +
            "where c.available = true order by c.title")
    List<CourseDTO> findByAvailableAndOrderByTitleAsc();

    @Query("select new edu.tecnocampus.labinternet.coursefy.application.dto.CourseSearchDTO(c.title, c.description) " +
            "from courses c " +
            "where lower(c.title) like lower(concat('%','q','%')) or " +
                "lower(c.description) like lower(concat('%','q','%')) " +
            "order by c.title")
    List<CourseSearchDTO> findByTitleOrDescription(@Param("q") String q);

    @Query( "select new edu.tecnocampus.labinternet.coursefy.application.dto.CourseDTO(c)" +
            "from courses c join c.category a  " +
                            "join c.language l " +
            "where (:ctgId IS NULL OR a.id IN :ctgId) and (:lanId IS NULL OR l.id IN :lanId)")
    List<CourseDTO> findAllByCategoryOrLanguage(@Param("ctgId") List<Long> ctgId, @Param("lanId") List<Long> lanId);

    @Query( "select new edu.tecnocampus.labinternet.coursefy.application.dto.CourseDTO(c)" +
            "from courses c join c.user u  " +
            "where u.username like :username " +
            "order by c.dateLastUpdate desc ")
    List<CourseDTO> findAllByUserOrderByDateLastUpdate(@Param("username") String username);

    @Query( "select new edu.tecnocampus.labinternet.coursefy.application.dto.TeacherDTO(c.user, AVG(r.satisfactionDegree)) " +
            "from courses c join c.user u join c.reviews r " +
            "where YEAR(r.creationDate) = :year " +
            "group by u " +
            "order by AVG(r.satisfactionDegree) desc ")
    List<TeacherDTO> findTopTeacherByYearOrderByAvgReview(@Param("year") int year, Pageable pageable);
}


