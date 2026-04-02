package coursefy.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Embeddable
public class EnrolmentId implements Serializable {

    @Column(name = "course_id")
    private String courseId;

    @Column(name = "user_id")
    private String userId;

    public EnrolmentId(String courseId, String userId) {
        this.courseId = courseId;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EnrolmentId that)) return false;
        return Objects.equals(courseId, that.courseId) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, userId);
    }
}
