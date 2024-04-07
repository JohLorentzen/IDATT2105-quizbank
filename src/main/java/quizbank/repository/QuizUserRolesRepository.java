package quizbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import quizbank.enums.Role;
import quizbank.model.Quiz;
import quizbank.model.QuizUserRoles;
import quizbank.model.User;

import java.util.List;

/**
 * Repository for managing {@link QuizUserRoles} entities.
 */
public interface QuizUserRolesRepository extends JpaRepository<QuizUserRoles, Long> {

    /**
     * Finds all quizzes associated with a specific user and role.
     *
     * @param user the user associated with the quizzes
     * @param role the role associated with the quizzes
     * @return a list of quizzes for the specified user and role
     */
    @Query("SELECT qur.quiz FROM QuizUserRoles qur WHERE qur.user = :user AND qur.role = :role")
    List<Quiz> findQuizzesByUserAndRole(User user, Role role);
}