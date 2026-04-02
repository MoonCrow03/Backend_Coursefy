package coursefy.application.dto;

import coursefy.domain.Lesson;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class LessonDTO {
    private Long id;
    private String name;
    private String description;
    private float duration;
    private String videoUrl;

    public LessonDTO(Lesson lesson){
        id = lesson.getId();
        name = lesson.getName();
        description = lesson.getDescription();
        duration = lesson.getDuration();
        videoUrl = lesson.getVideoUrl();
    }

    public LessonDTO(String name, String description, float duration, String videoUrl) {
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.videoUrl = videoUrl;
    }
}
