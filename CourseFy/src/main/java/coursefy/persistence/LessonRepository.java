package coursefy.persistence;

import coursefy.domain.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, String> {
    Optional<Lesson> findLessonByName(String name);
}