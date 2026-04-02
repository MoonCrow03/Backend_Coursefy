package coursefy.domain;

import coursefy.application.dto.UserDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "users")
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        })
public class User {
    public enum Gender {Man, Woman, Indefinite}

    @Id
    private String username;

    private String name;
    private String first_surname;
    private String second_surname;

    @Email
    private String email;

    private Gender gender;

    public User(UserDTO userDTO){
        username = userDTO.getUsername();
        name = userDTO.getName();
        first_surname = userDTO.getFirst_surname();
        second_surname = userDTO.getSecond_surname();
        email = userDTO.getEmail();
        gender = Gender.valueOf(userDTO.getGender());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
