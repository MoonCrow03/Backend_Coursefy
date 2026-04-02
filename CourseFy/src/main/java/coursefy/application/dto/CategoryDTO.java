package coursefy.application.dto;

import coursefy.domain.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
@Getter
public class CategoryDTO {
    private Long id;
    private String name;
    private String description;

    public CategoryDTO(Category category){
        id = category.getId();
        name = category.getName();
        description = category.getDescription();
    }

    public CategoryDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryDTO that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
