package coursefy.application.dto;

import coursefy.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
@Getter
public class UserDTO {

    @Size(min = 5, max = 10)
    @Pattern(regexp = "^[A-Z][a-zA-Z]*$", message = "Nickname must begin with a capital letter. Also only letters are allowed")
    private String username;

    private String name;
    private String first_surname;
    private String second_surname;

    @Email
    private String email;
    private String gender;

    public UserDTO(User user){
        username = user.getUsername();
        name = user.getName();
        first_surname = user.getFirst_surname();
        second_surname = user.getSecond_surname();
        email = user.getEmail();
        gender = user.getGender().toString();
    }

    public UserDTO(String username, String name, String first_surname, String second_surname, String email, String gender) {
        this.username = username;
        this.name = name;
        this.first_surname = first_surname;
        this.second_surname = second_surname;
        this.email = email;
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO userDTO)) return false;
        return Objects.equals(username, userDTO.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
