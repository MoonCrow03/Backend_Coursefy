package coursefy.domain;

import coursefy.application.dto.CategoryDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "categories")
@Table(name = "categories",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name")
        })
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    public Category(CategoryDTO categoryDTO){
        id = categoryDTO.getId();
        name = categoryDTO.getName();
        description = categoryDTO.getDescription();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category category)) return false;
        return Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
