package quizbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import quizbank.model.QuizAttempt;

import java.util.List;

public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Long> {
    List<QuizAttempt> findByUserId(Long userId);
}