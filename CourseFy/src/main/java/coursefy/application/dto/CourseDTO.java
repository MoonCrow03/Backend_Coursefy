package coursefy.application.dto;

import coursefy.domain.Course;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@NoArgsConstructor
@Getter
public class CourseDTO {
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

    public CourseDTO(Course course) {
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
    }

    public CourseDTO(String title, String description, String imageUrl, float price) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.datePublication = updateDate(LocalDate.now());
        this.dateLastUpdate = updateDate(LocalDate.now());
        this.available = false;
        this.price = price;
    }

    public CourseDTO(String title, String description, String imageUrl, float price, UserDTO user, CategoryDTO category, LanguageDTO language) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.datePublication = updateDate(LocalDate.now());
        this.dateLastUpdate = updateDate(LocalDate.now());
        this.available = false;
        this.price = price;

        this.user = user;
        this.category = category;
        this.language = language;
    }

    private String updateDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CourseDTO courseDTO)) return false;
        return Objects.equals(title, courseDTO.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
