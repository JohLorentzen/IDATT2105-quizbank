package quizbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import quizbank.model.QuizAttempt;

import java.util.List;

/**
 * Repository for managing {@link QuizAttempt} entities.
 */
public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Long> {

    /**
     * Finds all quiz attempts associated with a specific user.
     *
     * @param userId the ID of the user
     * @return a list of quiz attempts for the specified user
     */
    List<QuizAttempt> findByUserId(Long userId);
}