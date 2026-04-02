package coursefy.application.dto;

import coursefy.domain.Course;
import coursefy.domain.Enrollment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class CourseFromUserDTO {
    private String title;
    private String description;
    private String imageUrl;
    private String datePublication;
    private String dateLastUpdate;
    private boolean available;
    private float price;

    private CategoryDTO category;
    private LanguageDTO language;
    private UserDTO user;
    private EnrollmentDTO enrollment;
    private ReviewDTO review;
    private List<LessonDTO> lessons = new ArrayList<>();
    private List<LessonDTO> completedLessons = new ArrayList<>();

    public CourseFromUserDTO(Course course, Enrollment enrollment) {
        this.title = course.getTitle();
        this.description = course.getDescription();
        this.imageUrl = course.getImageUrl();
        this.price = course.getPrice();
        this.available = course.isAvailable();

        if(course.getDatePublication() != null)
            this.datePublication = updateDate(course.getDatePublication());

        if(course.getDateLastUpdate() != null)
            this.dateLastUpdate = updateDate(course.getDateLastUpdate());

        this.user = new UserDTO(course.getUser());
        this.category = new CategoryDTO(course.getCategory());
        this.language = new LanguageDTO(course.getLanguage());
        this.enrollment = new EnrollmentDTO(enrollment);

        this.lessons = course.getLessonList().stream().map(x -> new LessonDTO(x)).collect(Collectors.toList());
        this.completedLessons = enrollment.getCompletedLessons().stream().map(x -> new LessonDTO(x)).collect(Collectors.toList());

        if(course.hasReview(enrollment.getUser())){
            review = new ReviewDTO(course.getReview(enrollment.getUser()));
        }
    }

    private String updateDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CourseFromUserDTO that)) return false;
        return Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
