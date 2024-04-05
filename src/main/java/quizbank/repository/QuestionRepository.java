package quizbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import quizbank.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
