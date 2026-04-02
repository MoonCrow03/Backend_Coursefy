package coursefy.persistence;

import coursefy.application.dto.ReviewDTO;
import coursefy.domain.Course;
import coursefy.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {
    Optional<Review> findByTitle(String name);
    List<Review> findAllByCourse(Course course);

    @Query( "select new edu.tecnocampus.labinternet.coursefy.application.dto.ReviewDTO(r)" +
            "from courses c join c.reviews r " +
            "where c.title like :course_id " +
            "order by r.creationDate desc ")
    List<ReviewDTO> findAllByCourseOrderByCreationDateDesc(@Param("course_id") String course);

    @Query( "select new edu.tecnocampus.labinternet.coursefy.application.dto.ReviewDTO(r)" +
            "from courses c join c.reviews r " +
            "where c.title like :course_id " +
            "order by r.satisfactionDegree desc")
    List<ReviewDTO> findAllByCourseOrderBySatisfactionDegreeDesc(@Param("course_id") String course);
}
