package quizbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import quizbank.model.Quiz;
import quizbank.model.User;

import java.util.List;

/**
 * Repository for managing {@link Quiz} entities.
 */
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    /**
     * Finds all quizzes created by a specific user.
     *
     * @param user the user who created the quizzes
     * @return a list of quizzes created by the specified user
     */
    List<Quiz> findByCreatedBy(User user);
}