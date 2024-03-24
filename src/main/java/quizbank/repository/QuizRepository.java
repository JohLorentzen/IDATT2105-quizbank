package quizbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import quizbank.dto.QuizDTO;
import quizbank.model.Quiz;
import quizbank.model.User;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {


    List<Quiz> findByCreatedBy(User user);
}
