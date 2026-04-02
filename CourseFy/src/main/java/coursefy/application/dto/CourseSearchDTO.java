package coursefy.application.dto;

import coursefy.domain.Course;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CourseSearchDTO {
    private String title;
    private String description;

    public CourseSearchDTO(Course course) {
        this.title = course.getTitle();
        this.description = course.getDescription();
    }

    public CourseSearchDTO(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
