package coursefy.domain;

import coursefy.application.dto.CourseDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.*;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "courses")
@Table(name = "courses")
public class Course {
    @Id
    @Pattern(regexp = "^[A-Z].*", message = "Titles must begin with capital letter")
    private String title;

    private String description;
    private String imageUrl;
    private LocalDate datePublication;
    private LocalDate dateLastUpdate;
    private boolean available;

    @DecimalMin(value = "0.0", message = "No negative price numbers")
    private float price;

    @ManyToOne
    private Category category;

    @ManyToOne
    private User user;

    @ManyToOne
    private Language language;

    @OneToMany
    @JoinColumn(name = "course_id")
    @OrderColumn(name = "list_index")
    private Set<Lesson> lessonList = new HashSet<>();

    @OneToMany
    @JoinColumn(name = "review_id")
    private Set<Review> reviews = new HashSet<>();

    public Course(CourseDTO courseDTO) {
        updateCourse(courseDTO);

        this.available = false;
        this.price = courseDTO.getPrice();
        this.datePublication = LocalDate.now();
        this.dateLastUpdate = LocalDate.now();

        this.user = new User(courseDTO.getUser());
        this.category = new Category(courseDTO.getCategory());
        this.language = new Language(courseDTO.getLanguage());
    }

    public void updateAvailability() {
        this.dateLastUpdate = LocalDate.now();
        this.available = !available;
    }

    public void updatePrice(float price) {
        this.dateLastUpdate = LocalDate.now();
        this.price = price;
    }

    public void updateCourse(CourseDTO courseDTO) {
        this.dateLastUpdate = LocalDate.now();
        this.title = courseDTO.getTitle();
        this.description = courseDTO.getDescription();
        this.imageUrl = courseDTO.getImageUrl();
    }

    public boolean hasReview(User user){
        boolean a = getReview(user) != null;
         return getReview(user) != null;
    }

    public Review getReview(User user){
        return reviews.stream().filter(x -> x.getUser().equals(user)).findAny().orElse(null);
    }

    public boolean isTitleCapitalized(String title) {
        return title != null && title.matches("^[A-Z].*");
    }

    public void addLesson(Lesson lesson){
        lessonList.add(lesson);
    }

    public void addReview(Review review){
        reviews.add(review);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course course)) return false;
        return Objects.equals(title, course.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}