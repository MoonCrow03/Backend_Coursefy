package coursefy.domain;

import coursefy.application.dto.ReviewDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "reviews")
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String contents;

    @Min(value = 0, message = "The minimum rating is 0")
    @Max(value = 5, message = "The maximum rating is 5")
    private int satisfactionDegree;

    private LocalDate creationDate;
    private LocalDate lastUpdateDate;

    @ManyToOne
    private Course course;

    @ManyToOne
    private User user;

    public Review(ReviewDTO reviewDTO, Course course, User user){
        updateReview(reviewDTO);

        id = reviewDTO.getId();
        creationDate = LocalDate.now();
        this.course = course;
        this.user = user;

        this.course.addReview(this);
    }

    public void updateReview(ReviewDTO reviewDTO){
        title = reviewDTO.getTitle();
        contents = reviewDTO.getContents();
        satisfactionDegree = reviewDTO.getSatisfactionDegree();
        lastUpdateDate = LocalDate.now();
    }

    public boolean isOwner(User user){
        return this.user.equals(user);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Review review)) return false;
        return Objects.equals(id, review.id) && Objects.equals(user, review.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user);
    }
}
