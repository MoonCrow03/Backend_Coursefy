package coursefy.application;

import coursefy.application.dto.CategoryDTO;
import coursefy.application.exception.CategoryNameDuplicateException;
import coursefy.domain.Category;
import coursefy.persistence.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryDTO createCategory(CategoryDTO categoryDTO){
        // We use the name instead of the ID, because ID is generated randomly and the name is UNIQUE
        if (categoryRepository.findByName(categoryDTO.getName()).isPresent()) {
            throw new CategoryNameDuplicateException("Category already exists");
        }

        Category category = new Category(categoryDTO);
        categoryRepository.save(category);
        return new CategoryDTO(category);
    }

    public void deleteCategory(String id){
        categoryRepository.deleteById(id);
    }

    public void deleteAllCategories(){
        categoryRepository.deleteAll();
    }
}
