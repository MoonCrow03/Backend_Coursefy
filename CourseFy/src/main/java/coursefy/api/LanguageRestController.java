package coursefy.api;

import coursefy.application.LanguageService;
import coursefy.application.dto.LanguageDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class LanguageRestController {
    private LanguageService languageService;

    public LanguageRestController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @PostMapping("/languages")
    @ResponseStatus(HttpStatus.CREATED)
    public LanguageDTO createLanguage (@RequestBody @Valid LanguageDTO languageDTO){
        return languageService.createLanguage(languageDTO);
    }
}
