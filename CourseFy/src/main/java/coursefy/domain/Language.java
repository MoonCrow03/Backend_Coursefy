package coursefy.domain;

import coursefy.application.dto.LanguageDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "languages")
@Table(name = "languages",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name")
        })
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Language(LanguageDTO languageDTO){
        id = languageDTO.getId();
        name = languageDTO.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Language language)) return false;
        return Objects.equals(id, language.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
