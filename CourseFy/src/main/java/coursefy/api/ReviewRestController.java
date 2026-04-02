package coursefy.api;

import coursefy.application.ReviewService;
import coursefy.application.dto.ReviewDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/users/{userId}/courses/{courseId}/enrollments")
@RestController
public class ReviewRestController {
    ReviewService reviewService;

    public ReviewRestController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/reviews")
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewDTO createReview(@PathVariable String userId, @PathVariable String courseId, @RequestBody @Valid ReviewDTO reviewDTO){
        return reviewService.createReview(reviewDTO, courseId, userId);
    }

    @PutMapping("/reviews/{reviewTitle}")
    public ReviewDTO updateReview(@PathVariable String reviewTitle, @PathVariable String userId, @RequestBody @Valid ReviewDTO reviewDTO){
        return reviewService.updateReview(reviewDTO, reviewTitle, userId);
    }

    @GetMapping("/reviews/listByDate")
    public List<ReviewDTO> listReviewsByCourseOrderByCreationDate(@PathVariable String courseId){
        return reviewService.listReviewsByCourseOrderByCreationDate(courseId);
    }

    @GetMapping("/reviews/listByRating")
    public List<ReviewDTO> listReviewsByCourseOrderBySatisDegree(@PathVariable String courseId){
        return reviewService.listReviewsByCourseOrderBySatisDegree(courseId);
    }
}
