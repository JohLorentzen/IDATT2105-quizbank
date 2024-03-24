package quizbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import quizbank.dto.QuizDTO;
import quizbank.model.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Long> {



}
