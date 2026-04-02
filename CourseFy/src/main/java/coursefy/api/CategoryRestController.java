package coursefy.api;

import coursefy.application.CategoryService;
import coursefy.application.dto.CategoryDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class CategoryRestController {
    private CategoryService categoryService;

    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDTO createCategory (@RequestBody @Valid CategoryDTO categoryDTO){
        return categoryService.createCategory(categoryDTO);
    }

    @DeleteMapping("/categories/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable String id){
        categoryService.deleteCategory(id);}

    @DeleteMapping("/categories")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllCategories(){
        categoryService.deleteAllCategories();}
}
