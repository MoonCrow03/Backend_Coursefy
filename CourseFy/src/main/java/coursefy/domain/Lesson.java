package coursefy.domain;

import coursefy.application.dto.LessonDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private float duration;
    private String videoUrl;

    @ManyToMany(mappedBy = "completedLessons")
    private Set<Enrollment> enrollments = new HashSet<>();

    public Lesson(LessonDTO lessonDTO){
        id = lessonDTO.getId();
        updateLesson(lessonDTO);
    }

    public void updateLesson(LessonDTO lessonDTO){
        name = lessonDTO.getName();
        description = lessonDTO.getDescription();
        videoUrl = lessonDTO.getVideoUrl();
        duration = lessonDTO.getDuration();
    }

    public void addEnrollment(Enrollment enrollment){
        enrollments.add(enrollment);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lesson lesson)) return false;
        return Objects.equals(id, lesson.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
