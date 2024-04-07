package quizbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import quizbank.model.Question;

/**
 * Repository for managing {@link Question} entities.
 */
public interface QuestionRepository extends JpaRepository<Question, Long> {
}