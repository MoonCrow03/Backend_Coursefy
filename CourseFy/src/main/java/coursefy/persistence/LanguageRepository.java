package coursefy.persistence;

import coursefy.domain.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface LanguageRepository extends JpaRepository<Language, String> {
    Optional<Language> findByName (String name);
}
