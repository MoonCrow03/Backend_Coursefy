package coursefy.application.dto;

import coursefy.domain.Language;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
@Getter
public class LanguageDTO {
    private Long id;
    private String name;

    public LanguageDTO(Language language){
        id = language.getId();
        name = language.getName();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LanguageDTO that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
