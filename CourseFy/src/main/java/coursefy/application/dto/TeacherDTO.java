package coursefy.application.dto;

import coursefy.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TeacherDTO {
    private String username;
    private String name;
    private String firstSurname;
    private String secondSurname;
    private double avgRating;

    public TeacherDTO(User user, double avgRating){
        username = user.getUsername();
        name = user.getName();
        firstSurname = user.getFirst_surname();
        secondSurname = user.getSecond_surname();
        this.avgRating = avgRating;
    }

}
