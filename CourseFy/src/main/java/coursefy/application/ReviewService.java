package coursefy.application;

import coursefy.application.dto.ReviewDTO;
import coursefy.application.exception.*;
import edu.tecnocampus.labinternet.coursefy.application.exception.*;
import coursefy.domain.Course;
import coursefy.domain.Enrollment;
import coursefy.domain.Review;
import coursefy.domain.User;
import coursefy.persistence.CourseRepository;
import coursefy.persistence.EnrollmentRepository;
import coursefy.persistence.ReviewRepository;
import coursefy.persistence.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewService {
    ReviewRepository reviewRepository;
    CourseRepository courseRepository;
    UserRepository userRepository;
    EnrollmentRepository enrollmentRepository;

    public ReviewService(ReviewRepository reviewRepository, CourseRepository courseRepository, EnrollmentRepository enrollmentRepository, UserRepository userRepository){
        this.reviewRepository=reviewRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
    }

    public ReviewDTO createReview (ReviewDTO reviewDTO, String courseId, String userId) {
        Course course = courseRepository.findCourseByTitle(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));

        User user = userRepository.findByUsername(userId).orElseThrow(() -> new UsernameNotFoundException(userId));

        Enrollment enrollment = enrollmentRepository.findEnrollmentByCourseAndUser(courseId, userId)
                .orElseThrow(() -> new EnrollmentNotFoundException("Enrollment not found"));

        if(course.hasReview(user)){
            throw new ReviewAlreadyExistException("This course already has a review");
        }

        if (!enrollment.canMakeReview()){
            throw new CantMakeReview("Can't make a review about this course");
        }

        Review review = new Review(reviewDTO, course, user);

        reviewRepository.save(review);

        return new ReviewDTO(review);
    }

    @Transactional
    public ReviewDTO updateReview(ReviewDTO reviewDTO, String reviewTitle, String userId){
        Review review = reviewRepository.findByTitle(reviewTitle).orElseThrow(() -> new ReviewNotFoundException("Review not found"));

        User user = userRepository.findByUsername(userId).orElseThrow(() -> new UsernameNotFoundException(userId));

        if(!review.isOwner(user)){
            throw new ReviewNotModificableException("Review is not modifiable");
        }

        review.updateReview(reviewDTO);
        return new ReviewDTO(review);
    }

    public List<ReviewDTO> listReviewsByCourseOrderByCreationDate(String courseId){
        return reviewRepository.findAllByCourseOrderByCreationDateDesc(courseId);
    }

    public List<ReviewDTO> listReviewsByCourseOrderBySatisDegree(String courseId){
        return reviewRepository.findAllByCourseOrderBySatisfactionDegreeDesc(courseId);
    }
}
