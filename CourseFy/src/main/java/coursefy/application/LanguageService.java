package coursefy.application;

import coursefy.application.dto.LanguageDTO;
import coursefy.application.exception.LanguageDuplicateException;
import coursefy.domain.Language;
import coursefy.persistence.LanguageRepository;
import org.springframework.stereotype.Service;

@Service
public class LanguageService {
    private LanguageRepository languageRepository;

    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    public LanguageDTO createLanguage(LanguageDTO languageDTO){

        // We use the name instead of the ID, because ID is generated randomly and the name is UNIQUE
        if (languageRepository.findById(languageDTO.getName()).isPresent()) {
            throw new LanguageDuplicateException("Language already exists");
        }

        Language language = new Language(languageDTO);
        languageRepository.save(language);
        return new LanguageDTO(language);
    }

}
